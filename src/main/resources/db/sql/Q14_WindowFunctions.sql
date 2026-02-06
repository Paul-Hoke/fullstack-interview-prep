-- =====================================================
-- Q14: What are window functions?
-- =====================================================

-- Window functions perform calculations across a set of rows
-- related to the current row, without collapsing the result.

-- ROW_NUMBER - unique sequential number
SELECT
  first_name,
  last_name,
  department_id,
  salary,
  ROW_NUMBER() OVER (ORDER BY salary DESC) AS salary_rank
FROM employees;

-- ROW_NUMBER with PARTITION BY
SELECT
  first_name,
  last_name,
  department_id,
  salary,
  ROW_NUMBER() OVER (PARTITION BY department_id ORDER BY salary DESC) AS dept_rank
FROM employees;

-- RANK - same rank for ties, gaps after
SELECT
  first_name,
  salary,
  RANK() OVER (ORDER BY salary DESC) AS salary_rank
FROM employees;

-- DENSE_RANK - same rank for ties, no gaps
SELECT
  first_name,
  salary,
  DENSE_RANK() OVER (ORDER BY salary DESC) AS salary_dense_rank
FROM employees;

-- Compare ROW_NUMBER, RANK, and DENSE_RANK
SELECT
  first_name,
  salary,
  ROW_NUMBER() OVER (ORDER BY salary DESC) AS row_num,
  RANK() OVER (ORDER BY salary DESC) AS rank,
  DENSE_RANK() OVER (ORDER BY salary DESC) AS dense_rank
FROM employees;

-- NTILE - divide into n groups
SELECT
  first_name,
  salary,
  NTILE(4) OVER (ORDER BY salary DESC) AS salary_quartile
FROM employees;

-- LAG - access previous row
SELECT
  order_id,
  order_date,
  customer_id,
  LAG(order_date, 1) OVER (PARTITION BY customer_id ORDER BY order_date) AS prev_order_date,
  DATEDIFF('DAY', LAG(order_date) OVER (PARTITION BY customer_id ORDER BY order_date), order_date) AS days_since_last_order
FROM orders;

-- LEAD - access next row
SELECT
  order_id,
  order_date,
  LEAD(order_date, 1) OVER (ORDER BY order_date) AS next_order_date
FROM orders;

-- SUM as window function - running total
SELECT
  order_id,
  order_date,
  (SELECT SUM(oi.quantity * oi.unit_price) FROM order_items oi WHERE oi.order_id = o.order_id) AS order_total,
  SUM((SELECT SUM(oi.quantity * oi.unit_price) FROM order_items oi WHERE oi.order_id = o.order_id))
      OVER (ORDER BY order_date) AS running_total
FROM orders o
ORDER BY order_date;

-- AVG as window function
SELECT
  first_name,
  department_id,
  salary,
  ROUND(AVG(salary) OVER (PARTITION BY department_id), 2) AS dept_avg,
  salary - AVG(salary) OVER (PARTITION BY department_id) AS diff_from_avg
FROM employees;

-- FIRST_VALUE / LAST_VALUE
SELECT
  first_name,
  department_id,
  salary,
  FIRST_VALUE(first_name) OVER (PARTITION BY department_id ORDER BY salary DESC) AS highest_paid,
  FIRST_VALUE(salary) OVER (PARTITION BY department_id ORDER BY salary DESC) AS max_salary
FROM employees;

-- Window frame specification
SELECT
  order_id,
  order_date,
  SUM(1) OVER (ORDER BY order_date ROWS BETWEEN 2 PRECEDING AND CURRENT ROW) AS orders_last_3
FROM orders;

-- Practical: Find top 3 earners per department
SELECT * FROM (
  SELECT
    first_name,
    last_name,
    department_id,
    salary,
    DENSE_RANK() OVER (PARTITION BY department_id ORDER BY salary DESC) AS dept_rank
  FROM employees
) ranked
WHERE dept_rank <= 3
ORDER BY department_id, dept_rank;
