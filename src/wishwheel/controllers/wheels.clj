(ns wishwheel.controllers.wheels
  (:require [ring.util.response :refer [resource-response response]]
            [wishwheel.models.wheel :as wheel]))

(defn show
  [id]
  (let [wheel (first (wheel/find-by-id {:id id}))]
    (if (nil? wheel)
      {:status 404 :body "Wheel does not exist"}
      (response wheel))))

(defn create
  [wheel-params]
  (try
    (wheel/validate wheel-params)
    (wheel/insert! wheel-params)
    {:status 201 :body wheel-params}
    (catch java.lang.AssertionError e
      {:status 422 :body (.getMessage e)})))
