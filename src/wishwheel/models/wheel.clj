(ns wishwheel.models.wheel
  " Wheel Model
    Clojure functions that are used to interact with the wheels table.

    DATE      BY             CHANGE REF  DESCRIPTION
    ========  ==========     =========== =============
    11/9/14   Taylor Lapeyre 4a1e3c      get rid of stupid dumb validation stuff"
  (:require [yesql.core :refer [defqueries]]
            [wishwheel.config :refer [db]]))

(defqueries "sql/wheels.sql" {:connection db})

; crickets
