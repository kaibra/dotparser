(ns kaibra.access)

(defn node-seq [graph]
  (vals graph))

(defn- get-node-by-pred [graph pred]
  (->> (node-seq graph)
       (filter pred)
       (first)))

(defn- sub-graph-node-seq [graph pred-or-keyword]
  (let [{:keys [children id] :as node} (if (keyword? pred-or-keyword)
                                         (get graph pred-or-keyword)
                                         (get-node-by-pred graph pred-or-keyword))]
    (concat
      [[id node]]
      (mapcat #(sub-graph-node-seq (dissoc graph id) %) children))))

(defn sub-graph-for-node [graph pred-or-keyword]
  (into {} (sub-graph-node-seq graph pred-or-keyword)))


