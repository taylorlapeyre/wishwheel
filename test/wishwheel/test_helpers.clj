(ns wishwheel.test-helpers
  (:require [wishwheel.core :refer :all]
            [ring.mock.request :as mock]
            [cheshire.core :as json]
            [environ.core :refer [env]]
            [oj.core :as oj]))

(defn reset-db []
  (oj/exec {:table :users_groups :delete :all} (env :db))
  (oj/exec {:table :groups :delete :all} (env :db))
  (oj/exec {:table :users  :delete :all} (env :db))
  (oj/exec {:table :items  :delete :all} (env :db))
  (oj/exec {:table :wheels :delete :all} (env :db)))

(defn before-each
  {:expectations-options :in-context}
  [test-fn]
  (test-fn)
  (reset-db))

(defn hit-api
  ([url]
    (hit-api :get url nil))
  ([method url]
    (hit-api method url nil))
  ([method url data]
    (let [json-data (json/encode data)
          mock-request (-> (mock/request method url)
                           (mock/body json-data)
                           (mock/content-type "application/json"))]
      (let [response (app mock-request)]
        (try
          (update-in response [:body] json/decode)
          (catch Exception e
            response))))))