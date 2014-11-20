(ns wishwheel.core
  "Defines our routes and provides the main HTTP handler."
  (:gen-class)
  (:use compojure.core
        wishwheel.images
        ring.middleware.json
        ring.middleware.multipart-params)
  (:require [ring.adapter.jetty :as jetty-adapter]
            [ring.util.response :refer [resource-response]]
            [compojure.route   :as route]
            [compojure.handler :as handler]
            [environ.core :refer [env]]
            [wishwheel.handlers.users  :as user-handlers]
            [wishwheel.handlers.wheels :as wheel-handlers]
            [wishwheel.handlers.items  :as item-handlers]
            [wishwheel.handlers.groups :as group-handlers]))

(defroutes main-routes
  (GET "/" []
    (resource-response "index.html" {:root "public"}))

  (context "/api" []

    ; ========================================================

    ; Wheel Resources
    (GET "/wheels/:id"
      [id]
      (wheel-handlers/show id))

    (POST "/wheels"
      {params :params body :body}
      (wheel-handlers/create (:token body) (:wheel body)))

    ; ========================================================

    ; User Resources
    (GET "/users/:email"
      [email]
      (user-handlers/show email))

    (POST "/users"
      {params :params body :body}
      (user-handlers/create (:user body)))

    ; ========================================================

    ; Item Resources
    (GET "/items/:id"
      [id]
      (item-handlers/show id))

    (PATCH "/items/:id"
      {params :params body :body}
      (if-let [i (or (:image params) (:image body))]
        (item-handlers/update (:token body) (:id params) (assoc (:item body) :image i))
        (item-handlers/update (:token body) (:id params) (:item body))))

    (POST "/wheels/:id/items"
      {params :params}
      (if-let [i (:image params)]
        (item-handlers/create (:token params) (:id params) (assoc (:item params) :image i))
        (item-handlers/create (:token params) (:id params) (:item params))))


    (GET "/wheels/:id/items"
      [id]
      (item-handlers/index id))

    ; ========================================================

    ; Group Resources
    (GET "/groups/:id"
      [id]
      (group-handlers/show id))

    (POST "/groups"
      {params :params body :body}
      (group-handlers/create (:token body) (:group body)))

    (GET "/users/:email/groups"
      [email]
      (group-handlers/index email))

    (POST "/groups/:id/adduser"
      {params :params body :body}
      (group-handlers/add-user (:token body) (:id params) (:user_id body)))

    ; ========================================================

    ; Authentication Resource
    (POST "/auth"
      {params :params body :body}
      (user-handlers/auth (:email body) (:password body))))

    ; ========================================================

  (route/resources "/")
  (route/not-found "Resource not found"))

(def app
  "This is the handler for HTTP requests."
  (-> (handler/api main-routes)
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
