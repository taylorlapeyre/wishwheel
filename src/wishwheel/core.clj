(ns wishwheel.core
  (:use compojure.core)
  (:require [compojure.route :as route]
            [compojure.handler :as handler]
            [compojure.response :as response]
            [ring.middleware.json :as middleware]
            [ring.util.response :refer [resource-response]]
            [wishwheel.controllers.users :as users-controller]
            [wishwheel.controllers.wheels :as wheels-controller]
            [wishwheel.controllers.items :as items-controller]))

(defroutes main-routes
  (GET "/" []
    (resource-response "index.html" {:root "public"}))

  (GET "/wheels/:id" [id]
    (wheels-controller/show id))

  ; FIXME: what is user-params?
  (POST "/wheels" [wheel-params]
    (wheels-controller/create wheel-params))

  (GET "/users/:email" [email]
    (users-controller/show email))

  ; FIXME: what is user-params?
  (POST "/users" [user-params]
    (users-controller/create user-params))

  (GET "/items/:id" [id]
    (items-controller/show id))

  (GET "/wheels/:id/items" [id]
    (items-controller/index id))

  (route/resources "/")
  (route/not-found "Page not found"))

(def app
  (-> (handler/site main-routes)
      (middleware/wrap-json-body)
      (middleware/wrap-json-response)))
