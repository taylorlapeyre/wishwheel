(ns wishwheel.controllers.items
  (:require [ring.util.response :refer [resource-response response]]
            [wishwheel.models.item :as item]))

(defn index
  [wheel-id]
  (let [items (item/find-by-wheel {:wheel_id wheel-id})]
    (response items)))

(defn show
  [id]
  (let [item (first (item/find-by-id {:id id}))]
    (if (nil? item)
      {:status 404 :body "Item does not exist"}
      (response item))))

(defn update
  [id item-data]
  (let [item (first (item/find-by-id {:id id}))]
    (if (nil? item)
      {:status 404 :body "Item does not exist"}
      (do
        (item/update! (merge {:id id} item-data))
        (let [item (first (item/find-by-id {:id id}))]
          (response item))))))
