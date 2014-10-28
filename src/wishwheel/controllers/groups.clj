(ns wishwheel.controllers.groups
  (:require [ring.util.response :refer [resource-response response]]
            [wishwheel.models.group :as group]
            [wishwheel.models.user  :as user]))

(defn index
  [user-id]
  (let [groups (group/find-by-user {:user_id user-id})]
    (response groups)))

(defn show
  [id]
  (let [group (first (group/find-by-id {:id id}))]
    (if (nil? group)
      {:status 404 :body "Group does not exist"}
      (response (assoc group :users (user/find-by-group {:group_id id}))))))

(defn add-user
  [id user_id]
  (let [group (first (group/find-by-id {:id id}))]
    (if (nil? group)
      {:status 404 :body "Group does not exist"}
      (do
        (group/add-user! {:id id :user_id user_id})
        (let [group (first (group/find-by-id {:id id}))]
          (response group))))))
