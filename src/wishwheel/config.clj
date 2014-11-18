(ns wishwheel.config
  " Configuration
    Defines our database configuration so that other functions can access it.

    DATE      BY             CHANGE REF  DESCRIPTION
    ========  ==========     =========== =============
    11/7/14   Taylor Lapeyre 3f7d47      couple fixes")

(def db {:subprotocol "mysql"
         :subname "//127.0.0.1:3306/wishwheel3"
         :user "root"
         :password ""})
