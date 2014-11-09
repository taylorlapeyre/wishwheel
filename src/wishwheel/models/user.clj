(ns wishwheel.models.user
  "Functions for interfacing with the `users` table in the database."
  (:require [oj.core :as oj]
            [oj.modifiers :refer [query select where insert update]]
            [wishwheel.config :refer [db]]
            [wishwheel.models.wheel :as wheel]
            [crypto.password.:refer [encrypt check]]))

(def safe-cols
  "Columns that don't contain sensitive information, such as passwords
  or API tokens."
  [:id :email :first_name :last_name])

(defn find-by-email
  "Given an email address, finds the corresponding user in the database
  and returns it."
  ([email]
    (find-by-email email {:with-token? false}))
  ([email {:keys [with-token?]}]
    (let [cols (if-not with-token? safe-cols (conj safe-cols :token))]
      (-> (query :users)
          (select cols)
          (where {:email email})
          (oj/exec db)
          (first)))))

(defn find-by-token
  "Given an token, finds the corresponding user in the database
  and returns it."
  [token]
  (-> (query :users)
      (select safe-cols)
      (where {:token token})
      (oj/exec db)
      (first)))

(defn find-by-id
  "Given an id, finds the corresponding user in the database
  and returns it."
  [id]
  (-> (query :users)
      (select safe-cols)
      (where {:id id})
      (oj/exec db)
      (first)))

(defn find-by-group
  "Given a group id, finds all users that are members of the corresponding
  group."
  [group_id]
  (let [memberships (-> (query :users_groups)
                        (where {:group_id group_id})
                        (oj/exec db))]
     (find-by-id (map :user_id memberships))))

(defn create
  "Create a new user with the given data. Does not validate, will throw
  DB error on integrity constraint violation. Will encrypt their password."
  [user-data]
  (let [user-data (assoc user-data :password (encrypt (:password user-data)))]
    (-> (query :users)
        (insert user-data)
        (oj/exec db))))

(defn authenticate
  "Given an email and password, finds the user with matching credentials."
  [email pswd]
  (when-let [user (-> (query :users)
                      (select (conj safe-cols :password :token))
                      (where {:email email})
                      (oj/exec db)
                      (first))]
    (when (check pswd (:password user))
      (dissoc user :password))))

(defn valid-token?
  "Given a user id and api token, finds the user with the id and determines
  if the given token matches."
  [id token]
  (when-let [user (-> (query :users)
                      (select [:token])
                      (where {:id id})
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
