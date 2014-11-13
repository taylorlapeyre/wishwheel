(ns wishwheel.core
  "Defines our routes and provides the main HTTP handler."
  (:gen-class)
  (:use nav.core
        wishwheel.images
        ring.middleware.json
        ring.middleware.multipart-params
        ring.middleware.resource
        ring.middleware.file-info
        org.httpkit.server)
  (:require [environ.core :refer [env]]
            [ring.util.response :refer [resource-response]]
            [wishwheel.handlers.users  :as user-handlers]
            [wishwheel.handlers.wheels :as wheel-handlers]
            [wishwheel.handlers.items  :as item-handlers]
            [wishwheel.handlers.groups :as group-handlers]))

(defn serve-website
  [request]
  (resource-response "index.html" {:root "public"}))

(defn wrap-with-logger [handler]
  (fn [request]
    (println (:request-method request) (:uri request))
    (let [response (handler request)]
      response)))

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
      (wrap-json-response)
      (wrap-with-logger)))

(defn -main
  "This is the entry point into the application. It runs the server."
  [& [port]]
  (let [chosen-port (or port (env :port) "3000")
        parse-int #(Integer/parseInt (re-find #"\A-?\d+" %))]
    (println "Starting server on port" chosen-port)
    (run-server app {:port (parse-int chosen-port)})))
