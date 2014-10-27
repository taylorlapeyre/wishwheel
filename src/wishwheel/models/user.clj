(ns wishwheel.models.user
  (:require [yesql.core :refer [defqueries]]
            [wishwheel.database :refer [db]]
            [schema.core :as s]))

(defqueries "sql/users.sql"
  {:connection db})

(defn validate
  [user]
  (let [schema {:email      s/Str
                :password   s/Str
                :first_name s/Str
                :last_name  s/Str}]
    (s/validate user schema)))
