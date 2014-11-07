(ns wishwheel.images
  "Logic for saving images to the filesystem."
  (:require [clojure.java.io :as io]
            [clojure.string :as string])
  (:import java.io.File))


(defn get-random-id [length]
  "Generate a random alphanumeric identifier of with a given length."
  (let [alphanumeric "abcdefghijklmnopqrstuvwxyz1234567890"]
    (apply str (repeatedly length #(rand-nth alphanumeric)))))

(defn random-file-name
  "Given an existing filename, returns a randomly generated file name
  using the same file extension. Files are located in:
  resources/public/images/"
  [filename]
  (let [file-extension (last (string/split filename #"\."))
        directory "resources/public/images/"]
    (str directory (get-random-id 10) "." file-extension)))

(defn image-store
  "Accepts a map with :filename and :stream keys, uploads the file, and
  returns the location of the file."
  [{:keys [filename stream]}]
  (let [filename (random-file-name filename)
        file (io/file filename)]
    (io/copy stream file)
    filename))
