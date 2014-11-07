(ns wishwheel.images
  "Logic for saving images to the filesystem."
  (:require [clojure.java.io :as io]
            [clojure.string :as string])
  (:import java.io.File))


(defn get-random-id [length]
  "Generate a random alphanumeric identifier of with a given length."
  (let [alphanumeric "abcdefghijklmnopqrstuvwxyz1234567890"]
    (apply str (repeatedly length #(rand-nth alphanumeric)))))

(defn probably-unique-file-name
  "Given an existing filename, returns a randomly generated file name
  using the same file extension located in the given directory."
  [filename directory]
  (let [file-extension (last (string/split filename #"\."))]
    (str directory (get-random-id 10) "." file-extension)))

(defn image-store
  "Accepts a map with :filename and :stream keys, uploads the file, and
  returns the location of the file relative to the public folder."
  [{:keys [filename stream]}]
  (let [directory "resources/public/images/system/"
        filename (probably-unique-file-name filename directory)
        file (io/file filename)]
    (io/copy stream file)
    (string/replace filename "resources/public/" "")))
