(ns wishwheel.handlers.users_test
  (:use midje.sweet
        wishwheel.test-helpers)
  (:require [wishwheel.models.user :as user]
            [wishwheel.test-data :as data]))

(facts "About User HTTP Handlers"
       (with-state-changes [(before :facts (reset-db!))]
         (fact "POST /api/users"
               (let [response (hit-api :post "/api/users" {:user data/user})]
                 (:status response) => 201))
         (fact "POST /api/auth"
               (let [response (hit-api :post "/api/auth" {:email "non" :password "sense"})]
                 (:status response) => 403))
         (fact "GET /api/users/:email"
               (user/create data/user)
               (let [response (hit-api (str "/api/users/" (:email data/user)))]
                 (:status response) => 200
                 (get-in response [:body "email"]) => (:email data/user)))
         (fact "POST /api/auth"
               (user/create data/user)
               (let [data (select-keys data/user [:email :password])
                     response (hit-api :post "/api/auth" data)]
                 (:status response) => 200
                 (contains? (set (keys (:body response))) "token") => true))))