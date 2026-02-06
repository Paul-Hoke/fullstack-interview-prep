-- =====================================================
-- Q24: What is the difference between EXISTS and IN?
-- =====================================================

-- Both are used to filter based on subquery results,
-- but they work differently and have different performance characteristics.

-- =====================================================
-- IN operator
-- =====================================================

-- IN checks if a value matches ANY value in a list/subquery
-- Returns TRUE if match found, FALSE otherwise

-- Simple IN with list
SELECT * FROM employees
WHERE department_id IN (1, 2, 3);

-- IN with subquery
SELECT first_name, last_name
FROM employees
WHERE department_id IN (
  SELECT department_id
  FROM departments
  WHERE location = 'New York'
);

-- Find products in categories that have subcategories
SELECT product_name
FROM products
WHERE category_id IN (
  SELECT DISTINCT parent_category_id
  FROM categories
  WHERE parent_category_id IS NOT NULL
);

-- =====================================================
-- EXISTS operator
-- =====================================================

-- EXISTS checks if subquery returns ANY rows
-- Returns TRUE if at least one row exists, FALSE otherwise

-- Find departments that have employees
SELECT department_name
FROM departments d
WHERE EXISTS (
  SELECT 1
  FROM employees e
  WHERE e.department_id = d.department_id
);

-- Find customers who have placed orders
SELECT first_name, last_name
FROM customers c
WHERE EXISTS (
  SELECT 1
  FROM orders o
  WHERE o.customer_id = c.customer_id
);

-- =====================================================
-- NOT IN vs NOT EXISTS
-- =====================================================

-- NOT IN - find values not in the list
SELECT first_name, last_name
FROM employees
WHERE department_id NOT IN (
  SELECT department_id
  FROM departments
  WHERE location = 'New York'
);

-- NOT EXISTS - find rows where correlated subquery returns no rows
SELECT department_name
FROM departments d
WHERE NOT EXISTS (
  SELECT 1
  FROM employees e
  WHERE e.department_id = d.department_id
);

-- =====================================================
-- Key Differences
-- =====================================================

-- 1. NULL handling
-- IN returns UNKNOWN (not TRUE) when comparing with NULL
-- This query may return unexpected results if subquery has NULLs:
SELECT * FROM employees
WHERE manager_id NOT IN (SELECT manager_id FROM employees);  -- Returns nothing due to NULL!

-- EXISTS handles NULL correctly:
SELECT e1.*
FROM employees e1
WHERE NOT EXISTS (
  SELECT 1 FROM employees e2
  WHERE e1.employee_id = e2.manager_id
);  -- Correctly finds employees who aren't managers

-- 2. Performance characteristics
-- IN: Subquery executed once, result set stored, then outer query checked against it
-- EXISTS: Subquery executed for each row of outer query (correlated), stops at first match

-- For large outer table, small subquery result: IN may be faster
-- For small outer table, large inner table: EXISTS may be faster (stops early)

-- =====================================================
-- Equivalent queries
-- =====================================================

-- These return the same results:

-- Using IN
SELECT product_name FROM products
WHERE category_id IN (SELECT category_id FROM categories WHERE category_name = 'Electronics');

-- Using EXISTS
SELECT p.product_name FROM products p
WHERE EXISTS (
  SELECT 1 FROM categories c
  WHERE c.category_id = p.category_id
    AND c.category_name = 'Electronics'
);

-- Using JOIN
SELECT p.product_name
FROM products p
JOIN categories c ON p.category_id = c.category_id
WHERE c.category_name = 'Electronics';

-- Rule of thumb:
-- Use IN for simple subqueries returning small result sets
-- Use EXISTS for correlated subqueries or when subquery has NULLs
-- Use JOIN when you need columns from both tables
