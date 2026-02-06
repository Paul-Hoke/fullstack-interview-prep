-- =====================================================
-- Q09: How do you handle NULL values in SQL?
-- =====================================================

-- NULL represents unknown or missing data
-- NULL is NOT equal to anything, including another NULL

-- Finding NULL values
SELECT * FROM employees WHERE commission_pct IS NULL;
SELECT * FROM employees WHERE manager_id IS NULL;  -- Top-level managers

-- Finding non-NULL values
SELECT * FROM employees WHERE commission_pct IS NOT NULL;

-- COALESCE - returns first non-NULL value
SELECT
  first_name,
  last_name,
  commission_pct,
  COALESCE(commission_pct, 0) AS commission_or_zero
FROM employees;

-- COALESCE with multiple fallbacks
SELECT
  c.first_name,
  c.last_name,
  COALESCE(c.phone, c.email, 'No contact info') AS contact
FROM customers c;

-- NULLIF - returns NULL if two values are equal
-- Useful to avoid division by zero
SELECT
  product_name,
  units_in_stock,
  units_on_order,
  units_in_stock / NULLIF(units_on_order, 0) AS stock_to_order_ratio
FROM products;

-- NVL equivalent in H2 (use COALESCE or IFNULL)
SELECT
  first_name,
  IFNULL(commission_pct, 0) AS commission
FROM employees;

-- NULL in calculations
-- Any arithmetic with NULL results in NULL
SELECT
  first_name,
  salary,
  commission_pct,
  salary * commission_pct AS commission_amount,  -- NULL if commission_pct is NULL
  salary * COALESCE(commission_pct, 0) AS safe_commission_amount
FROM employees;

-- NULL in aggregates
-- Most aggregates ignore NULL values
SELECT
  COUNT(*) AS total_rows,
  COUNT(commission_pct) AS non_null_commissions,
  AVG(commission_pct) AS avg_commission  -- Only averages non-NULL values
FROM employees;

-- NULL in comparisons
-- This returns no rows because NULL = NULL is unknown, not true
SELECT * FROM employees WHERE commission_pct = NULL;  -- Wrong!
SELECT * FROM employees WHERE commission_pct IS NULL;  -- Correct!

-- NULL in ORDER BY
SELECT first_name, last_name, commission_pct
FROM employees
ORDER BY commission_pct NULLS LAST;

-- CASE with NULL
SELECT
  first_name,
  last_name,
  commission_pct,
  CASE
    WHEN commission_pct IS NULL THEN 'No Commission'
    WHEN commission_pct < 0.1 THEN 'Low Commission'
    WHEN commission_pct < 0.2 THEN 'Medium Commission'
    ELSE 'High Commission'
  END AS commission_level
FROM employees;

-- NULL-safe equality (H2 supports IS NOT DISTINCT FROM)
SELECT * FROM employees e1
JOIN employees e2 ON e1.commission_pct IS NOT DISTINCT FROM e2.commission_pct
WHERE e1.employee_id < e2.employee_id;
