(ns leiningen.hash-assets
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [leiningen.core.main :as main])
  (:import [org.apache.commons.codec.digest DigestUtils]))

(defn- md5 [content] (DigestUtils/md5Hex content))

(defn- hash-files [source-root files]
  (loop [files (seq files)
         result (list)]
    (if-not files
      result
      (let [source (first files)
            content (slurp (str source-root "/" source))
            hash (md5 content)
            target (str/replace-first source "." (str "-" hash "."))]
        (recur (next files) (conj result [source target]))))))

(defn- replace-all [content replacements]
  (reduce (fn [s [match replacement]] (str/replace s match replacement)) content replacements))

(defn- file-exists? [path]
  (let [exists? (.isFile (io/file path))]
    (when-not exists?
      (main/warn (str "File \"" path "\" not found!")))
    exists?))

(defn- valid-configuration? [{:keys [source-root index files] :as configuration}]
  (let [files (conj files index)
        paths (map (fn [file] (str source-root "/" file)) files)
        valid? (every? file-exists? paths)]
    (when-not valid?
      (main/warn (str "Invalid configuration \"" configuration "\"!")))
    valid?))

(defn hash-assets
  "Add md5 hashes to the filenames of your static assets and use those in your index.html."
  [project]
  (let [configuration (:hash-assets project)]
    (when (valid-configuration? configuration)
      (let [{:keys [source-root target-root index files]} (:hash-assets project)
            hashed-files (hash-files source-root files)
            index-content (slurp (str source-root "/" index))
            target-index (str target-root "/" index)
            target-index-content (replace-all index-content hashed-files)]
        (io/make-parents target-index)
        (spit target-index target-index-content)
        (doseq [[original hashed] hashed-files
                :let [original-path (str source-root "/" original)]
                :let [hashed-path (str target-root "/" hashed)]]
          (io/make-parents hashed-path)
          (io/copy (io/file original-path) (io/file hashed-path)))))))
