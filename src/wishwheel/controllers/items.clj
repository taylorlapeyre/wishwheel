(ns wishwheel.controllers.items
  (:require [ring.util.response :refer [resource-response response]]
            [wishwheel.models.item :as item]))

(defn index
  "Given a valid wheel id, returns all items that belong to that wheel."
  [wheel-id]
  (let [items (item/find-by-wheel {:wheel_id wheel-id})]
    (response items)))

(defn show
  "Returns a json representation of the item with a given id."
  [id]
  (let [item (first (item/find-by-id {:id id}))]
    (if (nil? item)
      {:status 404 :body "Item does not exist"}
      (response item))))

(defn create
  "Creates a new item in a given wheel."
  [wheel-id item-params]
  (try
    (item/validate item-params)
    (item/insert! (merge {:wheel_id wheel-id} item-params))
    {:status 201 :body item-params}
    (catch java.lang.AssertionError e
      {:status 422 :body (.getMessage e)})))

(defn update
  "Assigns an item to another user. Will only look for user_id in item-data."
  [id item-data]
  (let [item (first (item/find-by-id {:id id}))]
    (if (nil? item)
      {:status 404 :body "Item does not exist"}
      (do
        (item/assign-user! {:id id :user_id (:user_id item-data))
        (let [item (first (item/find-by-id {:id id}))]
          (response item))))))
