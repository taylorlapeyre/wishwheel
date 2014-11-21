(defproject wishwheel "0.2.0"
  :min-lein-version "2.0.0"
  :description "A gift sharing website."
  :url "https://github.com/taylorlapeyre/wishwheel"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :main wishwheel.core
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [mysql/mysql-connector-java "5.1.32"]
                 [http-kit "2.1.16"]
                 [ring/ring-core "1.3.1"]
                 [ring/ring-json "0.3.1"]
                 [oj "0.1.9"]
                 [nav "0.1.0"]
                 [environ "0.5.0"]
                 [crypto-password "0.1.3"]]
  :plugins [[lein-ring "0.7.1"]
            [codox "0.8.10"]
            [lein-environ "1.0.0"]
            [lein-expectations "0.0.7"]]
  :profiles {:test {:dependencies [[ring-mock "0.1.5"]
                                   [cheshire "5.3.1"]
                                   [expectations "2.0.9"]]
                    :env {:db {:subprotocol "mysql"
                               :subname "//127.0.0.1:3306/wishwheel3_test"
                               :user "root"
                               :password ""}}}
             :dev {:env {:db {:subprotocol "mysql"
                               :subname "//127.0.0.1:3306/wishwheel3"
                               :user "root"
                               :password ""}}}
             :prod {:env {:db {:subprotocol "mysql"
                               :subname "//127.0.0.1:3306/wishwheel3_prod"
                               :user "root"
                               :password ""}}}}
  :ring {:handler wishwheel.core/app}
  :tern {:migration-dir "migrations"
         :color true
         :init wishwheel.config/configure-migrations})
