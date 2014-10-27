(defproject wishwheel "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [yesql "0.5.0-beta2"]
                 [mysql/mysql-connector-java "5.1.32"]
                 [cheshire "5.3.1"]
                 [ring/ring-core "1.2.0"]
                 [ring/ring-json "0.2.0"]
                 [compojure "1.2.1"]
                 [prismatic/schema "0.3.1"]]
  :plugins [[lein-ring "0.7.1"]]
  :ring {:handler wishwheel.core/app})
