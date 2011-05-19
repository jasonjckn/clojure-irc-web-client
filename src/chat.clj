(ns chat
  (:use hiccup.core))

(defn chat-page []
  (html
   [:style
    "#msg { width:600px; }\n"
    "#chat { height:400px; width: 600px; border: 2px solid; font-family: consolas; overflow:auto;}\n"]
   [:h3 "IRC Chat Client"]
   [:input#msg {:type "textbox" :onkeydown "if (event.keyCode == 13) { ws.send(this.value); this.value = ''; }"}]
   [:div#chat "Connecting...<br />"]
   [:script
    "ws=new WebSocket('ws://localhost:3005/socket');\n"
    "chat=document.getElementById('chat');\n"
    "ws.onmessage=function(m){chat.innerHTML = m.data + '<br />' + chat.innerHTML;}\n"]
   ))
