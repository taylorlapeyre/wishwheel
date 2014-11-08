(ns wishwheel.models.item
  (:require [oj.core :as oj]
            [oj.modifiers :as mods]
            [wishwheel.config :refer [db]]))

(defn find-by-wheel
  [wheel_id]
  (-> (mods/query :items)
      (mods/where {:wheel_id wheel_id})
      (oj/exec db)))

(defn find-by-id
  [id]
  (-> (mods/query :items)
      (mods/where {:id id})
      (oj/exec db)
      (first)))

(defn create
  [item-data]
  (-> (mods/query :items)
      (mods/insert item-data)
      (oj/exec db)))

(defn update
  [id item-data]
  (-> (mods/query :items)
      (mods/where {:id id})
      (mods/update item-data)
      (oj/exec db)))
