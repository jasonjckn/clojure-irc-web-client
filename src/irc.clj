(ns irc
  (:use [clojure.java.io :only [reader writer]]
        [gloss core]
        [lamina core]
        [aleph http tcp]
        )
  (:import [java.net Socket]))

(def chan "#bot-testing")
(def nick "clj-powered-bot")

(defn irc-cmd [cmd args] (str cmd " " args "\r\n"))
(defn msg-cmd [txt] (irc-cmd "PRIVMSG" (str chan " :" txt)))
(defn user-cmd [] (irc-cmd "USER" (str nick " 0 * :tutorial bot")))
(defn join-cmd [] (irc-cmd "JOIN" chan))
(defn nick-cmd [] (irc-cmd "NICK" nick))
(defn pong-cmd [token] (irc-cmd "PONG" token))

(defn handshake [] [(nick-cmd) (user-cmd) (join-cmd)])

(defn parse-ping [s]
  (let [[_ b] (re-matches #"PING :(.+)" s)] b))

(defn make-irc-channel []
  (let [ch @(tcp-client
             {:host "irc.freenode.org" :port 6667
              :frame (string :utf-8 :delimiters ["\r\n"])})

        str-ch (map* str ch)

        pong-if-ping #(when-let [token (parse-ping %)]
                        (enqueue ch (pong-cmd token)))

        setup-irc (fn []
                    (siphon (apply channel (handshake)) ch)
                    (receive-all str-ch pong-if-ping))]
    
    (receive-all str-ch println)
    (setup-irc)

    ch))

(def irc-ch)
(defn init-globals []
  (def irc-ch (make-irc-channel)))





