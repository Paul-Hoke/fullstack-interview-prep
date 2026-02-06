-- =====================================================
-- Q02: How do you filter data using WHERE clause?
-- =====================================================

-- Simple comparison operators
SELECT * FROM employees WHERE salary > 70000;

SELECT * FROM employees WHERE department_id = 1;

SELECT * FROM employees WHERE is_active = TRUE;

-- Multiple conditions with AND
SELECT * FROM employees
WHERE department_id = 1
  AND salary > 60000;

-- Multiple conditions with OR
SELECT * FROM employees
WHERE department_id = 1
   OR department_id = 2;

-- Combining AND and OR (use parentheses!)
SELECT * FROM employees
WHERE (department_id = 1 OR department_id = 2)
  AND salary > 65000;

-- NOT operator
SELECT * FROM employees WHERE NOT is_active;

SELECT * FROM employees WHERE department_id != 1;

-- BETWEEN for ranges (inclusive)
SELECT * FROM employees
WHERE salary BETWEEN 50000 AND 70000;

SELECT * FROM orders
WHERE order_date BETWEEN '2024-01-01' AND '2024-03-31';

-- IN for multiple values
SELECT * FROM employees
WHERE department_id IN (1, 2, 3);

SELECT * FROM customers
WHERE country IN ('USA', 'Canada', 'UK');

-- LIKE for pattern matching
SELECT * FROM employees WHERE first_name LIKE 'J%';     -- Starts with J
SELECT * FROM employees WHERE last_name LIKE '%son';    -- Ends with son
SELECT * FROM employees WHERE email LIKE '%@%.com';     -- Contains pattern
SELECT * FROM products WHERE product_name LIKE '%Pro%'; -- Contains 'Pro'

-- IS NULL / IS NOT NULL
SELECT * FROM employees WHERE manager_id IS NULL;       -- Top-level managers
SELECT * FROM employees WHERE commission_pct IS NOT NULL;
