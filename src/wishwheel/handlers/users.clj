(ns wishwheel.handlers.users
  " User HTTP Handlers
    Clojure functions for handling HTTP requests to users resources.

    DATE      BY             CHANGE REF  DESCRIPTION
    ========  ==========     =========== =============
    11/9/14   Taylor Lapeyre 4a1e3c      get rid of stupid dumb validation stuff"
  (:require [ring.util.response :refer [response status not-found]]
            [wishwheel.models.user :as user]))

(defn show
  "Returns a json representation of the item with a given id."
  [email]
  (if-let [user (first (user/find-by-email {:email email}))]
    (response user)
    (not-found "User does not exist")))

(defn create
  "Creates a new user, encrypts their password, and generate their API token."
  [user-params]
  (try (user/secure-insert! user-params)
    (let [created-user (first (user/find-by-email {:email (:email user-params)}))]
      (status (response created-user) 201))
  (catch java.lang.AssertionError e
    (status (response (.getMessage e)) 422))))

(defn auth
  "Given an email and password, finds the user with matching credentials."
  [email password]
  (if-let [authenticated-user (user/authenticate email password)]
    (response authenticated-user)
    (status (response "Invalid credentials") 403)))
