-- =====================================================
-- Q04: What are the different types of JOINs?
-- =====================================================

-- INNER JOIN - only matching rows from both tables
SELECT
  e.first_name,
  e.last_name,
  d.department_name
FROM employees e
INNER JOIN departments d ON e.department_id = d.department_id;

-- LEFT JOIN (LEFT OUTER JOIN) - all rows from left table
-- Returns NULL for non-matching right table columns
SELECT
  d.department_name,
  e.first_name,
  e.last_name
FROM departments d
LEFT JOIN employees e ON d.department_id = e.department_id
ORDER BY d.department_name;

-- RIGHT JOIN (RIGHT OUTER JOIN) - all rows from right table
SELECT
  e.first_name,
  e.last_name,
  d.department_name
FROM employees e
RIGHT JOIN departments d ON e.department_id = d.department_id
ORDER BY d.department_name;

-- FULL OUTER JOIN - all rows from both tables
SELECT
  e.first_name,
  e.last_name,
  d.department_name
FROM employees e
FULL OUTER JOIN departments d ON e.department_id = d.department_id;

-- CROSS JOIN - cartesian product (every combination)
SELECT
  c.category_name,
  s.supplier_name
FROM categories c
CROSS JOIN suppliers s
WHERE c.parent_category_id IS NULL;  -- Only top-level categories

-- Self JOIN - joining a table to itself
-- Find employees and their managers
SELECT
  e.first_name || ' ' || e.last_name AS employee_name,
  m.first_name || ' ' || m.last_name AS manager_name
FROM employees e
LEFT JOIN employees m ON e.manager_id = m.employee_id
ORDER BY manager_name NULLS FIRST, employee_name;

-- Multiple JOINs
SELECT
  o.order_id,
  c.first_name || ' ' || c.last_name AS customer_name,
  e.first_name || ' ' || e.last_name AS employee_name,
  p.product_name,
  oi.quantity,
  oi.unit_price
FROM orders o
JOIN customers c ON o.customer_id = c.customer_id
LEFT JOIN employees e ON o.employee_id = e.employee_id
JOIN order_items oi ON o.order_id = oi.order_id
JOIN products p ON oi.product_id = p.product_id
ORDER BY o.order_id;

-- JOIN with aggregate
SELECT
  d.department_name,
  COUNT(e.employee_id) AS employee_count,
  AVG(e.salary) AS avg_salary
FROM departments d
LEFT JOIN employees e ON d.department_id = e.department_id
GROUP BY d.department_id, d.department_name
ORDER BY employee_count DESC;
