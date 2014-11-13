(ns wishwheel.core
  "Defines our routes and provides the main HTTP handler."
  (:gen-class)
  (:use nav.core
        wishwheel.images
        ring.middleware.json
        ring.middleware.multipart-params
        ring.middleware.resource
        ring.middleware.file-info)
  (:require [ring.adapter.jetty :as jetty-adapter]
            [ring.util.response :refer [resource-response]]
            [environ.core :refer [env]]
            [wishwheel.handlers.users  :as user-handlers]
            [wishwheel.handlers.wheels :as wheel-handlers]
            [wishwheel.handlers.items  :as item-handlers]
            [wishwheel.handlers.groups :as group-handlers]))

(defn serve-website
  [request]
  (resource-response "index.html" {:root "public"}))

(def main-routes
  (-> (GET   "/"                           serve-website)
      (GET   "/api/wheels/:id"             wheel-handlers/show)
      (POST  "/api/wheels"                 wheel-handlers/create)
      (GET   "/api/users/:email"           user-handlers/show)
      (POST  "/api/users"                  user-handlers/create)
      (GET   "/api/items/:id"              item-handlers/show)
      (PATCH "/api/items/:id"              item-handlers/update)
      (POST  "/api/wheels/:wheel-id/items" item-handlers/create)
      (GET   "/api/wheels/:id/items"       item-handlers/index)
      (GET   "/api/groups/:id"             group-handlers/show)
      (POST  "/api/groups"                 group-handlers/create)
      (GET   "/api/users/:email/groups"    group-handlers/index)
      (POST  "/api/groups/:id/adduser"     group-handlers/add-user)
      (POST  "/api/auth"                   user-handlers/auth)))

(def app
  "This is the handler for HTTP requests."
  (-> (route main-routes)
      (wrap-resource "public")
      (wrap-file-info)
      (wrap-multipart-params {:store image-store})
      (wrap-json-body {:keywords? true})
      (wrap-json-response)))

(defn -main
  "This is the entry point into the application. It runs the server."
  [& [port]]
  (let [chosen-port (or port (env :port) "3000")
        parse-int #(Integer/parseInt (re-find #"\A-?\d+" %))]
    (jetty-adapter/run-jetty app {:port (parse-int chosen-port)
                                  :join? false})))
