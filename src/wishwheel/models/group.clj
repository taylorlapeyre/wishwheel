(ns wishwheel.models.group
  (:require [yesql.core :refer [defqueries]]
            [wishwheel.database :refer [db]]
            [schema.core :as s]))

(defqueries "sql/groups.sql"
  {:connection db})

(defn validate
  [group]
  (let [schema {:name     s/Str
                :user_id  s/Int}]
    (s/validate schema group)))
