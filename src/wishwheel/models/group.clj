(ns wishwheel.models.group
  (:require [yesql.core :refer [defqueries]]
            [wishwheel.config :refer [db]]))

(defqueries "sql/groups.sql" {:connection db})

; crickets
