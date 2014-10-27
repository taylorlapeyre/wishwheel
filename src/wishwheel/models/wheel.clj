(ns wishwheel.models.wheel
  (:require [yesql.core :refer [defqueries]]
            [wishwheel.database :refer [db]]
            [schema.core :as s]))

(defqueries "sql/wheels.sql"
  {:connection db})

(defn validate
  [wheel]
  (let [schema {:name     s/Str
                :group_id s/Int
                :user_id  s/Int}]
    (s/validate wheel schema)))
