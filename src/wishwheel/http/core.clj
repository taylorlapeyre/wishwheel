(ns wishwheel.http.core
  (:require [ring.util.response :refer [response status]]))

(defmacro respond
  "Nested if-let. If all bindings are non-nil, execute body in the context of
  those bindings.  If a binding is nil, evaluate its `else-expr` form and stop
  there.  `else-expr` is otherwise not evaluated.

  bindings* => binding-form else-expr"
  [bindings & body]
  (cond
    (= (count bindings) 0) `(do ~@body)
    (symbol? (bindings 0)) `(if-let ~(subvec bindings 0 2)
                              (respond ~(subvec bindings 3) ~@body)
                              ~(status (response (case (bindings 2)
                                                   404 "Not found"
                                                   403 "Unauthorized"
                                                   422 "Invalid data"))
                                       (bindings 2)))
    :else (throw (IllegalArgumentException. "symbols only in bindings"))))