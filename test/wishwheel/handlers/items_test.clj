(ns wishwheel.handlers.items-test
  (:use midje.sweet
        wishwheel.test-helpers)
  (:require [wishwheel.models.user :as user]
            [wishwheel.test-data :as data]
            [wishwheel.models.item :as item]
            [wishwheel.models.wheel :as wheel]
            [wishwheel.models.group :as group]))

(defn user-get
  [user-key]
  (if-let [the-user (user/find-by-email (:email data/user) {:with-token? true})]
    (get the-user user-key)
    (do
      (user/create data/user)
      (user-get user-key))))

(facts "About Item HTTP Handlers"
       (with-state-changes [(before :facts (reset-db!))]
         (fact "POST /api/wheels/:id/items"
               (group/create {:name "my group"
                              :user_id (user-get :id)})
               (wheel/create {:name "My Wheel"
                              :group_id (:id (group/find-last))
                              :user_id (user-get :id)})
               (let [response (hit-api :post (str "/api/wheels/" ))]))))
