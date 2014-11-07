(ns wishwheel.images
  (:require [clojure.java.io :as io]
            [clojure.string :as string])
  (:import java.io.File))


(defn get-random-id [length]
  (let [alphanumeric "abcdefghijklmnopqrstuvwxyz1234567890"]
    (apply str (repeatedly length #(rand-nth alphanumeric)))))

(defn random-file-name
  [filename]
  (let [file-extension (last (string/split filename #"\."))
        directory "resources/public/images/"]
    (str directory (get-random-id 10) "." file-extension)))

(defn image-store
  [{:keys [filename stream]}]
  (let [filename (random-file-name filename)
        file (io/file filename)]
    (io/copy stream file)
    filename))
