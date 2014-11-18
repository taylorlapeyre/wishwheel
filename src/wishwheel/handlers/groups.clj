(ns wishwheel.handlers.groups
  " Group HTTP Handlers
    Clojure functions for handling HTTP requests to group resources.

    DATE      BY             CHANGE REF  DESCRIPTION
    ========  ==========     =========== =============
    11/9/14   Taylor Lapeyre 4a1e3c      get rid of stupid dumb validation stuff"
  (:require [ring.util.response :refer [response status not-found]]
            [wishwheel.models.group :as group]
            [wishwheel.models.user  :as user]))

(defn index
  "Given a user email, returns all groups that the user is a member of."
  [email]
  (if-let [user (first (user/find-by-email {:email email}))]
    (response (group/find-by-user {:user_id (:user_id user)}))
    (not-found "User does not exist")))

(defn show
  "Given a group id, returns a json representation of the group."
  [id]
  (if-let [group (first (group/find-by-id {:id id}))]
    (response (assoc group :users (user/find-by-group {:group_id id})))
    (not-found "Group does not exist")))

(defn create
  "Creates a new group."
  [token group-data]
  (user/when-authenticated token (fn [api-user]
    (try (group/insert! group-data)
      (status (response group-data) 201)
    (catch Exception e
      (status (response (.getMessage e)) 422))))))

(defn add-user
  "Relates a given group id and a user id in the table users_groups."
  [token id user_id]
  (user/when-authenticated token (fn [api-user]
    (if-let [group (first (group/find-by-id {:id id}))]
      (do
        (group/add-user! {:id id :user_id user_id})
        (response (first (group/find-by-id {:id id}))))
      (not-found "Group does not exist")))))
