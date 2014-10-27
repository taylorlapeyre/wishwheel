(ns wishwheel.models.user
  (:require [yesql.core :refer [defqueries]]
            [wishwheel.config :refer [db]]
            [crypto.password.bcrypt :as password]
            [schema.core :as s]))

(defqueries "sql/users.sql"
  {:connection db})

(defn validate
  [user]
  (let [schema {:email      s/Str
                :password   s/Str
                :first_name s/Str
                :last_name  s/Str}]
    (s/validate schema user)))

(defn secure-insert!
  [user]
  (when (validate user)
    (insert! (assoc user :password (password/encrypt (:password user))))))

(defn authenticate
  [email pswd]
  (println "Authenticating " email)
  (let [user (first (find-by-email {:email email}))]
    (when (and (not (nil? user)) (password/check pswd (:password user)))
      user)))

(defn valid-token?
  [id token]
  (let [user (first (find-by-id {:id id}))]
    (when-not (nil? user)
      (= token (:token user)))))
