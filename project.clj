(defproject ircbot "0.1"
  :dependencies [[org.clojure/clojure "1.2.0"]
                 [org.clojure/clojure-contrib "1.2.0"]

                 [compojure "0.6.3"]
                 [hiccup "0.3.4"]

                 [aleph "0.1.5-SNAPSHOT"]
                 [lamina "0.4.0-SNAPSHOT"]
                 [ring/ring-core "0.2.5"]

                 ;[org.eclipse.jetty/jetty-websocket "7.4.0.RC0"]
                 ]
  :dev-dependencies [[swank-clojure "1.2.1"]
                     ;[lein-ring "0.4.0"]
                     ]
  ;:ring {:handler test/app}
  ;:main ircbot
  ;:aot :all
  )

