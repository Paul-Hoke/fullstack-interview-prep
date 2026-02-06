-- =====================================================
-- Q21: What are Common Table Expressions (CTEs)?
-- =====================================================

-- A CTE is a temporary named result set that you can
-- reference within a SELECT, INSERT, UPDATE, or DELETE.

-- Basic CTE
WITH high_earners AS (
  SELECT first_name, last_name, salary, department_id
  FROM employees
  WHERE salary > 70000
)
SELECT * FROM high_earners
ORDER BY salary DESC;

-- CTE with join
WITH dept_stats AS (
  SELECT
    department_id,
    COUNT(*) AS emp_count,
    ROUND(AVG(salary), 2) AS avg_salary,
    SUM(salary) AS total_salary
  FROM employees
  GROUP BY department_id
)
SELECT
  d.department_name,
  ds.emp_count,
  ds.avg_salary,
  ds.total_salary
FROM departments d
JOIN dept_stats ds ON d.department_id = ds.department_id
ORDER BY ds.avg_salary DESC;

-- Multiple CTEs
WITH
  order_totals AS (
    SELECT
      order_id,
      SUM(quantity * unit_price * (1 - discount)) AS total
    FROM order_items
    GROUP BY order_id
  ),
  customer_orders AS (
    SELECT
      c.customer_id,
      c.first_name || ' ' || c.last_name AS customer_name,
      COUNT(o.order_id) AS order_count,
      COALESCE(SUM(ot.total), 0) AS total_spent
    FROM customers c
    LEFT JOIN orders o ON c.customer_id = o.customer_id
    LEFT JOIN order_totals ot ON o.order_id = ot.order_id
    GROUP BY c.customer_id, c.first_name, c.last_name
  )
SELECT *
FROM customer_orders
WHERE order_count > 0
ORDER BY total_spent DESC;

-- Recursive CTE - employee hierarchy
WITH RECURSIVE emp_hierarchy AS (
  -- Anchor member: top-level managers (no manager)
  SELECT
    employee_id,
    first_name,
    last_name,
    manager_id,
    1 AS level,
    CAST(first_name || ' ' || last_name AS VARCHAR(500)) AS hierarchy_path
  FROM employees
  WHERE manager_id IS NULL

  UNION ALL

  -- Recursive member: employees with managers
  SELECT
    e.employee_id,
    e.first_name,
    e.last_name,
    e.manager_id,
    eh.level + 1,
    CAST(eh.hierarchy_path || ' > ' || e.first_name || ' ' || e.last_name AS VARCHAR(500))
  FROM employees e
  JOIN emp_hierarchy eh ON e.manager_id = eh.employee_id
)
SELECT
  employee_id,
  first_name,
  last_name,
  level,
  hierarchy_path
FROM emp_hierarchy
ORDER BY hierarchy_path;

-- Recursive CTE - category hierarchy
WITH RECURSIVE category_tree AS (
  SELECT
    category_id,
    category_name,
    parent_category_id,
    1 AS depth,
    CAST(category_name AS VARCHAR(500)) AS path
  FROM categories
  WHERE parent_category_id IS NULL

  UNION ALL

  SELECT
    c.category_id,
    c.category_name,
    c.parent_category_id,
    ct.depth + 1,
    CAST(ct.path || ' > ' || c.category_name AS VARCHAR(500))
  FROM categories c
  JOIN category_tree ct ON c.parent_category_id = ct.category_id
)
SELECT * FROM category_tree
ORDER BY path;

-- CTE for complex filtering
WITH ranked_salaries AS (
  SELECT
    employee_id,
    first_name,
    last_name,
    salary,
    department_id,
    DENSE_RANK() OVER (PARTITION BY department_id ORDER BY salary DESC) AS dept_rank
  FROM employees
)
SELECT *
FROM ranked_salaries
WHERE dept_rank <= 2
ORDER BY department_id, dept_rank;

-- CTE benefits:
-- 1. Improve readability of complex queries
-- 2. Allow recursive queries
-- 3. Can be referenced multiple times
-- 4. Break down complex logic into steps
