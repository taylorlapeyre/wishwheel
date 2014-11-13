(ns wishwheel.handlers.items
  (:require [ring.util.response :refer [response status not-found]]
            [wishwheel.models.item  :as item]
            [wishwheel.models.user  :as user]
            [wishwheel.models.wheel :as wheel]))

(defn index
  "Given a valid wheel id, returns all items that belong to that wheel."
  [{:keys [params]}]
  (if (wheel/find-by-id (:wheel-id params))
    (response (item/find-by-wheel (:wheel-id params)))
    (not-found "Wheel does not exist.")))

(defn show
  "Returns a json representation of the item with a given id."
  [{:keys [params]}]
  (if-let [item (item/find-by-id (:id params))]
    (response item)
    (not-found "Item does not exist")))

(defn create
  "Creates a new item in a given wheel."
  [{:keys [params body]}]
  (user/when-authenticated (:token body) (fn [api-user]
    (let [item-params (assoc (:item body) :wheel_id (:wheel-id params))]
      (try (item/create item-params)
           (status (response item-params) 201)
      (catch java.lang.AssertionError e
        (status (response (.getMessage e)) 422)))))))

(defn update
  "Assigns an item to another user. Will only look for user_id in item-data."
  [{:keys [params body]}]
  (user/when-authenticated (:token body) (fn [api-user]
    (if-let [item (item/find-by-id (:id params))]
      (do (item/update (:id params) (:item body))
          (response (item/find-by-id (:id params))))
      (not-found "Item does not exist")))))
