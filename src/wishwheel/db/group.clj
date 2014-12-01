(ns wishwheel.db.group
  "Functions for interfacing with the `groups` table in the database."
  (:require [oj.core :as oj]
            [oj.modifiers :refer [query select where insert update limit order]]
            [environ.core :refer [env]]))

(defn find-by-id
  "Given an id, finds the corresponding group in the database
  and returns it."
  [id]
  (-> (query :groups)
      (where {:id id})
      (oj/exec (env :db))
      (first)))

(defn create
  "Create a new group with the given data. Does not validate, will throw
  DB error on integrity constraint violation."
  [group-data]
  (-> (query :groups)
      (insert group-data)
      (oj/exec (env :db))))

(defn add-user
  "Create a new group with the given data. Does not validate, will throw
  DB error on integrity constraint violation."
  [group_id user_id]
  (-> (query :users_groups)
      (insert {:user_id user_id :group_id group_id})
      (oj/exec (env :db))))

(defn find-by-user
  "Given a user id, finds all groups that the user is a member of."
  [user_id]
  (let [memberships (-> (query :users_groups)
                        (where {:user_id user_id})
                        (oj/exec (env :db)))]
     (find-by-id (map :group_id memberships))))
