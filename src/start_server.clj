(ns start-server
  (:require [irc :as irc]
            [websocket :as ws])
  (:use [websocket]
        [index]
        [ring.middleware.reload]
        [aleph http tcp]
        [compojure.core])
  (:require [compojure.route :as route]
            [compojure.handler :as handler]))

(defroutes main-routes
  (GET "/" [] (index-page))
  (GET "/socket" [] (wrap-aleph-handler ws/websocket-handler)))

(defn -main []
  (irc/init-globals)
  (future (ws/siphon-irc-server))
  (def main-routes-reload (wrap-reload #'main-routes '(websocket index irc)))
  (println ">> Starting server at http://localhost:3005/")
  (println ">> Joining irc://irc.freenode.org/#bot-testing.")
  (start-http-server (wrap-ring-handler main-routes-reload) {:port 3005 :websocket true}))

(-main)



