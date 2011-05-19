(ns ircbot
  (:use [clojure.java.io :only [reader writer]])
  (:import [java.net Socket]))

;; == OVERVIEW ==
;; chat on freenode with other people for extended periods of time.

;; === MODEL OF PROTOCOL ===
;; if `PING :<X>` then `PONG :<X>`
;; QUIT :Exiting
;; PRIVMSG #tutbot-testing :Hello There!

(def chan "#clojure")
(def nick "clj-powered-bot")

(defn irc-cmd [cmd args] (str cmd " " args "\r\n"))
(defn msg-cmd [txt] (irc-cmd "PRIVMSG" (str chan " :" txt)))
(defn user-cmd [] (irc-cmd "USER" (str nick " 0 * :tutorial bot")))
(defn join-cmd [] (irc-cmd "JOIN" chan))
(defn nick-cmd [] (irc-cmd "NICK" nick))

(defn do-cmds [w & cmds]
  (doseq [c cmds] (.write w c))
  (.flush w))

(defn init-irc [w]
  (do-cmds w (nick-cmd) (user-cmd) (join-cmd)))


;; HACKS:
(defn -main []
  (let [s (Socket. "irc.freenode.org" 6667)
        w (writer s)]
    (init-irc w)
    s))

(defn init-globals []
  (def s (-main))
  (def br (reader s))
  (def bw (writer s)))

(defn do-msg [txt]
  (do-cmds bw (msg-cmd txt)))
(defn do-read []
  (.readLine br))

;; DEBUG:
#_ (def s (-main))
#_ (def br (reader s))
#_ (def bw (writer s))
#_ (.readLine br)
#_ (.write bw "PRIVMSG #tutbot-testing :Hello There\r\n")
#_ (.flush bw)

