-- =====================================================
-- Q08: What are UNION, INTERSECT, and EXCEPT?
-- =====================================================

-- UNION - combines results, removes duplicates
-- Get all cities from customers and employees' departments
SELECT city FROM customers WHERE city IS NOT NULL
UNION
SELECT location AS city FROM departments WHERE location IS NOT NULL;

-- UNION ALL - combines results, keeps duplicates (faster)
SELECT first_name, last_name, 'Customer' AS type FROM customers
UNION ALL
SELECT first_name, last_name, 'Employee' AS type FROM employees;

-- UNION with ORDER BY (applied to final result)
SELECT first_name, last_name, 'Customer' AS type, city
FROM customers
UNION
SELECT first_name, last_name, 'Employee' AS type,
       (SELECT location FROM departments d WHERE d.department_id = e.department_id) AS city
FROM employees e
ORDER BY type, last_name;

-- INTERSECT - only rows that appear in both queries
-- Find cities that have both customers and department offices
SELECT city FROM customers WHERE city IS NOT NULL
INTERSECT
SELECT location FROM departments WHERE location IS NOT NULL;

-- EXCEPT (MINUS in Oracle) - rows in first query but not in second
-- Find cities with customers but no department offices
SELECT city FROM customers WHERE city IS NOT NULL
EXCEPT
SELECT location FROM departments WHERE location IS NOT NULL;

-- Practical example: Find customers who haven't ordered recently
-- All customers
SELECT customer_id, first_name, last_name FROM customers
EXCEPT
-- Customers with orders in 2024
SELECT DISTINCT c.customer_id, c.first_name, c.last_name
FROM customers c
JOIN orders o ON c.customer_id = o.customer_id
WHERE o.order_date >= '2024-01-01';

-- Combining set operations
-- Find employees who are either managers OR in Engineering department
(SELECT employee_id, first_name, last_name
 FROM employees
 WHERE employee_id IN (SELECT DISTINCT manager_id FROM employees WHERE manager_id IS NOT NULL))
UNION
(SELECT employee_id, first_name, last_name
 FROM employees
 WHERE department_id = 1);

-- Using UNION to create a summary report
SELECT 'Active Employees' AS category, COUNT(*) AS count FROM employees WHERE is_active = TRUE
UNION ALL
SELECT 'Inactive Employees', COUNT(*) FROM employees WHERE is_active = FALSE
UNION ALL
SELECT 'Total Customers', COUNT(*) FROM customers
UNION ALL
SELECT 'Total Orders', COUNT(*) FROM orders
UNION ALL
SELECT 'Pending Orders', COUNT(*) FROM orders WHERE status = 'PENDING';
