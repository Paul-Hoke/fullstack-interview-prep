-- =====================================================
-- Q06: What are aggregate functions in SQL?
-- =====================================================

-- COUNT - count rows
SELECT COUNT(*) AS total_employees FROM employees;
SELECT COUNT(commission_pct) AS employees_with_commission FROM employees;
SELECT COUNT(DISTINCT department_id) AS department_count FROM employees;

-- SUM - sum numeric values
SELECT SUM(salary) AS total_payroll FROM employees;
SELECT SUM(units_in_stock) AS total_inventory FROM products;

-- AVG - average value
SELECT AVG(salary) AS average_salary FROM employees;
SELECT ROUND(AVG(salary), 2) AS average_salary FROM employees;

-- MIN and MAX
SELECT
  MIN(salary) AS lowest_salary,
  MAX(salary) AS highest_salary
FROM employees;

SELECT
  MIN(hire_date) AS first_hire,
  MAX(hire_date) AS latest_hire
FROM employees;

-- Combining aggregates
SELECT
  COUNT(*) AS employee_count,
  SUM(salary) AS total_payroll,
  ROUND(AVG(salary), 2) AS avg_salary,
  MIN(salary) AS min_salary,
  MAX(salary) AS max_salary,
  MAX(salary) - MIN(salary) AS salary_range
FROM employees
WHERE is_active = TRUE;

-- Aggregates with GROUP BY
SELECT
  department_id,
  COUNT(*) AS count,
  SUM(salary) AS total,
  ROUND(AVG(salary), 2) AS average
FROM employees
GROUP BY department_id;

-- Aggregates with expressions
SELECT
  SUM(quantity * unit_price) AS gross_total,
  SUM(quantity * unit_price * discount) AS total_discount,
  SUM(quantity * unit_price * (1 - discount)) AS net_total
FROM order_items;

-- Conditional aggregation with CASE
SELECT
  COUNT(*) AS total_employees,
  COUNT(CASE WHEN salary >= 70000 THEN 1 END) AS high_earners,
  COUNT(CASE WHEN salary < 70000 THEN 1 END) AS other_employees,
  SUM(CASE WHEN is_active THEN salary ELSE 0 END) AS active_payroll
FROM employees;

-- Aggregate with FILTER (H2 supports this)
SELECT
  COUNT(*) AS total_orders,
  COUNT(*) FILTER (WHERE status = 'DELIVERED') AS delivered_count,
  COUNT(*) FILTER (WHERE status = 'PENDING') AS pending_count,
  COUNT(*) FILTER (WHERE status = 'SHIPPED') AS shipped_count
FROM orders;
