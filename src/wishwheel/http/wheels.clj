(ns wishwheel.handlers.wheels
  (:require [ring.util.response :refer [response status not-found]]
            [wishwheel.db.core :as db]))

(defn show
  [{:keys [params]}]
  "Returns a json representation of the wheel with a given id."
  (if-let [wheel (db/wheel {:where {:id (:id params)}})]
    (response wheel)
    (not-found "Wheel does not exist")))

(defn create
  "Creates a new wheel."
  [{:keys [body]}]
  (if-let [api-user (db/user {:where {:token (:token body)}})]
    (try (db/wheel {:insert (assoc (:wheel body) :user_id (:id api-user))})
         (status (response (:wheel body)) 201)
         (catch java.lang.AssertionError e
           (status (response (.getMessage e)) 422)))
    (status (response "Invalid API token.") 403)))
