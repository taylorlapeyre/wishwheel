(ns wishwheel.core
  (:use compojure.core)
  (:require [compojure.route :as route]
            [compojure.handler :as handler]
            [compojure.response :as response]
            [wishwheel.models.user :as user]
            [cheshire.core :as json]
            [ring.util.response :refer [resource-response response]]
            [ring.middleware.json :as middleware]))

(defroutes main-routes
  (GET "/"
    []
    (resource-response "index.html" {:root "public"}))

  (GET "/user/:email"
    [email]
    (response (user/find-by-email {:email email})))

  (route/resources "/")
  (route/not-found "Page not found"))

(def app
  (-> (handler/site main-routes)
      (middleware/wrap-json-body)
      (middleware/wrap-json-response)))
