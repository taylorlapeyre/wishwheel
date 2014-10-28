(ns wishwheel.controllers.users
  (:require [ring.util.response :refer [resource-response response]]
            [wishwheel.models.user :as user]))

(defn show
  [email]
  (let [user (first (user/find-by-email {:email email}))]
    (if (nil? user)
      {:status 404 :body "User does not exist"}
      (response user))))

(defn create
  [user-params]
  (try
    (user/validate user-params)
    (user/secure-insert! user-params)
    {:status 201 :body user-params}
    (catch java.lang.AssertionError e
      {:status 422 :body (.getMessage e)})))

(defn auth
  [email password]
  (let [maybe-user (user/authenticate email password)]
    (if maybe-user
      (response maybe-user)
      {:status 403 :body (str "Invalid credentials for email " email)})))
