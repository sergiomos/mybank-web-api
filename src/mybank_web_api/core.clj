(ns mybank-web-api.core
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.route :as route])
  (:gen-class))

(defn respond-hello 
  [request]
  {:status 200 :body "Olá pedestal!"})

(def routes
  (route/expand-routes
   #{["/hello" 
      :get respond-hello 
      :route-name :greet]}))

(defn create-server []
  (http/create-server
   {::http/routes routes
    ::http/type :jetty
    ::http/port 3000}))

(defn start []
  (http/start (create-server)))