(ns irc
  (:use [clojure.java.io :only [reader writer]])
  (:import [java.net Socket]))

(def chan "#bot-testing")
(def nick "clj-powered-bot")

(defn irc-cmd [cmd args] (str cmd " " args "\r\n"))
(defn msg-cmd [txt] (irc-cmd "PRIVMSG" (str chan " :" txt)))
(defn user-cmd [] (irc-cmd "USER" (str nick " 0 * :tutorial bot")))
(defn join-cmd [] (irc-cmd "JOIN" chan))
(defn nick-cmd [] (irc-cmd "NICK" nick))

(defn do-cmds [w & cmds]
  (doseq [c cmds] (.write w c))
  (.flush w))

(defn irc-handshake []
  (let [s (Socket. "irc.freenode.org" 6667)
        w (writer s)]
    (do-cmds w (nick-cmd) (user-cmd) (join-cmd))
    s))

;; GLOBAL VAR HACKS:
(defn init-globals []
  (def s (irc-handshake))
  (def br (reader s))
  (def bw (writer s)))
(defn send-msg [txt]
  (do-cmds bw (msg-cmd txt)))
(defn get-status-line []
  (.readLine br))

