-- =====================================================
-- Q23: How do INSERT, UPDATE, and DELETE work?
-- =====================================================

-- =====================================================
-- INSERT - Add new rows
-- =====================================================

-- Insert single row with all columns
INSERT INTO departments (department_id, department_name, location, budget)
VALUES (9, 'Research', 'Boston', 750000.00);

-- Insert with only required columns (others get defaults/NULL)
INSERT INTO customers (customer_id, first_name, last_name, email)
VALUES (19, 'Test', 'Customer', 'test.customer@example.com');

-- Insert multiple rows
INSERT INTO categories (category_id, category_name, description) VALUES
(14, 'Test Category 1', 'Test description 1'),
(15, 'Test Category 2', 'Test description 2');

-- Insert from SELECT (copy data)
INSERT INTO customers (customer_id, first_name, last_name, email, city, country)
SELECT
  20,
  first_name,
  last_name,
  'copy.' || email,
  city,
  country
FROM customers
WHERE customer_id = 1;

-- Verify inserts
SELECT * FROM departments WHERE department_id = 9;
SELECT * FROM customers WHERE customer_id IN (19, 20);
SELECT * FROM categories WHERE category_id IN (14, 15);

-- =====================================================
-- UPDATE - Modify existing rows
-- =====================================================

-- Update single column
UPDATE departments SET budget = 800000.00 WHERE department_id = 9;

-- Update multiple columns
UPDATE customers
SET phone = '555-TEST', city = 'Test City'
WHERE customer_id = 19;

-- Update with expression
UPDATE employees
SET salary = salary * 1.05
WHERE department_id = 1 AND is_active = TRUE;

-- Update with subquery
UPDATE products
SET unit_price = unit_price * 1.1
WHERE category_id = (SELECT category_id FROM categories WHERE category_name = 'Electronics');

-- Verify updates
SELECT * FROM departments WHERE department_id = 9;
SELECT * FROM customers WHERE customer_id = 19;

-- Restore employee salaries
UPDATE employees
SET salary = salary / 1.05
WHERE department_id = 1 AND is_active = TRUE;

-- Restore product prices
UPDATE products
SET unit_price = unit_price / 1.1
WHERE category_id = (SELECT category_id FROM categories WHERE category_name = 'Electronics');

-- =====================================================
-- DELETE - Remove rows
-- =====================================================

-- Delete specific rows
DELETE FROM categories WHERE category_id IN (14, 15);

-- Delete with condition
DELETE FROM customers WHERE customer_id = 20;

-- Delete all test data
DELETE FROM customers WHERE customer_id = 19;
DELETE FROM departments WHERE department_id = 9;

-- TRUNCATE - Delete all rows (faster than DELETE, resets auto-increment)
-- TRUNCATE TABLE table_name;  -- Cannot use with foreign key constraints

-- Verify deletions
SELECT COUNT(*) FROM departments WHERE department_id = 9;  -- Should be 0
SELECT COUNT(*) FROM customers WHERE customer_id IN (19, 20);  -- Should be 0

-- =====================================================
-- Best Practices
-- =====================================================

-- 1. Always use WHERE clause with UPDATE and DELETE (unless intended)
-- 2. Use transactions for multiple related changes
-- 3. Test UPDATE/DELETE with SELECT first:
SELECT * FROM employees WHERE department_id = 1;  -- Preview before update
-- UPDATE employees SET ... WHERE department_id = 1;

-- 4. Backup data before bulk updates
-- 5. Use LIMIT with DELETE for large tables (batch deletes)
