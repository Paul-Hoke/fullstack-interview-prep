-- =====================================================
-- SQL Interview Prep - Database Schema
-- =====================================================

-- Drop tables if they exist (for clean restart)
DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS employees;
DROP TABLE IF EXISTS departments;
DROP TABLE IF EXISTS customers;
DROP TABLE IF EXISTS suppliers;
DROP TABLE IF EXISTS inventory;
DROP TABLE IF EXISTS audit_log;

-- =====================================================
-- DEPARTMENTS Table
-- =====================================================
CREATE TABLE departments (
  department_id INT PRIMARY KEY,
  department_name VARCHAR(100) NOT NULL,
  location VARCHAR(100),
  budget DECIMAL(15, 2)
);

-- =====================================================
-- EMPLOYEES Table
-- =====================================================
CREATE TABLE employees (
  employee_id INT PRIMARY KEY,
  first_name VARCHAR(50) NOT NULL,
  last_name VARCHAR(50) NOT NULL,
  email VARCHAR(100) UNIQUE,
  phone VARCHAR(20),
  hire_date DATE NOT NULL,
  job_title VARCHAR(100),
  salary DECIMAL(10, 2),
  commission_pct DECIMAL(4, 2),
  manager_id INT,
  department_id INT,
  is_active BOOLEAN DEFAULT TRUE,
  FOREIGN KEY (manager_id) REFERENCES employees(employee_id),
  FOREIGN KEY (department_id) REFERENCES departments(department_id)
);

-- Create index on commonly queried columns
CREATE INDEX idx_employees_dept ON employees(department_id);
CREATE INDEX idx_employees_manager ON employees(manager_id);
CREATE INDEX idx_employees_salary ON employees(salary);

-- =====================================================
-- CUSTOMERS Table
-- =====================================================
CREATE TABLE customers (
  customer_id INT PRIMARY KEY,
  first_name VARCHAR(50) NOT NULL,
  last_name VARCHAR(50) NOT NULL,
  email VARCHAR(100) UNIQUE,
  phone VARCHAR(20),
  address VARCHAR(200),
  city VARCHAR(100),
  state VARCHAR(50),
  country VARCHAR(50) DEFAULT 'USA',
  postal_code VARCHAR(20),
  registration_date DATE,
  credit_limit DECIMAL(10, 2)
);

-- =====================================================
-- CATEGORIES Table
-- =====================================================
CREATE TABLE categories (
  category_id INT PRIMARY KEY,
  category_name VARCHAR(100) NOT NULL,
  description VARCHAR(500),
  parent_category_id INT,
  FOREIGN KEY (parent_category_id) REFERENCES categories(category_id)
);

-- =====================================================
-- SUPPLIERS Table
-- =====================================================
CREATE TABLE suppliers (
  supplier_id INT PRIMARY KEY,
  supplier_name VARCHAR(100) NOT NULL,
  contact_name VARCHAR(100),
  email VARCHAR(100),
  phone VARCHAR(20),
  address VARCHAR(200),
  city VARCHAR(100),
  country VARCHAR(50)
);

-- =====================================================
-- PRODUCTS Table
-- =====================================================
CREATE TABLE products (
  product_id INT PRIMARY KEY,
  product_name VARCHAR(100) NOT NULL,
  category_id INT,
  supplier_id INT,
  unit_price DECIMAL(10, 2) NOT NULL,
  units_in_stock INT DEFAULT 0,
  units_on_order INT DEFAULT 0,
  reorder_level INT DEFAULT 10,
  discontinued BOOLEAN DEFAULT FALSE,
  FOREIGN KEY (category_id) REFERENCES categories(category_id),
  FOREIGN KEY (supplier_id) REFERENCES suppliers(supplier_id)
);

CREATE INDEX idx_products_category ON products(category_id);

