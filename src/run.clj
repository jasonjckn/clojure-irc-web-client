(ns run
  (:use [server]
        [ring.middleware.reload]
        [aleph http tcp]
        [lamina core]))

(defn -main []
     (def main-routes-reload (wrap-reload #'main-routes '(server chat ircbot)))
     (println "Starting server at http://localhost:3005/")
     (start-http-server (wrap-ring-handler main-routes-reload) {:port 3005 :websocket true}))

(-main)


