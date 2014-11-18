(ns wishwheel.handlers.wheels
  " Wheel HTTP Handlers
    Clojure functions for handling HTTP requests to wheel resources.

    DATE      BY             CHANGE REF  DESCRIPTION
    ========  ==========     =========== =============
    11/9/14   Taylor Lapeyre 4a1e3c      get rid of stupid dumb validation stuff"
  (:require [ring.util.response :refer [response status not-found]]
            [wishwheel.models.wheel :as wheel]
            [wishwheel.models.user :as user]))

(defn show
  [id]
  "Returns a json representation of the wheel with a given id."
  (if-let [wheel (first (wheel/find-by-id {:id id}))]
    (response wheel)
    (not-found "Wheel does not exist")))

(defn create
  "Creates a new wheel."
  [token wheel-params]
  (user/when-authenticated token (fn [api-user]
    (try (wheel/insert! wheel-params)
      (let [created-wheel (first (wheel/find-by-id {:id (:id wheel-params)}))]
        (status (response created-wheel) 201))
    (catch java.lang.AssertionError e
      (status (response (.getMessage e)) 422))))))
