(ns wishwheel.models.item
  (:require [yesql.core :refer [defqueries]]
            [wishwheel.config :refer [db]]
            [schema.core :as s]))

(defqueries "sql/items.sql"
  {:connection db})

(defn validate
  [item]
  (s/validate {:name     s/Str
               :price    s/Num
               :wheel_id s/Int
               (s/optional-key :user_id) s/Int} item))
