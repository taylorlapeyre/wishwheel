(ns wishwheel.models.user
  (:require [oj.core :as oj]
            [oj.modifiers :as mods]
            [wishwheel.config :refer [db]]
            [wishwheel.models.wheel :as wheel]
            [crypto.password.bcrypt :as bcrypt]))

(def safe-cols
  "Columns that don't contain sensitive information, such as passwords
  or API tokens."
  [:id :email :first_name :last_name])

(defn find-by-email
  ([email]
    (find-by-email email {:with-token? false}))
  ([email {:keys [with-token?]}]
    (let [cols (if-not with-token? safe-cols (conj safe-cols :token))]
      (-> (mods/query :users)
          (mods/select cols)
          (mods/where {:email email})
          (oj/exec db)
          (first)))))

(defn find-by-token
  [token]
  (-> (mods/query :users)
      (mods/select safe-cols)
      (mods/where {:token token})
      (oj/exec db)
      (first)))

(defn find-by-id
  [id]
  (-> (mods/query :users)
      (mods/select safe-cols)
      (mods/where {:id id})
      (oj/exec db)
      (first)))

(defn find-by-group
  [group_id]
  (let [memberships (-> (mods/query :users_groups)
                        (mods/where {:group_id group_id})
                        (oj/exec db))]
     (find-by-id (map :user_id memberships))))

(defn create
  [user-data]
  (let [user-data (assoc user-data :password (bcrypt/encrypt (:password user-data)))]
    (-> (mods/query :users)
        (mods/insert user-data)
        (oj/exec db))))

(defn authenticate
  "Given an email and password, finds the user with matching credentials."
  [email pswd]
  (when-let [user (-> (mods/query :users)
                      (mods/select (conj safe-cols :password :token))
                      (mods/where {:email email})
                      (oj/exec db)
                      (first))]
    (when (bcrypt/check pswd (:password user))
      (dissoc user :password))))

(defn valid-token?
  "Given a user id and api token, finds the user with the id and determines
  if the given token matches."
  [id token]
  (when-let [user (-> (mods/query :users)
                      (mods/select [:token])
                      (mods/where {:id id})
                      (oj/exec db)
                      (first))]
    (= token (:token user))))

(defn when-authenticated
  "Finds a user with a matching api token and passes it into the succcess
  function. If the user is not found, returns a 403."
  [token success-fn]
  (if-let [api-user (find-by-token token)]
    (success-fn api-user)
    {:status 403 :body "Unauthorized"}))

(defn can-be-assigned-to-item?
  "Returns true if the group that the item's wheel belongs to includes
  the given user."
  [user item]
  (let [item-wheel (wheel/find-by-id (:wheel_id item))
        users-in-wheel-group (into #{} (find-by-group (:group_id item-wheel)))]
    (contains? users-in-wheel-group user)))
