(ns wishwheel.core
  (:use compojure.core)
  (:require [compojure.route :as route]
            [compojure.handler :as handler]
            [ring.middleware.json :as middleware]
            [ring.util.response :refer [resource-response]]
            [wishwheel.controllers.users :as users-controller]
            [wishwheel.controllers.wheels :as wheels-controller]
            [wishwheel.controllers.items :as items-controller]))

(defroutes main-routes
  (GET "/" []
    (resource-response "index.html" {:root "public"}))

  (GET "/wheels/:id"
    [id]
    (wheels-controller/show id))

  (POST "/wheels"
    {body :body}
    (wheels-controller/create (:wheel body)))

  (GET "/users/:email"
    [email]
    (users-controller/show email))

  (POST "/users"
    {body :body}
    (users-controller/create (:user body)))

  (GET "/items/:id"
    [id]
    (items-controller/show id))

  (PATCH "/items/:id"
    {body :body, params :params}
    (items-controller/update (:id params) (:item body)))

  (GET "/wheels/:id/items"
    [id]
    (items-controller/index id))

  (POST "/auth"
    {body :body}
    (users-controller/auth (:email body) (:password body)))

  (route/resources "/")
  (route/not-found "Page not found"))

(def app
  (-> (handler/site main-routes)
      (middleware/wrap-json-body {:keywords? true})
      (middleware/wrap-json-response)))
