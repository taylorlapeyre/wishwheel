(ns wishwheel.handlers.groups
  (:require [ring.util.response :refer [response status not-found]]
            [wishwheel.models.group :as group]
            [wishwheel.models.user  :as user]))

(defn index
  "Given a user email, returns all groups that the user is a member of."
  [{:keys [params]}]
  (if-let [user (user/find-by-email (:email params))]
    (response (group/find-by-user (:user_id user)))
    (not-found "User does not exist")))

(defn show
  "Given a group id, returns a json representation of the group."
  [{:keys [params]}]
  (if-let [group (group/find-by-id (:id params))]
    (response (assoc group :users (user/find-by-group (:id params))))
    (not-found "Group does not exist")))

(defn create
  "Creates a new group."
  [{:keys [params body]}]
  (user/when-authenticated (:token body) (fn [api-user]
    (try (group/create (:group body))
         (status (response (:group body)) 201)
    (catch Exception e
      (status (response (.getMessage e)) 422))))))

(defn add-user
  "Relates a given group id and a user id in the table users_groups."
  [{:keys [params body]}]
  (user/when-authenticated (:token body) (fn [api-user]
    (if-let [group (group/find-by-id (:id params))]
      (do (group/add-user (:id params) (:user_id body))
          (response (group/find-by-id (:id params))))
      (not-found "Group does not exist")))))
