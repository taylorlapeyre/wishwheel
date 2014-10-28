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

(defn create
  [wheel-id item-params]
  (try
    (item/validate item-params)
    (item/insert! (merge {:wheel_id wheel-id} item-params))
    {:status 201 :body item-params}
    (catch java.lang.AssertionError e
      {:status 422 :body (.getMessage e)})))

(defn update
  [id item-data]
  (let [item (first (item/find-by-id {:id id}))]
    (if (nil? item)
      {:status 404 :body "Item does not exist"}
      (do
        (item/update! (merge {:id id} item-data))
        (let [item (first (item/find-by-id {:id id}))]
          (response item))))))
