(ns wishwheel.models.user
  (:require [yesql.core :refer [defqueries]]
            [wishwheel.config :refer [db]]
            [wishwheel.models.wheel :as wheel]
            [crypto.password.bcrypt :as bcrypt]
            [schema.core :as s]))

(defqueries "sql/users.sql"
  {:connection db})

(defn validate
  [user]
  (s/validate {:email      s/Str
               :password   s/Str
               :first_name s/Str
               :last_name  s/Str} user))

(defn secure-insert!
  "Encrypt the user's password and insert the resulting information"
  [user]
  (insert! (assoc user :password (bcrypt/encrypt (:password user)))))

(defn authenticate
  "Given an email and password, finds the user with matching credentials."
  [email pswd]
  (when-let [user (first (find-by-email {:email email}))]
    (when (bcrypt/check pswd (:password user))
      user)))

(defn valid-token?
  "Given a user id and api token, finds the user with the id and determines
  if the given token matches."
  [id token]
  (when-let [user (first (find-by-id {:id id}))]
    (= token (:token user))))

(defn when-authenticated
  "Finds a user with a matching api token and passes it into the succcess
  function. If the user is not found, returns a 403."
  [token success-fn]
  (if-let [api-user (first (find-by-token {:token token}))]
    (success-fn api-user)
    {:status 403 :body "Unauthorized"}))

(defn can-be-assigned-to-item?
  "Returns true if the group that the item's wheel belongs to includes
  the given user."
  [user item]
  (let [item-wheel (first (wheel/find-by-id {:id (:wheel_id item)}))
        users-in-wheel-group (into #{}
                              (find-by-group {:group_id (:group_id item-wheel)}))]
    (contains? users-in-wheel-group user)))
