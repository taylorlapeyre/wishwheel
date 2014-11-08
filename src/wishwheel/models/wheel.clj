(ns wishwheel.models.wheel
  (:require [oj.core :as oj]
            [oj.modifiers :as mods]
            [wishwheel.config :refer [db]]))

(defn find-by-user
  [user-id]
  (-> (mods/query :wheels)
      (mods/where {:user_id user-id})
      (oj/exec db)))

(defn find-by-group
  [group-id]
  (-> (mods/query :wheels)
      (mods/where {:group_id group-id})
      (oj/exec db)))

(defn find-by-id
  [id]
  (-> (mods/query :wheels)
      (mods/where {:id id})
      (oj/exec db)
      (first)))

(defn create
  [wheel-data]
  (-> (mods/query :wheels)
      (mods/insert wheel-data)
      (oj/exec db)))
