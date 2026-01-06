(ns app
  (:require [db])
  (:require [menu]))
;this is the file where the main is
(defn -main 
  "Load files and start the app"
  []
  (let [customerDB (db/customers_map "cust.txt") ;from the clojure repl, clojure.repl/
        productDB (db/products_map "prod.txt")
        salesDB (db/sales_map "sales.txt")]
    (menu/menu customerDB productDB salesDB)))

; run the app
(-main)
