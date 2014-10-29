(ns wishwheel.models.wheel
  (:require [yesql.core :refer [defqueries]]
            [wishwheel.config :refer [db]]
            [schema.core :as s]))

(defqueries "sql/wheels.sql"
  {:connection db})

(defn validate
  [wheel]
  (s/validate {:name     s/Str
               :group_id s/Int
               :user_id  s/Int} wheel))
