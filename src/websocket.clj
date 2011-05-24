(ns websocket
  (:require [irc :as irc])
  (:use [aleph http tcp]
        [lamina core]))

(defn websocket-handler [ch client-info]
  (siphon (map* irc/msg-cmd ch) irc/irc-ch)
  (siphon irc/irc-ch ch))








