#_{:clj-kondo/ignore [:namespace-name-mismatch]}
(ns name_of_namespace
  (:require [clojure.string :as stringy])
  (:require [db]))

;; Display the menu and ask the user for the option
(defn showMenu
  []
  (println "\n\n*** Sales Menu ***")
  (println "------------------\n")
  (println "1. Display Customer Table")
  (println "2. Display Product Table")
  (println "3. Display Sales Table")
  (println "4. Total Sales for Customer")
  (println "5. Total Count for Product")
  (println "6. Exit")
  (do
    (print "\nEnter an option? ")
    (flush)
    (read-line)))

;
(defn clear_screen
  []
  (print (str (char 27) "[2J"))
  (flush))
;
(defn wait_for_user
  []
  (print "\nPress any key to continue ")
  (flush)
  (read-line))

(defn option1
  [customerDB productDB salesDB]
  ;(println "Read and display the contents of the cust.txt file")
  (println "\n*** Customer Table **")
  (let [sorted_customers (sort-by :id (vals customerDB))]
    (doseq [c sorted_customers]
      (println (str (:id c) ": [\"" (:name c) "\" \"" (:address c) "\" \"" (:phone c) "\"]"))))
  (wait_for_user)
  (clear_screen))


(defn option2
  [customerDB productDB salesDB]
  ;(println "Read and display the contents of the prod.txt file")
  (println "** Product Table **")
  (let [sorted_products (sort-by :id (vals productDB))]
    (doseq [p sorted_products]
      (println (str (:id p) ": [\"" (:description p) "\" \"" (:cost p) "\"]"))))
  (wait_for_user)
  (clear_screen))


(defn option3
  [customerDB productDB salesDB]
  ;(println "Read and display a 'friendly' version of the prod.txt file")
  (println "** Sales Table **")
  (let [sorted-sales (sort-by :id (vals salesDB))]
    (doseq [s sorted-sales]
      (let [customer (get customerDB (:customer_ID s))
            product (get productDB (:product_ID s))]
        (println (str (:id s) ": [\"" (:name customer) "\" \"" (:description product) "\" \"" (:count s) "\"]")))))
  (wait_for_user)
  (clear_screen))

(defn option4
  [customerDB productDB salesDB]
  (print "\nPlease enter a customer name => ")
  (flush)
  (let [name (read-line)
        total (db/calculate_total_customer customerDB productDB salesDB name)]
    (if (not (nil? total))
      (println (format "\n%s: $%.2f" name total))
      (println (format "\nCustomer '%s' not found or has no sales." name))))
  (wait_for_user)
  (clear_screen))

(defn option5
  [customerDB productDB salesDB]
  (print "\nPlease enter a product type => ")
  (flush)
  (let [item (read-line)
        count (db/calculate_nbr_of_products productDB salesDB item)]
    (if (not (nil? count))
      (println (format "\n%s: %d" item count))
      (println (format "\nProduct '%s' not found or has no sales." item))))
  (wait_for_user)
  (clear_screen))

;(defn option4
 ; [customerDB productDB salesDB]
 ; (print "\nPlease enter a customer name => ")
;; (flush)
 ; (let [name (read-line)
;    total (db/calculate_total_customer customerDB productDB salesDB name)]
;  (if (not (nil? total))
;    (println (format "\n%s: $%.2f" name total))
    ;(println (format "\nCustomer%s not found or has no sales." name))
   ; )
  ;  )
 ; (wait_for_user)
;  (clear_screen)
  ;)

;(defn option5
 ; [customerDB productDB salesDB]
  ;(print "\nPlease enter a product type => ")
 ; (flush)
  ;(let [item (read-line)
   ;     count (db/calculate_nbr_of_products productDB salesDB item)
    ;    ]
;    (if (not (nil? count))
 ;     (println (format "\n%s:" item))
  ;    (println (format "\nProduct%s not found or has no sales." item))
   ;   )
;  )
 ; (wait_for_user)
  ;(clear_screen)
;)



; If the menu selection is valid, call the relevant function to
; process the selection
(defn processOption
  [option customerDB productDB salesDB]
  (if (= option "1")
    (option1 customerDB productDB salesDB)
    (if (= option "2")
      (option2 customerDB productDB salesDB)
      (if (= option "3")
        (option3 customerDB productDB salesDB)
        (if (= option "4")
          (option4 customerDB productDB salesDB)
          (if (= option "5")
            (option5 customerDB productDB salesDB)
            (println "Invalid Option, please try again")))))))


; Display the menu and get a menu item selection. Process the
; selection and then loop again to get the next menu selection
(defn menu
  [customerDB productDB salesDB] ; parm(s) can be provided here, if needed
  (let [option (stringy/trim (showMenu))]
    (if (= option "6")
      (println "\nGood Bye\n")
      (do
        (processOption option customerDB productDB salesDB)
        (recur customerDB productDB salesDB)))))



; ------------------------------
; Run the program. Note that your assignment will run from app.clj
; and then call the code in this file
;(menu)
(let [customerDB (db/customers_map "cust.txt")
      productDB (db/products_map "prod.txt")
      salesDB (db/sales_map "sales.txt")]
  (menu customerDB productDB salesDB))
