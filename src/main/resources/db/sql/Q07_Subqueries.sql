-- =====================================================
-- Q07: What are subqueries and how do you use them?
-- =====================================================

-- Subquery in WHERE clause (scalar subquery)
-- Find employees earning more than average
SELECT first_name, last_name, salary
FROM employees
WHERE salary > (SELECT AVG(salary) FROM employees);

-- Subquery with IN
-- Find employees in departments located in 'New York'
SELECT first_name, last_name, department_id
FROM employees
WHERE department_id IN (
  SELECT department_id
  FROM departments
  WHERE location = 'New York'
);

-- Subquery with NOT IN
-- Find customers who have never placed an order
SELECT first_name, last_name, email
FROM customers
WHERE customer_id NOT IN (
  SELECT DISTINCT customer_id FROM orders
);

-- Subquery with EXISTS
-- Find departments that have at least one employee
SELECT department_name
FROM departments d
WHERE EXISTS (
  SELECT 1
  FROM employees e
  WHERE e.department_id = d.department_id
);

-- Subquery with NOT EXISTS
-- Find products that have never been ordered
SELECT product_name, unit_price
FROM products p
WHERE NOT EXISTS (
  SELECT 1
  FROM order_items oi
  WHERE oi.product_id = p.product_id
);

-- Correlated subquery
-- Find employees who earn more than their department average
SELECT e.first_name, e.last_name, e.salary, e.department_id
FROM employees e
WHERE e.salary > (
  SELECT AVG(e2.salary)
  FROM employees e2
  WHERE e2.department_id = e.department_id
);

-- Subquery in SELECT clause
SELECT
  e.first_name,
  e.last_name,
  e.salary,
  (SELECT AVG(salary) FROM employees) AS company_avg,
  e.salary - (SELECT AVG(salary) FROM employees) AS diff_from_avg
FROM employees e
ORDER BY diff_from_avg DESC;

-- Subquery in FROM clause (derived table)
SELECT
  dept_stats.department_id,
  dept_stats.avg_salary,
  dept_stats.employee_count
FROM (
  SELECT
    department_id,
    ROUND(AVG(salary), 2) AS avg_salary,
    COUNT(*) AS employee_count
  FROM employees
  GROUP BY department_id
) AS dept_stats
WHERE dept_stats.employee_count > 3;

-- Multiple subqueries
SELECT first_name, last_name, salary
FROM employees
WHERE salary = (SELECT MAX(salary) FROM employees)
   OR salary = (SELECT MIN(salary) FROM employees);

-- Subquery with ALL
-- Find employees who earn more than ALL employees in department 3
SELECT first_name, last_name, salary
FROM employees
WHERE salary > ALL (
  SELECT salary
  FROM employees
  WHERE department_id = 3
);

-- Subquery with ANY/SOME
-- Find employees who earn more than ANY employee in department 1
SELECT first_name, last_name, salary, department_id
FROM employees
WHERE department_id != 1
  AND salary > ANY (
    SELECT salary
    FROM employees
    WHERE department_id = 1
  );
