(ns wishwheel.db.item
  "Functions for interfacing with the `items` table in the database."
  (:require [oj.core :as oj]
            [oj.modifiers :as ojm]
            [environ.core :refer [env]]))

(defn find-by-wheel
  "Given a wheel id, finds all items that belong to it in the database
  and returns them."
  [wheel_id]
  (-> (ojm/query :items)
      (ojm/where {:wheel_id wheel_id})
      (oj/exec (env :db))))

(defn find-by-id
  "Given an id, finds the corresponding item in the database
  and returns it."
  [id]
  (-> (ojm/query :items)
      (ojm/where {:id id})
      (oj/exec (env :db))
      (first)))

(defn create
  "Create a new item with the given data. Does not validate, will throw
  DB error on integrity constraint violation."
  [item-data]
  (-> (ojm/query :items)
      (ojm/insert item-data)
      (oj/exec (env :db))))

(defn update
  "Given an id, updates that item with the supplied data. Does not validate,
  will throw DB error on integrity constraint violation."
  [id item-data]
  (-> (ojm/query :items)
      (ojm/where {:id id})
      (ojm/update item-data)
      (oj/exec (env :db))))
