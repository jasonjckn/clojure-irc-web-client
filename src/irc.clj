(ns irc
  (:use [clojure.java.io :only [reader writer]]
        [gloss core]
        [lamina core]
        [aleph http tcp]
        ))

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
	       ;; :as-str will decode as actual strings, rather than just a CharSequence
              :frame (string :utf-8 :delimiters ["\r\n"] :as-str true)})

        setup-irc (fn []
		    ;;this filter* should be a remove*, but I apparently forgot to expose that in alpha1
		    (siphon (->> ch fork (map* parse-ping) (filter* (complement nil?))) ch) 
		    (apply enqueue ch (handshake)))]
    
    (receive-all (fork ch) println)
    (setup-irc)

    ch))

(def irc-ch)
(defn init-globals []
  (def irc-ch (make-irc-channel)))





