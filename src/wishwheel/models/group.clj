(ns wishwheel.models.group
  (:require [oj.core :as oj]
            [oj.modifiers :as mods]
            [wishwheel.config :refer [db]]))

(defn find-by-id
  [id]
  (-> (mods/query :groups)
      (mods/where {:id id})
      (oj/exec db)
      (first)))

(defn create
  [group-data]
  (-> (mods/query :groups)
      (mods/insert group-data)
      (oj/exec db)))

(defn add-user
  [group_id user_id]
  (-> (mods/query :users_groups)
      (mods/insert {:user_id user_id :group_id group_id})
      (oj/exec db)))

(defn find-by-user
  [user_id]
  (let [memberships (-> (mods/query :users_groups)
                        (mods/where {:user_id user_id})
                        (oj/exec db))]
     (find-by-id (map :group_id memberships))))
