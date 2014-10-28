(defproject wishwheel "0.1.0-SNAPSHOT"
  :min-lein-version "2.0.0"
  :description "A simple news website written in Clojure."
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [yesql "0.5.0-beta2"]
                 [mysql/mysql-connector-java "5.1.32"]
                 [environ "0.5.0"]
                 [ring/ring-core "1.2.0"]
                 [ring/ring-jetty-adapter "1.3.1"]
                 [ring/ring-json "0.2.0"]
                 [crypto-password "0.1.3"]
                 [compojure "1.2.1"]
                 [prismatic/schema "0.3.1"]]
  :plugins [[lein-ring "0.7.1"]]
  :ring {:handler wishwheel.core/app}
  :main wishwheel.core)
