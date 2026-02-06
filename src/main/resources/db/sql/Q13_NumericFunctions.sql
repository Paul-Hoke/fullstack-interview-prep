-- =====================================================
-- Q13: What are common numeric functions in SQL?
-- =====================================================

-- ROUND - round to decimal places
SELECT
  salary,
  salary / 12 AS monthly_raw,
  ROUND(salary / 12, 2) AS monthly_rounded,
  ROUND(salary / 12, 0) AS monthly_no_decimals
FROM employees;

-- CEIL / CEILING - round up
SELECT
  unit_price,
  CEILING(unit_price) AS rounded_up
FROM products;

-- FLOOR - round down
SELECT
  unit_price,
  FLOOR(unit_price) AS rounded_down
FROM products;

-- TRUNCATE - truncate to decimal places
SELECT
  salary / 12 AS monthly_raw,
  TRUNCATE(salary / 12, 2) AS monthly_truncated
FROM employees;

-- ABS - absolute value
SELECT
  quantity_change,
  ABS(quantity_change) AS absolute_change
FROM inventory;

-- MOD - modulo (remainder)
SELECT
  employee_id,
  MOD(employee_id, 2) AS is_odd,
  CASE WHEN MOD(employee_id, 2) = 0 THEN 'Even' ELSE 'Odd' END AS parity
FROM employees;

-- POWER - exponentiation
SELECT
  2 AS base,
  POWER(2, 10) AS two_to_ten;  -- 1024

-- SQRT - square root
SELECT
  units_in_stock,
  SQRT(units_in_stock) AS sqrt_stock
FROM products
WHERE units_in_stock > 0;

-- SIGN - returns -1, 0, or 1
SELECT
  quantity_change,
  SIGN(quantity_change) AS change_sign
FROM inventory;

-- LOG / LN - logarithms
SELECT
  units_in_stock,
  LOG10(units_in_stock + 1) AS log10_stock,
  LN(units_in_stock + 1) AS natural_log
FROM products
WHERE units_in_stock > 0;

-- RANDOM - random number
SELECT
  first_name,
  RANDOM() AS random_value
FROM employees;

-- Practical: Calculate percentage
SELECT
  d.department_name,
  COUNT(e.employee_id) AS emp_count,
  ROUND(COUNT(e.employee_id) * 100.0 / (SELECT COUNT(*) FROM employees), 2) AS percentage
FROM departments d
LEFT JOIN employees e ON d.department_id = e.department_id
GROUP BY d.department_id, d.department_name
ORDER BY percentage DESC;

-- Practical: Running calculations
SELECT
  product_name,
  unit_price,
  units_in_stock,
  unit_price * units_in_stock AS inventory_value,
  ROUND(unit_price * 1.1, 2) AS price_with_10pct_markup
FROM products;

-- Practical: Statistical calculation
SELECT
  ROUND(AVG(salary), 2) AS mean_salary,
  ROUND(STDDEV(salary), 2) AS std_dev,
  ROUND(VARIANCE(salary), 2) AS variance
FROM employees;

-- Practical: Percentile-like calculation
SELECT
  first_name,
  salary,
  ROUND((salary - (SELECT MIN(salary) FROM employees)) * 100.0 /
        ((SELECT MAX(salary) FROM employees) - (SELECT MIN(salary) FROM employees)), 1) AS percentile
FROM employees
ORDER BY percentile DESC;
