(ns websocket
  (:require [irc :as irc])
  (:use [aleph http tcp]
        [lamina core])
  )

(def status (channel))
(defn siphon-irc-server []
  (when-let [line (irc/get-status-line)]
    (enqueue status line)
    (recur)))

(defn websocket-handler [channel client-info]
  (receive-all channel irc/send-msg)
  (siphon status channel))








