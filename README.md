# Clojure Sales Database System

A functional programming approach to sales database management, built with Clojure. This application demonstrates core functional programming concepts including immutable data structures, higher-order functions, and pure function design.

## Features

- **Customer Management**: Store and retrieve customer information (ID, name, address, phone)
- **Product Inventory**: Track product details and pricing
- **Sales Tracking**: Record and analyze sales transactions
- **Analytics**:
  - Calculate total sales revenue per customer
  - Track product sales volume
  - Generate sales reports
- **Interactive Menu**: User-friendly command-line interface

## Project Structure
```
clojure-sales-database/
├── src/
│   ├── app.clj          # Main entry point
│   ├── db.clj           # Database operations and data management
│   └── menu.clj         # Interactive menu system (a3.clj)
├── cust.txt             # Customer data file
├── prod.txt             # Product data file
├── sales.txt            # Sales transaction data
└── README.md
```

## Data Format

### Customer Data (cust.txt)
```
customerID|name|address|phoneNumber
```

### Product Data (prod.txt)
```
productID|description|unitCost
```

### Sales Data (sales.txt)
```
salesID|customerID|productID|itemCount
```

## Installation

1. Ensure you have [Clojure](https://clojure.org/guides/install_clojure) installed
2. Clone this repository:
```bash
   git clone https://github.com/yourusername/clojure-sales-database.git
   cd clojure-sales-database
```

## Usage

Run the application:
```bash
clojure -M app.clj
```

### Menu Options

1. **Display Customer Table** - View all customers
2. **Display Product Table** - View all products
3. **Display Sales Table** - View all sales with customer and product details
4. **Total Sales for Customer** - Calculate revenue by customer name
5. **Total Count for Product** - Calculate units sold by product type
6. **Exit** - Close the application

## Technical Highlights

### Functional Programming Concepts
- **Pure Functions**: All data transformations are side-effect free
- **Immutable Data Structures**: Uses Clojure's persistent hash maps
- **Higher-Order Functions**: Extensive use of `map`, `filter`, `reduce`
- **Function Composition**: Modular design with composable functions

### Key Functions

**Database Operations (db.clj)**
- `customers_map`, `products_map`, `sales_map` - Load data from files into hash maps
- `search_customer`, `search_product` - Query operations
- `calculate_total_customer` - Aggregate customer sales
- `calculate_nbr_of_products` - Sum product quantities sold

**Menu System (menu.clj)**
- Interactive display and input handling
- Formatted output for tables and reports
- Screen management utilities

## Example Output
```
*** Sales Menu ***
------------------

1. Display Customer Table
2. Display Product Table
3. Display Sales Table
4. Total Sales for Customer
5. Total Count for Product
6. Exit

Enter an option? 4

Please enter a customer name => John Doe

John Doe: $1,250.00
```

## Technologies

- **Language**: Clojure
- **Paradigm**: Functional Programming
- **Data Structures**: Hash Maps, Vectors, Sequences

## Future Enhancements

- [ ] Add create/update/delete operations for records
- [ ] Implement data persistence (save changes to files)
- [ ] Add date tracking for sales
- [ ] Generate sales reports by date range
- [ ] Export data to CSV/JSON formats
- [ ] Add input validation and error handling

## Author

Naj - Computer Science Student, Concordia University

## Course Context

Developed for COMP 348 (Programming Languages) - Functional Programming Module

## License

MIT License - Feel free to use this project for educational purposes.
