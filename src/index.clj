(ns index
  (:use hiccup.core))

(defn index-page []
  (html
   [:style
    "#msg { width:600px; }\n"
    "#chat { height:400px; width: 600px; border: 2px solid; font-size: 10px; font-family: consolas; overflow:auto;}\n"]
   [:h3 "IRC Chat Client"]
   [:input#msg {:type "textbox" :onkeydown "if (event.keyCode == 13) { ws.send(this.value); this.value = ''; }"}]
   [:div#chat ""]
   [:p "Status: " [:span#foo "Disconnected."]]
   [:script
    "ws=new WebSocket('ws://localhost:3005/socket');\n"
    "chat=document.getElementById('chat');\n"
    "foo=document.getElementById('foo');\n"
    "ws.onopen=function(){foo.innerHTML = 'Connected!';}\n"
    "ws.onclose=function(){foo.innerHTML = 'Disconnected.';}\n"
    "ws.onmessage=function(m){chat.innerHTML = m.data + '<br />' + chat.innerHTML;}\n"]
   ))
