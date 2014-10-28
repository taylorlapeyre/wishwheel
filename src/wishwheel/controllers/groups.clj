(ns wishwheel.controllers.groups
  (:require [ring.util.response :refer [resource-response response]]
            [wishwheel.models.group :as group]
            [wishwheel.models.user  :as user]))

(defn index
  "Given a user email, returns all groups that the user is a member of."
  [email]
  (let [user (first (user/find-by-email {:email email}))]
    (if (nil? user)
      {:status 404 :body "User does not exist"}
      (let [groups (group/find-by-user {:user_id (:user_id user)})]
        (response groups)))))

(defn show
  "Given a group id, returns a json representation of the group."
  [id]
  (let [group (first (group/find-by-id {:id id}))]
    (if (nil? group)
      {:status 404 :body "Group does not exist"}
      (response (assoc group :users (user/find-by-group {:group_id id}))))))

(defn create
  "Creates a new group."
  [token group-params]
  (user/when-authenticated token (fn [api-user]
    (try
      (group/validate group-params)
      (group/insert! group-params)
      {:status 201 :body group-params}
      (catch Exception e
        {:status 422 :body (.getMessage e)})))))

(defn add-user
  "Relates a given group id and a user id in the table users_groups."
  [token id user_id]
  (user/when-authenticated token (fn [api-user]
    (let [group (first (group/find-by-id {:id id}))]
      (if (nil? group)
        {:status 404 :body "Group does not exist"}
        (do
          (group/add-user! {:id id :user_id user_id})
          (response (first (group/find-by-id {:id id})))))))))
