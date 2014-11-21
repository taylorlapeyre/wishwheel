(ns wishwheel.handlers.groups_test
  (:use expectations
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

(expect 201
  (:status (hit-api :post "/api/groups" {:group {:name "My Group"}
                                         :token (user-get :token)})))

(expect
  {:name "horray"}
  (do
    (group/create {:name "horray" :user_id (user-get :id)})
    (let [group-id (:id (group/find-last))]
      (:body (hit-api :post (str "/api/groups/" group-id "/adduser")
               {:email (:email data/user)
                :token (user-get :token)})))))