-- =====================================================
-- ORDERS Table
-- =====================================================
CREATE TABLE orders (
  order_id INT PRIMARY KEY,
  customer_id INT NOT NULL,
  employee_id INT,
  order_date DATE NOT NULL,
  required_date DATE,
  shipped_date DATE,
  ship_address VARCHAR(200),
  ship_city VARCHAR(100),
  ship_country VARCHAR(50),
  status VARCHAR(20) DEFAULT 'PENDING',
  FOREIGN KEY (customer_id) REFERENCES customers(customer_id),
  FOREIGN KEY (employee_id) REFERENCES employees(employee_id)
);

CREATE INDEX idx_orders_customer ON orders(customer_id);
CREATE INDEX idx_orders_date ON orders(order_date);

-- =====================================================
-- ORDER_ITEMS Table
-- =====================================================
CREATE TABLE order_items (
  order_id INT,
  product_id INT,
  quantity INT NOT NULL,
  unit_price DECIMAL(10, 2) NOT NULL,
  discount DECIMAL(4, 2) DEFAULT 0,
  PRIMARY KEY (order_id, product_id),
  FOREIGN KEY (order_id) REFERENCES orders(order_id),
  FOREIGN KEY (product_id) REFERENCES products(product_id)
);

-- =====================================================
-- INVENTORY Table (for tracking stock changes)
-- =====================================================
CREATE TABLE inventory (
  inventory_id INT PRIMARY KEY AUTO_INCREMENT,
  product_id INT NOT NULL,
  quantity_change INT NOT NULL,
  change_type VARCHAR(20) NOT NULL,
  change_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  notes VARCHAR(200),
  FOREIGN KEY (product_id) REFERENCES products(product_id)
);

-- =====================================================
-- AUDIT_LOG Table (for trigger demonstrations)
-- =====================================================
CREATE TABLE audit_log (
  log_id INT PRIMARY KEY AUTO_INCREMENT,
  table_name VARCHAR(50),
  operation VARCHAR(20),
  record_id INT,
  old_values VARCHAR(1000),
  new_values VARCHAR(1000),
  changed_by VARCHAR(100),
  changed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =====================================================
-- VIEW: Employee Details (for Q15)
-- =====================================================
CREATE VIEW v_employee_details AS
SELECT
  e.employee_id,
  e.first_name,
  e.last_name,
  e.first_name || ' ' || e.last_name AS full_name,
  e.email,
  e.job_title,
  e.salary,
  e.hire_date,
  d.department_name,
  m.first_name || ' ' || m.last_name AS manager_name
FROM employees e
LEFT JOIN departments d ON e.department_id = d.department_id
LEFT JOIN employees m ON e.manager_id = m.employee_id;

-- =====================================================
-- VIEW: Order Summary
-- =====================================================
CREATE VIEW v_order_summary AS
SELECT
  o.order_id,
  o.order_date,
  c.first_name || ' ' || c.last_name AS customer_name,
  e.first_name || ' ' || e.last_name AS employee_name,
  SUM(oi.quantity * oi.unit_price * (1 - oi.discount)) AS total_amount,
  COUNT(oi.product_id) AS item_count
FROM orders o
JOIN customers c ON o.customer_id = c.customer_id
LEFT JOIN employees e ON o.employee_id = e.employee_id
JOIN order_items oi ON o.order_id = oi.order_id
GROUP BY o.order_id, o.order_date, c.first_name, c.last_name, e.first_name, e.last_name;

-- =====================================================
-- VIEW: Product Sales
-- =====================================================
CREATE VIEW v_product_sales AS
SELECT
  p.product_id,
  p.product_name,
  c.category_name,
  SUM(oi.quantity) AS total_quantity_sold,
  SUM(oi.quantity * oi.unit_price * (1 - oi.discount)) AS total_revenue
FROM products p
JOIN categories c ON p.category_id = c.category_id
LEFT JOIN order_items oi ON p.product_id = oi.product_id
GROUP BY p.product_id, p.product_name, c.category_name;
