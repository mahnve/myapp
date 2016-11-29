(ns myapp.core
  (:gen-class)
  (:require [mount.core :as mount]
            [rum.core :as rum]
            [yada.yada :as yada]))

(rum/defc hello-world []
  [:div [:p "Hello World"]])


(def routes
  ["/"
   [["" (yada/as-resource "root")]
    ["hello" (yada/resource {:id :resources/root
                             :produces {:media-type "text/html"}
                             :methods { :get
                                       { :response (rum/render-static-markup (hello-world))}}})]]])


(mount/defstate server
  :start (yada/listener routes {:port 3000})
  :stop ((:close server)))


(defn -main
  "starts the server"
  [& args]
  (mount/start))
