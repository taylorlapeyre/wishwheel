(ns wishwheel.models.group
  " Group Model
    Clojure functions that are used to interact with the users table.

    DATE      BY             CHANGE REF  DESCRIPTION
    ========  ==========     =========== =============
    11/9/14   Taylor Lapeyre 4a1e3c      get rid of stupid dumb validation stuff"
  (:require [yesql.core :refer [defqueries]]
            [wishwheel.config :refer [db]]))

(defqueries "sql/groups.sql" {:connection db})

; crickets
