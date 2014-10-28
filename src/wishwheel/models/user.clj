(ns wishwheel.models.user
  (:require [yesql.core :refer [defqueries]]
            [wishwheel.config :refer [db]]
            [wishwheel.models.wheel :as wheel]
            [wishwheel.config :refer [db]]
            [crypto.password.bcrypt :as bcrypt]
            [schema.core :as s]))

(defqueries "sql/users.sql"
  {:connection db})

(def schema {:email      s/Str
             :password   s/Str
             :first_name s/Str
             :last_name  s/Str})

(defn validate
  [user]
  (s/validate schema user))

(defn secure-insert!
  "Encrypt the user's password and insert the resulting information"
  [user]
  (insert! (assoc user :password (bcrypt/encrypt (:password user)))))

(defn authenticate
  "Given an email and password, finds the user with matching credentials."
  [email pswd]
  (let [user (first (find-by-email {:email email}))]
    (when (and user (bcrypt/check pswd (:password user)))
      user)))

(defn valid-token?
  "Given a user id and api token, finds the user with the id and determines
  if the given token matches."
  [id token]
  (let [user (first (find-by-id {:id id}))]
    (when user
      (= token (:token user)))))

(defn when-authenticated [token success-fn]
  (let [api-user (first (find-by-token {:token token}))]
    (if-not api-user
      {:status 403 :body "Unauthorized"}
      (success-fn api-user))))

(defn can-be-assigned-to-item?
  "Returns true if the group that the item's wheel belongs to includes
  the given user."
  [user item]
  (let [item-wheel (first (wheel/find-by-id {:id (:wheel_id item)}))
        users-in-wheel-group (into #{}
                              (find-by-group {:group_id (:group_id item-wheel)}))]
    (contains? users-in-wheel-group user)))
