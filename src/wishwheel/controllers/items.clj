(ns wishwheel.controllers.items
  (:require [ring.util.response :refer [resource-response response]]
            [wishwheel.models.item :as item]))

(defn show
  [id]
  (let [item (first (item/find-by-id {:id id}))]
    (if (nil? item)
      {:status 404 :body ""}
      (response item))))

(defn index
  [wheel-id]
  (let [items (item/find-by-wheel {:wheel_id wheel-id})]
    (response items)))
