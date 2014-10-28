(ns wishwheel.models.user
  (:require [yesql.core :refer [defqueries]]
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
  [user]
  (insert! (assoc user :password (bcrypt/encrypt (:password user)))))

(defn authenticate
  [email pswd]
  (let [user (first (find-by-email {:email email}))]
    (when (and user (bcrypt/check pswd (:password user)))
      user)))

(defn valid-token?
  [id token]
  (let [user (first (find-by-id {:id id}))]
    (when-not (nil? user)
      (= token (:token user)))))
