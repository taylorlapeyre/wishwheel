(ns wishwheel.models.wheel
  (:require [yesql.core :refer [defqueries]]
            [wishwheel.config :refer [db]]))

(defqueries "sql/wheels.sql" {:connection db})

; crickets
