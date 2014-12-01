(ns wishwheel.db.core
  (:require [oj.core :as oj]
            [environ.core :refer [env]]
            [crypto.password.bcrypt :as bcrypt]))

(defn user
  [& forms]
  (let [query (reduce merge {:table :users} forms)]
    (oj/exec (env :db) query)))

(defn group
  [& forms]
  (let [query (reduce merge {:table :groups} forms)]
    (oj/exec (env :db) query)))

(defn item
  [& forms]
  (let [query (reduce merge {:table :items} forms)]
    (oj/exec (env :db) query)))

(defn wheel
  [& forms]
  (let [query (reduce merge {:table :wheels} forms)]
    (oj/exec query (env :db))))

(defn assoc-user-to-group
  [data]
  (let [query {:table :users_groups :insert data}]
    (oj/exec query (env :db))))

(defn authenticate
  [email pswd]
  (let [query {:table :users :where {:email email}}
        user (first (oj/exec query (env :db)))])
    (when (and user (bcrypt/check pswd (:password user)))
      (dissoc user :password)))