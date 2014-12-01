(ns wishwheel.handlers.items
  (:require [ring.util.response :refer [response status not-found]]
            [wishwheel.db.core :as db]))

(defn index
  "Given a valid wheel id, returns all items that belong to that wheel."
  [{:keys [params]}]
  (if (db/wheel {:where {:id (:id params)}})
    (response (db/item {:where {:id (:wheel-id params)}})
    (not-found "Wheel does not exist."))))

(defn show
  "Returns a json representation of the item with a given id."
  [{:keys [params]}]
  (if-let [item (db/item {:where {:id (:id params)}})]
    (response item)
    (not-found "Item does not exist")))

(defn create
  "Creates a new item in a given wheel."
  [{:keys [params body]}]
  (if-let [api-user (db/user {:where {:token (:token body)}})]
    (let [item-params (assoc (:item body) :wheel_id (:wheel-id params))]
      (try (db/item {:insert item-params})
           (status (response item-params) 201)
           (catch java.lang.AssertionError e
             (status (response (.getMessage e)) 422))))
    (status (response "Invalid API token.") 403)))

(defn update
  "Assigns an item to another user. Will only look for user_id in item-data."
  [{:keys [params body]}]
  (if-let [api-user (db/user {:where {:token (:token body)}})]
    (if-let [item (db/item {:where {:id (:id params)}})]
      (do (db/item {:where {:id (:id params)}
                    :update (:item body)})
          (response (item/find-by-id (:id params))))
      (not-found "Item does not exist"))
    (status (response "Invalid API token.") 403)))
