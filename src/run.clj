(ns run
  (:use [server]
        [ring.middleware.reload]
        [aleph http tcp]
        [lamina core]))

(def main-routes-reload (wrap-reload #'main-routes '(server chat ircbot)))

(start-http-server (wrap-ring-handler main-routes-reload) {:port 3005 :websocket true})
