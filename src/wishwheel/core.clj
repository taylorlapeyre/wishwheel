(ns wishwheel.core
  (:use compojure.core)
  (:require [compojure.route :as route]
            [compojure.handler :as handler]
            [compojure.response :as response]
            [wishwheel.models.user :as user]
            [wishwheel.models.wheel :as wheel]
            [cheshire.core :as json]
            [ring.util.response :refer [resource-response response]]
            [ring.middleware.json :as middleware]))

(defroutes main-routes
  (GET "/" []
    (resource-response "index.html" {:root "public"}))

  (GET "/wheels/:id" []
    (fn [req]
      (let [id (get (:params req) :id)
            user (first (wheel/find-by-id {:id id}))]
        (if-not (nil? user)
          (response user)
          (ring.util.response/not-found nil)))))

  (POST "/wheels"
    []
    (fn [req]
      (let [wheel-params (:wheel (:params req))]
        (try
          (wheel/insert! wheel-params)
          (response wheel-params)
          (catch java.lang.AssertionError e
            (ring.util.response/status (response (.getMessage e)) 422))))))

  (GET "/users/:email"
    [email]
    (response (first (user/find-by-email {:email email}))))

  (POST "/users"
    [user]
    (response (user/secure-insert! user)))

  (POST "/authenticate"
    [& params]
    (println params)
    (response params))

  (route/resources "/")
  (route/not-found "Page not found"))

(def app
  (-> (handler/site main-routes)
      (middleware/wrap-json-body)
      (middleware/wrap-json-response)))
