(ns kaibra.access-test
  (:require [kaibra.access :as acc]
            [clojure.test :refer :all]))

(deftest sub-graph
  (let [graph {:1 {:id       :1
                   :label    "A"
                   :foo      "bar"
                   :children [:2]}
               :2 {:id       :2
                   :label    "B"
                   :bar      "baz"
                   :children [:3 :4]}
               :3 {:id       :3
                   :label    "C"
                   :children []}
               :4 {:id       :4
                   :children [:5]}
               :5 {:id       :5
                   :baf      "bif"
                   :bif      "bum"
                   :children []}}]
    (testing "should build sub-graph from keyword"
      (is (= {:2 {:id       :2
                  :label    "B"
                  :bar      "baz"
                  :children [:3 :4]}
              :3 {:id       :3
                  :label    "C"
                  :children []}
              :4 {:id       :4
                  :children [:5]}
              :5 {:id       :5
                  :baf      "bif"
                  :bif      "bum"
                  :children []}}
             (acc/sub-graph-for-node graph :2)))
      (is (= {:3 {:id       :3
                  :label    "C"
                  :children []}}
             (acc/sub-graph-for-node graph :3)))
      (is (= {:4 {:id       :4
                  :children [:5]}
              :5 {:id       :5
                  :baf      "bif"
                  :bif      "bum"
                  :children []}}
             (acc/sub-graph-for-node graph :4))))

    (testing "should build sub-graph from node-predicate"
      (is (= {:2 {:id       :2
                  :label    "B"
                  :bar      "baz"
                  :children [:3 :4]}
              :3 {:id       :3
                  :label    "C"
                  :children []}
              :4 {:id       :4
                  :children [:5]}
              :5 {:id       :5
                  :baf      "bif"
                  :bif      "bum"
                  :children []}}
             (acc/sub-graph-for-node graph #(= "B" (:label %)))))
      (is (= {:3 {:id       :3
                  :label    "C"
                  :children []}}
             (acc/sub-graph-for-node graph #(= "C" (:label %)))))
      (is (= {:4 {:id       :4
                  :children [:5]}
              :5 {:id       :5
                  :baf      "bif"
                  :bif      "bum"
                  :children []}}
             (acc/sub-graph-for-node graph #(= :4 (:id %))))))))

(deftest nodeq-seq
  (testing "should build node-seq from graph"
    (let [graph {:1 {:id       :1
                     :label    "A"
                     :foo      "bar"
                     :children [:2]}
                 :2 {:id       :2
                     :label    "B"
                     :bar      "baz"
                     :children [:3 :4]}
                 :3 {:id       :3
                     :label    "C"
                     :children []}
                 :4 {:id       :4
                     :children [:5]}
                 :5 {:id       :5
                     :baf      "bif"
                     :bif      "bum"
                     :children []}}]
      (is (= [{:id       :1
               :label    "A"
               :foo      "bar"
               :children [:2]}
              {:id       :2
               :label    "B"
               :bar      "baz"
               :children [:3 :4]}
              {:id       :3
               :label    "C"
               :children []}
              {:id       :4
               :children [:5]}
              {:id       :5
               :baf      "bif"
               :bif      "bum"
               :children []}]
             (acc/node-seq graph))))))
