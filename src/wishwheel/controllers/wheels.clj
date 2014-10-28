(ns wishwheel.controllers.wheels
  (:require [ring.util.response :refer [resource-response response]]
            [wishwheel.models.wheel :as wheel]
            [wishwheel.models.user :as user]))

(defn show
  [id]
  "Returns a json representation of the wheel with a given id."
  (let [wheel (first (wheel/find-by-id {:id id}))]
    (if (nil? wheel)
      {:status 404 :body "Wheel does not exist"}
      (response wheel))))

(defn create
  "Creates a new wheel."
  [token wheel-params]
  (user/when-authenticated token (fn [api-user]
    (try
      (wheel/validate wheel-params)
      (wheel/insert! wheel-params)
      {:status 201 :body wheel-params}
      (catch java.lang.AssertionError e
        {:status 422 :body (.getMessage e)})))))
