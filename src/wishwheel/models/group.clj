(ns wishwheel.models.group
  (:require [yesql.core :refer [defqueries]]
            [wishwheel.config :refer [db]]
            [schema.core :as s]))

(defqueries "sql/groups.sql"
  {:connection db})

(defn validate
  [group]
  (s/validate {:name    s/Str
               :user_id s/Int} group))
