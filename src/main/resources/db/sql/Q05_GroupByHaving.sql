-- =====================================================
-- Q05: What is GROUP BY and HAVING clause?
-- =====================================================

-- Basic GROUP BY with COUNT
SELECT
  department_id,
  COUNT(*) AS employee_count
FROM employees
GROUP BY department_id;

-- GROUP BY with multiple aggregates
SELECT
  department_id,
  COUNT(*) AS employee_count,
  SUM(salary) AS total_salary,
  AVG(salary) AS avg_salary,
  MIN(salary) AS min_salary,
  MAX(salary) AS max_salary
FROM employees
GROUP BY department_id;

-- GROUP BY with JOIN for readable output
SELECT
  d.department_name,
  COUNT(e.employee_id) AS employee_count,
  ROUND(AVG(e.salary), 2) AS avg_salary
FROM departments d
LEFT JOIN employees e ON d.department_id = e.department_id
GROUP BY d.department_id, d.department_name
ORDER BY avg_salary DESC;

-- GROUP BY multiple columns
SELECT
  department_id,
  job_title,
  COUNT(*) AS count,
  AVG(salary) AS avg_salary
FROM employees
GROUP BY department_id, job_title
ORDER BY department_id, job_title;

-- HAVING clause - filter AFTER grouping
-- Find departments with more than 3 employees
SELECT
  d.department_name,
  COUNT(e.employee_id) AS employee_count
FROM departments d
JOIN employees e ON d.department_id = e.department_id
GROUP BY d.department_id, d.department_name
HAVING COUNT(e.employee_id) > 3
ORDER BY employee_count DESC;

-- WHERE vs HAVING
-- WHERE filters rows BEFORE grouping
-- HAVING filters groups AFTER grouping
SELECT
  d.department_name,
  COUNT(e.employee_id) AS employee_count,
  AVG(e.salary) AS avg_salary
FROM departments d
JOIN employees e ON d.department_id = e.department_id
WHERE e.is_active = TRUE              -- Filter before grouping
GROUP BY d.department_id, d.department_name
HAVING AVG(e.salary) > 60000          -- Filter after grouping
ORDER BY avg_salary DESC;

-- Products by category with sales info
SELECT
  c.category_name,
  COUNT(p.product_id) AS product_count,
  SUM(p.units_in_stock) AS total_stock
FROM categories c
LEFT JOIN products p ON c.category_id = p.category_id
GROUP BY c.category_id, c.category_name
HAVING COUNT(p.product_id) > 0
ORDER BY product_count DESC;

-- Order totals by customer
SELECT
  c.first_name || ' ' || c.last_name AS customer_name,
  COUNT(o.order_id) AS order_count,
  SUM(oi.quantity * oi.unit_price * (1 - oi.discount)) AS total_spent
FROM customers c
JOIN orders o ON c.customer_id = o.customer_id
JOIN order_items oi ON o.order_id = oi.order_id
GROUP BY c.customer_id, c.first_name, c.last_name
HAVING SUM(oi.quantity * oi.unit_price * (1 - oi.discount)) > 500
ORDER BY total_spent DESC;
