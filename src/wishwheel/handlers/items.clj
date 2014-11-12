(ns wishwheel.handlers.items
  (:require [ring.util.response :refer [response status not-found]]
            [wishwheel.models.item  :as item]
            [wishwheel.models.user  :as user]
            [wishwheel.models.wheel :as wheel]))

(defn index
  "Given a valid wheel id, returns all items that belong to that wheel."
  [wheel-id]
  (if (wheel/find-by-id wheel-id)
    (response (item/find-by-wheel wheel-id))
    (not-found "Wheel does not exist.")))

(defn show
  "Returns a json representation of the item with a given id."
  [id]
  (if-let [item (item/find-by-id id)]
    (response item)
    (not-found "Item does not exist")))

(defn create
  "Creates a new item in a given wheel."
  [token wheel-id item-params]
  (user/when-authenticated token (fn [api-user]
    (let [item-params (merge {:wheel_id wheel-id} item-params)]
      (try (item/create item-params)
           (status (response item-params) 201)
      (catch java.lang.AssertionError e
        (status (response (.getMessage e)) 422)))))))

(defn update
  "Assigns an item to another user. Will only look for user_id in item-data."
  [token id item-data]
  (user/when-authenticated token (fn [api-user]
    (if-let [item (item/find-by-id id)]
      (do (item/update id item-data)
          (response (item/find-by-id id))
      (not-found "Item does not exist"))))))
