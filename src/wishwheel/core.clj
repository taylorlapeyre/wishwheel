(ns wishwheel.core
  (:gen-class)
  (:use compojure.core)
  (:require [compojure.route :as route]
            [compojure.handler :as handler]
            [ring.middleware.json :as middleware]
            [ring.util.response :refer [resource-response]]
            [environ.core :refer [env]]
            [ring.adapter.jetty :as jetty]
            [wishwheel.controllers.users  :as users-controller]
            [wishwheel.controllers.wheels :as wheels-controller]
            [wishwheel.controllers.items  :as items-controller]
            [wishwheel.controllers.groups :as groups-controller]))

(defroutes main-routes
  (GET "/" []
    (resource-response "index.html" {:root "public"}))

  ; ========================================================

  ; Wheel Resources
  (GET "/wheels/:id"
    [id]
    (wheels-controller/show id))

  (POST "/wheels"
    {body :body}
    (wheels-controller/create (:token body) (:wheel body)))

  ; ========================================================

  ; User Resources
  (GET "/users/:email"
    [email]
    (users-controller/show email))

  (POST "/users"
    {body :body}
    (users-controller/create (:user body)))

  ; ========================================================

  ; Item Resources
  (GET "/items/:id"
    [id]
    (items-controller/show id))

  (PATCH "/items/:id"
    {body :body params :params}
    (items-controller/update (:token body) (:id params) (:item body)))

  (POST "/wheel/:id/items"
    {body :body params :params}
    (items-controller/create (:token body) (:id params) (:item body)))

  (GET "/wheels/:id/items"
    [id]
    (items-controller/index id))

  ; ========================================================

  ; Group Resources
  (GET "/groups/:id"
    [id]
    (groups-controller/show id))

  (POST "/groups"
    {body :body}
    (groups-controller/create (:token body) (:group body)))

  (GET "/users/:id/groups"
    [id]
    (groups-controller/index id))

  (POST "/groups/:id/adduser"
    {body :body params :params}
    (groups-controller/add-user (:token body) (:id params) (:user_id body)))

  ; ========================================================

  ; Authentication Resource
  (POST "/auth"
    {body :body}
    (users-controller/auth (:email body) (:password body)))

  ; ========================================================

  (route/resources "/")
  (route/not-found "Page not found"))

(def app
  "This is the handler for HTTP requests."
  (-> (handler/site main-routes)
      (middleware/wrap-json-body {:keywords? true})
      (middleware/wrap-json-response)))

(defn -main
  "This is the entry point into the application. It runs the server."
  [& [port]]
  (let [chosen-port (or port (env :port) "3000")
        parse-int #(Integer/parseInt (re-find #"\A-?\d+" %))]
    (jetty/run-jetty app {:port (parse-int chosen-port) :join? false})))
