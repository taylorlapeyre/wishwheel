(ns wishwheel.models.item
  (:require [yesql.core :refer [defqueries]]
            [wishwheel.config :refer [db]]))

(defqueries "sql/items.sql" {:connection db})

; crickets
