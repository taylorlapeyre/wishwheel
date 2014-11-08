(ns wishwheel.handlers.groups
  (:require [ring.util.response :refer [response status not-found]]
            [wishwheel.models.group :as group]
            [wishwheel.models.user  :as user]))

(defn index
  "Given a user email, returns all groups that the user is a member of."
  [email]
  (if-let [user (user/find-by-email email)]
    (response (group/find-by-user (:user_id user)))
    (not-found "User does not exist")))

(defn show
  "Given a group id, returns a json representation of the group."
  [id]
  (if-let [group (group/find-by-id id)]
    (response (assoc group :users (user/find-by-group id)))
    (not-found "Group does not exist")))

(defn create
  "Creates a new group."
  [token group-data]
  (user/when-authenticated token (fn [api-user]
    (try (group/create group-data)
         (status (response group-data) 201)
    (catch Exception e
      (status (response (.getMessage e)) 422))))))

(defn add-user
  "Relates a given group id and a user id in the table users_groups."
  [token id user_id]
  (user/when-authenticated token (fn [api-user]
    (if-let [group (group/find-by-id id)]
      (do (group/add-user id user_id)
          (response (group/find-by-id id)))
      (not-found "Group does not exist")))))
