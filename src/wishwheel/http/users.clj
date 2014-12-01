(ns wishwheel.handlers.users
  (:require [ring.util.response :refer [response status not-found]]
            [wishwheel.db.core :as db]))

(defn show
  "Returns a json representation of the item with a given id."
  [{:keys [params]}]
  (if-let [user (db/user {:where {:email (:email params)}})]
    (response user)
    (not-found "User does not exist")))

(defn create
  "Creates a new user, encrypts their password, and generate their API token."
  [{:keys [body]}]
  (try (db/user {:insert (:user body)})
       (let [created-user (db/user {:where {:email (get-in body [:user :email])}})]
         (status (response created-user) 201))
       (catch java.lang.AssertionError e
         (status (response (.getMessage e)) 422))))

(defn auth
  "Given an email and password, finds the user with matching credentials."
  [{:keys [body]}]
  (if-let [authenticated-user (db/authenticate (:email body) (:password body))]
    (response authenticated-user)
    (status (response "Invalid credentials") 403)))
