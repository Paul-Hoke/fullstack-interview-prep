-- =====================================================
-- Q22: What is database normalization?
-- =====================================================

-- Normalization organizes data to reduce redundancy and improve integrity.
-- Our schema follows 3rd Normal Form (3NF).

-- =====================================================
-- 1NF (First Normal Form)
-- - Each column contains atomic (indivisible) values
-- - Each column contains values of a single type
-- - Each row is unique (primary key)
-- =====================================================

-- BAD (not 1NF): phone_numbers = "555-1234, 555-5678"
-- GOOD (1NF): separate rows or separate table for phone numbers

-- Our employees table is in 1NF:
SELECT employee_id, first_name, last_name, phone
FROM employees
LIMIT 5;

-- =====================================================
-- 2NF (Second Normal Form)
-- - Must be in 1NF
-- - No partial dependencies (all non-key columns depend on entire primary key)
-- =====================================================

-- Composite key example: order_items (order_id, product_id)
-- unit_price depends on product_id only (partial dependency)
-- In pure 2NF, we'd only store it in products table

-- Our design stores unit_price in order_items for historical pricing:
SELECT oi.order_id, oi.product_id, oi.unit_price AS order_price,
       p.unit_price AS current_price
FROM order_items oi
JOIN products p ON oi.product_id = p.product_id
LIMIT 5;

-- =====================================================
-- 3NF (Third Normal Form)
-- - Must be in 2NF
-- - No transitive dependencies (non-key columns depend only on primary key)
-- =====================================================

-- BAD: employees table with department_location
-- (department_location depends on department_id, not employee_id)

-- GOOD: separate departments table (our schema)
SELECT
  e.employee_id,
  e.first_name,
  d.department_name,
  d.location  -- comes from departments, not stored in employees
FROM employees e
JOIN departments d ON e.department_id = d.department_id;

-- =====================================================
-- Denormalization (intentional)
-- Sometimes we denormalize for performance
-- =====================================================

-- Example: storing customer_name in orders for quick access
-- We don't do this - we join to customers table

-- Query showing normalized structure (requires join)
SELECT
  o.order_id,
  c.first_name || ' ' || c.last_name AS customer_name,
  o.order_date
FROM orders o
JOIN customers c ON o.customer_id = c.customer_id
LIMIT 5;

-- Benefits of normalization:
-- 1. Reduces data redundancy
-- 2. Improves data integrity
-- 3. Smaller database size
-- 4. Easier updates (change in one place)

-- Drawbacks:
-- 1. More complex queries (joins)
-- 2. Potential performance impact on reads

-- =====================================================
-- Example: What an unnormalized table might look like
-- =====================================================

-- BAD design (all in one table - for illustration only)
CREATE TABLE orders_denormalized_example (
  order_id INT PRIMARY KEY,
  customer_name VARCHAR(100),      -- Redundant
  customer_email VARCHAR(100),     -- Redundant
  customer_phone VARCHAR(20),      -- Redundant
  product1_name VARCHAR(100),      -- Not atomic
  product1_qty INT,
  product1_price DECIMAL(10,2),
  product2_name VARCHAR(100),      -- Fixed number of products
  product2_qty INT,
  product2_price DECIMAL(10,2),
  employee_name VARCHAR(100),      -- Redundant
  department_name VARCHAR(100)     -- Transitive dependency
);

-- Compare with our normalized structure:
-- orders -> customers (1:N)
-- orders -> order_items -> products (N:M through junction table)
-- orders -> employees -> departments (chain of relationships)

-- Clean up example table
DROP TABLE IF EXISTS orders_denormalized_example;
