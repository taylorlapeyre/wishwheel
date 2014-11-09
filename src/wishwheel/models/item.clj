(ns wishwheel.models.item
  "Functions for interfacing with the `items` table in the database."
  (:require [oj.core :as oj]
            [oj.modifiers :refer [query select where insert update]]
            [wishwheel.config :refer [db]]))

(defn find-by-wheel
  "Given a wheel id, finds all items that belong to it in the database
  and returns them."
  [wheel_id]
  (-> (query :items)
      (where {:wheel_id wheel_id})
      (oj/exec db)))

(defn find-by-id
  "Given an id, finds the corresponding item in the database
  and returns it."
  [id]
  (-> (query :items)
      (where {:id id})
      (oj/exec db)
      (first)))

(defn create
  "Create a new item with the given data. Does not validate, will throw
  DB error on integrity constraint violation."
  [item-data]
  (-> (query :items)
      (insert item-data)
      (oj/exec db)))

(defn update
  "Given an id, updates that item with the supplied data. Does not validate,
  will throw DB error on integrity constraint violation."
  [id item-data]
  (-> (query :items)
      (where {:id id})
      (update item-data)
      (oj/exec db)))
