(ns db
  (:require [clojure.string :as str]))

; contains all the data mangement code

; list of functions I need to implement
;1) method to parse the lines into a vector
(defn parse_line
  [line]
  (str/split line #"\|"))
; wanted to nest functions, but that's not possible in clojure
(defn parse_customer_line
  "Format: customer ID | name | address | phoneNbr"
  [line]
  (let [[id name address phoneNbr] (parse_line line)]
    {:id (read-string id)
     :name name
     :address address
     :phone phoneNbr}
)
)

(defn parse_product_line
  "Format: product ID | description | unit cost"
  [line]
  (let [[id description cost] (parse_line line)]
    {:id (read-string id)
     :description description
     :cost (read-string cost)}
)
)

(defn parse_sales_line
  "Format: sales ID | customer ID | product ID | item count"
  [line]
  (let [[id customer_ID product_ID count] (parse_line line)]
    {:id (read-string id)
     :customer_ID (read-string customer_ID)
     :product_ID (read-string product_ID)
     :count (read-string count)}))

;2) create a map data structure with customer id, name, address, phoneNbr
; read the file, parse the lines, and build the map like this: {1 {:id 1: name "John Soe" :address "123 street st" :phone "123-8908"} (more ids here)}
(defn customers_map
  "Load customer data from cust.txt into a map data structure (key: customer ID)" ; documentation
  [filename] ; this is the parameter that the function takes
  (let [lines (str/split-lines (slurp filename))]
    ; reduce --> from clojure.core, it iterates through a sequence and accumulates a result
    (reduce
     (fn [current line]
       (let [customer (parse_customer_line line)]
         (assoc current (:id customer) customer)))
     {}
     lines)))

;3) create a map with product id (same as 2)
(defn products_map
  "Load product data from prod.txt into a map data structure (key: product ID)" ; documentation
  [filename] ; this is the parameter that the function takes
  (let [lines (str/split-lines (slurp filename))]
    ; reduce --> from clojure.core, it iterates through a sequence and accumulates a result
    (reduce
     (fn [current line]
       (let [product (parse_product_line line)]
         (assoc current (:id product) product)))
     {}
     lines)))

; 4) create a map with sales id
(defn sales_map
  "Load sales data from sales.txt into a map data structure (key: sales ID)" ; documentation
  [filename] ; this is the parameter that the function takes
  (let [lines (str/split-lines (slurp filename))]
    (reduce
     (fn [current line]
       (let [sale (parse_sales_line line)]
         (assoc current (:id sale) sale)))
     {}
     lines)))

; 5) find customer by name (insensitive case, returns the map entry or nil)
(defn search_customer
  "Find customer by name function"
  [customerDatabase, customer_name]
  (let [all_customers (vals customerDatabase) ; vals --> Returns a sequence of the map's values, in the same order as (seq map). (so without the keys)
        found_customer (filter (fn [customerf]
                                 (= (:name customerf) customer_name)) all_customers) ; to search, filter can be used, it only lets through items that pass a condition
        ]
(first found_customer)))

; 6) find product by name (insensitive case, returns the map entry or nil)
(defn search_product
  "Find product by name function"
  [productDatabase, product_name]
  (let [all_products (vals productDatabase) ; vals --> Returns a sequence of the map's values, in the same order as (seq map). (so without the keys)
        found_product (filter (fn [productf]
                                (= (:description productf) product_name))
all_products) ; to search, filter can be used, it only lets through items that pass a condition
        ]
(first found_product)))

; 8) get all sales for customer (use customer id, returns sequence of sale maps)
(defn customer_sales
  "Get all sales for customer by customer ID function"
  [salesDatabase customer_ID]
  (let [all_sales (vals salesDatabase)
        found_sales (filter (fn [salef]
                              (= (:customer_ID salef) customer_ID)
                              )
                            all_sales)
        ]
    found_sales)
  )

; 9) get all sales for product (use product id, returns sequence of sale maps)
(defn product_sales
  "Get all sales for a product by product ID function"
  [salesDatabase product_ID]
  (let [all_sales (vals salesDatabase)
        found_sales (filter (fn [salef]
                              (= (:product_ID salef) product_ID))
                            all_sales)]
    found_sales))


; 10) calculate total amount for a single sale
(defn calculate_total_sale
  "Calculate the total $ amount for a single sale function"
  [productDatabase sale]
  (let [product (get productDatabase (:product_ID sale))
        cost (:cost product)
        count (:count sale)
        ]
    (* cost count)
    )
  )

; 11) calculate customer total (use customer name, return a nbr of the total or nil)
;    find the customer, then get all the sales for the customer, then get product cost from prodDB, then do count*cost, then sum everything
(defn calculate_total_customer
  "Calculate the customer total $ amount for a single customer by name function"
  [customerDatabase productDB salesDB customer_name]
  (let [customer (search_customer customerDatabase customer_name)]
  (when customer
    (let [customer_ID (:id customer)
             sales (customer_sales salesDB customer_ID)]  ;
         (reduce (fn [total sale]
                   (+ total (calculate_total_sale productDB sale)))
                 0
                 sales)))
    )
  )

; 12) calculate product count (use product name)
(defn calculate_nbr_of_products
  "Calculate how many units were sold for a product by name function"
  [productDatabase salesDatabase product_name]
  (let [product (search_product productDatabase product_name)]
    (when product
      (let [product_ID (:id product)
            sales (product_sales salesDatabase product_ID)
            ]
        (reduce (fn [total sale]
                  (+ total (:count sale))
                  )
                0
                sales)
        )
      )
    )
  )

