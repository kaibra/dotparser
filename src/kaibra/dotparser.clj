(ns kaibra.dotparser
  (:require [clojure.string :as string]))

(defn- add-parsed-edges [nodes graph-line graph-type]
  (or
    (case graph-type
      :digraph (when-let [[_ start-node child-path end-node] (seq (re-matches #"\s*(\w+)\s+->\s((\w+).*)" graph-line))]
                 (add-parsed-edges
                   (update-in nodes [(keyword start-node) :children] conj (keyword end-node))
                   child-path
                   graph-type))
      nodes)
    nodes))

(defn- without-quotes [v]
  (string/replace v "\"" ""))

(defn- parse-attributes [graph-attributes]
  (if-let [[_ kvs] (seq (re-matches #"\s*(.+)\s*" (or graph-attributes "")))]
    (->> (string/split kvs #" ")
         (map #(string/split % #"\="))
         (reduce (fn [r [n v]]
                   (assoc r (keyword n) (without-quotes v))) {}))
    {}))

(defn- add-parsed-nodes [nodes graph-line graph-type]
  (or
    (case graph-type
      :digraph (when-let [[_ graph-id _ graph-attributes] (seq (re-matches #"\s*([^\s]+)\s+(\[(.*)\])?\s*" graph-line))]
                 (assoc nodes
                   (keyword graph-id)
                   (into {:children []
                          :id       (keyword graph-id)} (parse-attributes graph-attributes))))
      nodes)
    nodes))

(defn- parse-graph [graph-str graph-type]
  (loop [lines (string/split graph-str #";")
         nodes {}]
    (if (empty? lines)
      nodes
      (recur
        (rest lines)
        (-> nodes
            (add-parsed-edges (first lines) graph-type)
            (add-parsed-nodes (first lines) graph-type))))))

(defn parse-dotfile-str [dot-str]
  (when dot-str
    (let [without-linebreks (string/replace dot-str "\n" "")]
      (or
        (when-let [[_ _ diagraph] (seq (re-matches #"\s*digraph\s*([^\s]+)\s*\{(.*)\}\s*" without-linebreks))]
          (parse-graph diagraph :digraph))
        {}))))
