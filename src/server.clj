(ns server
  (:use [compojure.core]
        [ircbot]
        [chat])
  (:use [aleph http tcp]
        [lamina core])
  (:require [compojure.route :as route]
            [compojure.handler :as handler]))

(def status (channel))
(defn siphon-irc-server []
  (when-let [line (.readLine br)]
    (enqueue status line)
    (recur)))

(defn websocket-handler [channel client-info]
  (receive-all channel do-msg)
  (siphon status channel))

(defroutes main-routes
  (GET "/" [] (chat-page))
  (GET "/socket" [] (wrap-aleph-handler websocket-handler)))







