(ns wishwheel.core
  (:use compojure.core)
  (:require [compojure.route :as route]
            [compojure.handler :as handler]
            [ring.middleware.json :as middleware]
            [ring.util.response :refer [resource-response]]
            [wishwheel.controllers.users  :as users-controller]
            [wishwheel.controllers.wheels :as wheels-controller]
            [wishwheel.controllers.items  :as items-controller]
            [wishwheel.controllers.groups :as groups-controller]))

(defroutes main-routes
  (GET "/" []
    (resource-response "index.html" {:root "public"}))

  (comment ========================================================)

  ; Wheel Resources
  (GET "/wheels/:id"
    [id]
    (wheels-controller/show id))

  (POST "/wheels"
    {body :body}
    (wheels-controller/create (:wheel body)))

  (comment ========================================================)

  ; User Resources
  (GET "/users/:email"
    [email]
    (users-controller/show email))

  (POST "/users"
    {body :body}
    (users-controller/create (:user body)))

  (comment ========================================================)

  ; Item Resources
  (GET "/items/:id"
    [id]
    (items-controller/show id))

  (PATCH "/items/:id"
    {body :body params :params}
    (items-controller/update (:id params) (:item body)))

  (POST "/wheel/:id/items"
    {body :body params :params}
    (items-controller/create (:id params) (:item body)))

  (GET "/wheels/:id/items"
    [id]
    (items-controller/index id))

  (comment ========================================================)

  ; Group Resources
  (GET "/groups/:id"
    [id]
    (groups-controller/show id))

  (POST "/groups"
    {body :body}
    (groups-controller/create (:group body)))

  (GET "/users/:id/groups"
    [id]
    (groups-controller/index id))

  (POST "/groups/:id/adduser"
    {body :body params :params}
    (groups-controller/add-user (:id params) (:user_id body)))

  (comment ========================================================)

  ; Authentication Resource
  (POST "/auth"
    {body :body}
    (users-controller/auth (:email body) (:password body)))

  (comment ========================================================)

  (route/resources "/")
  (route/not-found "Page not found"))

(def app
  (-> (handler/site main-routes)
      (middleware/wrap-json-body {:keywords? true})
      (middleware/wrap-json-response)))
