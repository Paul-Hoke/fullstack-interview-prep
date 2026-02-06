-- =====================================================
-- Q16: What are indexes and how do they improve performance?
-- =====================================================

-- An INDEX is a data structure that improves query speed.
-- Trade-off: Faster reads, slower writes, more storage.

-- Indexes already created in schema.sql:
-- idx_employees_dept, idx_employees_manager, idx_employees_salary
-- idx_products_category, idx_orders_customer, idx_orders_date

-- Show existing indexes (H2 specific)
SELECT
  TABLE_NAME,
  INDEX_NAME,
  COLUMN_NAME
FROM INFORMATION_SCHEMA.INDEX_COLUMNS
WHERE TABLE_SCHEMA = 'PUBLIC'
ORDER BY TABLE_NAME, INDEX_NAME;

-- Create a simple index
CREATE INDEX idx_customers_email ON customers(email);

-- Create a composite index (multiple columns)
CREATE INDEX idx_orders_status_date ON orders(status, order_date);

-- Create a unique index (enforces uniqueness)
-- Already have unique constraint on email, but can create explicitly:
-- CREATE UNIQUE INDEX idx_unique_employee_email ON employees(email);

-- Query that benefits from index on department_id
EXPLAIN
SELECT first_name, last_name FROM employees
WHERE department_id = 1;

-- Query that benefits from composite index
EXPLAIN
SELECT * FROM orders
WHERE status = 'PENDING' AND order_date >= '2024-01-01';

-- Covering index query (all columns in index)
-- If we had: CREATE INDEX idx_emp_cover ON employees(department_id, first_name, last_name)
-- This query would be covered (no table lookup needed)

-- When indexes help:
-- 1. WHERE clause filtering
SELECT * FROM employees WHERE salary > 70000;

-- 2. JOIN conditions
SELECT e.first_name, d.department_name
FROM employees e
JOIN departments d ON e.department_id = d.department_id;

-- 3. ORDER BY
SELECT * FROM orders ORDER BY order_date DESC;

-- 4. GROUP BY
SELECT department_id, COUNT(*)
FROM employees
GROUP BY department_id;

-- When indexes may NOT help:
-- 1. Small tables (full scan is faster)
-- 2. High-selectivity columns (many rows match)
-- 3. Frequently updated columns
-- 4. Columns with many NULL values

-- Index on expression (H2 supports)
CREATE INDEX idx_emp_full_name ON employees((first_name || ' ' || last_name));

-- Drop an index
DROP INDEX IF EXISTS idx_customers_email;

-- Best practices:
-- 1. Index columns used in WHERE, JOIN, ORDER BY
-- 2. Put most selective columns first in composite indexes
-- 3. Don't over-index (each index slows writes)
-- 4. Consider covering indexes for frequently run queries
-- 5. Monitor index usage and remove unused indexes

-- Recreate for demo
CREATE INDEX idx_customers_email ON customers(email);
