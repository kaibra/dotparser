(defproject kaibra/dotparser "0.0.3"
  :description "A tool to parse dotfiles into a clojure structure"
  :url "https://github.com/kaibra/dotparser"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]]
  :main ^:skip-aot dotparser.core
  :target-path "target/%s"
  :test-paths ["test" "test-resources"]
  :profiles {:uberjar {:aot :all}})
