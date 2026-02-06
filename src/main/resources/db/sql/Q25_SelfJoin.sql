-- =====================================================
-- Q25: What is a self-join and when would you use it?
-- =====================================================

-- A SELF-JOIN is when a table is joined to itself.
-- Used when data in one row relates to data in another row of the same table.

-- =====================================================
-- Employee-Manager relationship
-- =====================================================

-- Find each employee and their manager
SELECT
  e.employee_id,
  e.first_name || ' ' || e.last_name AS employee_name,
  e.job_title AS employee_title,
  m.first_name || ' ' || m.last_name AS manager_name,
  m.job_title AS manager_title
FROM employees e
LEFT JOIN employees m ON e.manager_id = m.employee_id
ORDER BY m.employee_id NULLS FIRST, e.first_name;

-- Find employees who earn more than their manager
SELECT
  e.first_name || ' ' || e.last_name AS employee_name,
  e.salary AS employee_salary,
  m.first_name || ' ' || m.last_name AS manager_name,
  m.salary AS manager_salary
FROM employees e
JOIN employees m ON e.manager_id = m.employee_id
WHERE e.salary > m.salary;

-- Count direct reports for each manager
SELECT
  m.employee_id,
  m.first_name || ' ' || m.last_name AS manager_name,
  COUNT(e.employee_id) AS direct_reports
FROM employees m
JOIN employees e ON e.manager_id = m.employee_id
GROUP BY m.employee_id, m.first_name, m.last_name
ORDER BY direct_reports DESC;

-- =====================================================
-- Category hierarchy (parent-child)
-- =====================================================

-- Find categories and their parents
SELECT
  c.category_id,
  c.category_name,
  p.category_name AS parent_category
FROM categories c
LEFT JOIN categories p ON c.parent_category_id = p.category_id
ORDER BY p.category_name NULLS FIRST, c.category_name;

-- Find top-level categories and their subcategories
SELECT
  p.category_name AS parent_category,
  c.category_name AS subcategory
FROM categories p
JOIN categories c ON c.parent_category_id = p.category_id
ORDER BY p.category_name, c.category_name;

-- =====================================================
-- Finding related rows
-- =====================================================

-- Find employees in the same department (pairs)
SELECT
  e1.first_name || ' ' || e1.last_name AS employee1,
  e2.first_name || ' ' || e2.last_name AS employee2,
  d.department_name
FROM employees e1
JOIN employees e2 ON e1.department_id = e2.department_id
                  AND e1.employee_id < e2.employee_id  -- Avoid duplicates
JOIN departments d ON e1.department_id = d.department_id
ORDER BY d.department_name, employee1;

-- Find employees with same job title
SELECT
  e1.first_name || ' ' || e1.last_name AS employee1,
  e2.first_name || ' ' || e2.last_name AS employee2,
  e1.job_title
FROM employees e1
JOIN employees e2 ON e1.job_title = e2.job_title
                  AND e1.employee_id < e2.employee_id
ORDER BY e1.job_title;

-- Find customers in the same city
SELECT
  c1.first_name || ' ' || c1.last_name AS customer1,
  c2.first_name || ' ' || c2.last_name AS customer2,
  c1.city
FROM customers c1
JOIN customers c2 ON c1.city = c2.city
                  AND c1.customer_id < c2.customer_id
WHERE c1.city IS NOT NULL
ORDER BY c1.city;

-- =====================================================
-- Salary comparisons
-- =====================================================

-- Find employees with salary higher than department average
SELECT
  e.first_name,
  e.last_name,
  e.salary,
  dept_avg.avg_salary,
  e.salary - dept_avg.avg_salary AS above_average
FROM employees e
JOIN (
  SELECT department_id, AVG(salary) AS avg_salary
  FROM employees
  GROUP BY department_id
) dept_avg ON e.department_id = dept_avg.department_id
WHERE e.salary > dept_avg.avg_salary
ORDER BY above_average DESC;

-- Common use cases for self-joins:
-- 1. Hierarchical data (org charts, categories)
-- 2. Finding duplicates
-- 3. Comparing rows within same table
-- 4. Time series (compare current row with previous)
-- 5. Sequential data (flights with connections)
