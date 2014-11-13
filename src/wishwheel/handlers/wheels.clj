(ns wishwheel.handlers.wheels
  (:require [ring.util.response :refer [response status not-found]]
            [wishwheel.models.wheel :as wheel]
            [wishwheel.models.user :as user]))

(defn show
  [{:keys [params]}]
  "Returns a json representation of the wheel with a given id."
  (if-let [wheel (wheel/find-by-id (:id params))]
    (response wheel)
    (not-found "Wheel does not exist")))

(defn create
  "Creates a new wheel."
  [{:keys [params body]}]
  (user/when-authenticated (:token body) (fn [api-user]
    (try (wheel/create (assoc (:wheel body) :user_id (:id api-user)))
         (status (response (:wheel body)) 201)
    (catch java.lang.AssertionError e
      (status (response (.getMessage e)) 422))))))
