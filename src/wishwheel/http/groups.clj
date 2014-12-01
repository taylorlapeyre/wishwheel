(ns wishwheel.http.groups
  (:require [ring.util.response :refer [response status not-found]]
            [wishwheel.db.core :as db]
            [wishwheel.http.core :as http]))

(defn index
  "Given a user email, returns all groups that the user is a member of."
  [{:keys [params]}]
  (http/respond [user  (db/user {:where {:email (:email params)}}) 404
                 group (db/group {:where {:user_id (:id user)}})   404]
    (response group)))

(defn show
  "Given a group id, returns a json representation of the group."
  [{:keys [params]}]

  (http/respond [group (db/group {:where {:id (:id params)}}) 404
                 users (db/user {:where {:id (:id params)}})  404]
    (response (assoc group :users users))))

(defn create
  "Creates a new group"
  [{:keys [body]}]
  (http/respond [api-user (db/user {:where {:token (:token body)}}) 403
                 group-data (:group body) 422]
    (try (db/group {:insert (assoc group-data :user_id (:id api-user))})
         (status (response (:group body)) 201)
         (catch Exception e
           (status (response "Something went wrong.") 422)))))

(defn add-user
  "Relates a given group id and a user id in the table users_groups."
  [{:keys [params body]}]
  (http/respond [api-user (db/user {:where {:token (:token body)}}) 403
                 group    (db/group {:where {:id (:id params)}})    404
                 the-user (db/user {:where {:email (:email body)}}) 404]
    (db/assoc-user-to-group {:user_id (:id the-user) :group_id (:id group)})
    (response "Great job.")))