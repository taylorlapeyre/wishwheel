(ns wishwheel.handlers.users_test
  (:use expectations
        wishwheel.test-helpers)
  (:require [wishwheel.models.user :as user]
            [wishwheel.test-data :as data]))

(expect 201
  (:status (hit-api :post "/api/users" {:user data/user})))

(expect 403
  (:status (hit-api :post "/api/auth" {:email "non" :password "sense"})))

(expect (more-of {:keys [status body]}
                  200 status
                  (:email data/user) (body "email"))
  (do
    (user/create data/user)
    (hit-api (str "/api/users/" (:email data/user)))))

(expect "token"
  (do
    (user/create data/user)
    (in (->> (select-keys data/user [:email :password]) 
             (hit-api :post "/api/auth")
             (:body)
             (keys)))))