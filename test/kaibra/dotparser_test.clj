(ns kaibra.dotparser-test
  (:require [clojure.test :refer :all]
            [kaibra.dotparser :as dotparser]))

(deftest parsing-a-dotfile
  (testing "should parse a dotfile"
    (is (= {:1 {:id       :1
                :label    "A"
                :foo      "bar"
                :children [:2]}
            :2 {:id       :2
                :label    "B"
                :children [:3]}
            :3 {:id       :3
                :label    "C"
                :children []}}
           (dotparser/parse-dotfile-str (slurp "test-resources/digraph/digraph1.dot")))))

  (testing "should parse a dotfile"
    (is (= {:1 {:id       :1
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
                :children []}}
           (dotparser/parse-dotfile-str (slurp "test-resources/digraph/digraph2.dot"))))))



