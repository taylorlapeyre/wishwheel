(ns wishwheel.handlers.groups_test
  (:use midje.sweet
        wishwheel.test-helpers)
  (:require [wishwheel.models.group :as group]
            [wishwheel.models.user :as user]
            [wishwheel.test-data :as data]))

(defn user-get
  [user-key]
  (if-let [the-user (user/find-by-email (:email data/user) {:with-token? true})]
    (get the-user user-key)
    (do
      (user/create data/user)
      (user-get user-key))))

(facts "About Group HTTP handlers."
       (fact "POST /api/groups"
             (let [response (hit-api :post "/api/groups" {:group {:name "My Group"}
                                                          :token (user-get :token)})]
               (:status response) => 201))
       (fact "POST /api/groups/:id/adduser"
             (group/create {:name "Hello" :user_id (user-get :id)})
             (let [id (:id (group/find-last))
                   response (hit-api :post (str "/api/groups/" id "/adduser")
                                     {:email (:email data/user)
                                      :token (user-get :token)})]
               (:status response) => 200)))