-- =====================================================
-- Q10: How do you use CASE expressions?
-- =====================================================

-- Simple CASE expression
SELECT
  first_name,
  last_name,
  department_id,
  CASE department_id
    WHEN 1 THEN 'Engineering'
    WHEN 2 THEN 'Sales'
    WHEN 3 THEN 'Marketing'
    WHEN 4 THEN 'HR'
    ELSE 'Other'
  END AS department_name
FROM employees;

-- Searched CASE expression (more flexible)
SELECT
  first_name,
  last_name,
  salary,
  CASE
    WHEN salary >= 90000 THEN 'Executive'
    WHEN salary >= 70000 THEN 'Senior'
    WHEN salary >= 50000 THEN 'Mid-Level'
    ELSE 'Junior'
  END AS salary_grade
FROM employees
ORDER BY salary DESC;

-- CASE in ORDER BY
SELECT first_name, last_name, job_title
FROM employees
ORDER BY
  CASE job_title
    WHEN 'CEO' THEN 1
    WHEN 'CTO' THEN 2
    WHEN 'VP of Engineering' THEN 3
    WHEN 'Director of Engineering' THEN 4
    ELSE 5
  END;

-- CASE with aggregates (conditional counting)
SELECT
  COUNT(*) AS total_employees,
  SUM(CASE WHEN salary >= 70000 THEN 1 ELSE 0 END) AS high_earners,
  SUM(CASE WHEN salary < 70000 THEN 1 ELSE 0 END) AS other_earners,
  SUM(CASE WHEN is_active THEN 1 ELSE 0 END) AS active_count
FROM employees;

-- Pivot-like query using CASE
SELECT
  d.department_name,
  COUNT(*) AS total,
  SUM(CASE WHEN e.salary >= 70000 THEN 1 ELSE 0 END) AS high_earners,
  SUM(CASE WHEN e.salary < 70000 THEN 1 ELSE 0 END) AS other_earners
FROM departments d
LEFT JOIN employees e ON d.department_id = e.department_id
GROUP BY d.department_id, d.department_name;

-- CASE in WHERE clause
SELECT first_name, last_name, salary, department_id
FROM employees
WHERE
  CASE
    WHEN department_id = 1 THEN salary > 70000
    WHEN department_id = 2 THEN salary > 60000
    ELSE salary > 50000
  END;

-- Nested CASE
SELECT
  product_name,
  units_in_stock,
  reorder_level,
  CASE
    WHEN discontinued THEN 'Discontinued'
    ELSE
      CASE
        WHEN units_in_stock = 0 THEN 'Out of Stock'
        WHEN units_in_stock <= reorder_level THEN 'Low Stock'
        WHEN units_in_stock <= reorder_level * 2 THEN 'Normal'
        ELSE 'Well Stocked'
      END
  END AS stock_status
FROM products;

-- CASE for data transformation
SELECT
  o.order_id,
  o.order_date,
  o.status,
  CASE o.status
    WHEN 'PENDING' THEN 1
    WHEN 'PROCESSING' THEN 2
    WHEN 'SHIPPED' THEN 3
    WHEN 'DELIVERED' THEN 4
    WHEN 'CANCELLED' THEN 0
    ELSE -1
  END AS status_code,
  CASE
    WHEN o.shipped_date IS NULL THEN 'Not shipped'
    WHEN o.shipped_date <= o.required_date THEN 'On time'
    ELSE 'Late'
  END AS shipping_status
FROM orders o;
