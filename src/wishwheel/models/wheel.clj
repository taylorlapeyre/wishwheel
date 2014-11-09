(ns wishwheel.models.wheel
  "Functions for interfacing with the `wheels` table in the database."
  (:require [oj.core :as oj]
            [oj.modifiers :refer [query select where insert update]]
            [wishwheel.config :refer [db]]))

(defn find-by-user
  "Given a user id, finds all wheels created by that user."
  [user-id]
  (-> (query :wheels)
      (where {:user_id user-id})
      (oj/exec db)))

(defn find-by-group
  "Given an id, finds all wheels in the database that belong to that group."
  [group-id]
  (-> (query :wheels)
      (where {:group_id group-id})
      (oj/exec db)))

(defn find-by-id
  "Given an id, finds the corresponding wheel in the database
  and returns it."
  [id]
  (-> (query :wheels)
      (where {:id id})
      (oj/exec db)
      (first)))

(defn create
  "Create a new wheel with the given data. Does not validate, will throw
  DB error on integrity constraint violation."
  [wheel-data]
  (-> (query :wheels)
      (insert wheel-data)
      (oj/exec db)))
