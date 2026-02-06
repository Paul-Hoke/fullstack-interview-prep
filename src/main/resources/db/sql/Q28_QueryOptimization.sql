-- =====================================================
-- Q28: How do you optimize SQL queries?
-- =====================================================

-- =====================================================
-- 1. Use EXPLAIN to understand query execution
-- =====================================================

EXPLAIN SELECT * FROM employees WHERE department_id = 1;

EXPLAIN SELECT e.first_name, d.department_name
FROM employees e
JOIN departments d ON e.department_id = d.department_id;

-- =====================================================
-- 2. Use indexes effectively
-- =====================================================

-- Index on columns in WHERE, JOIN, ORDER BY
-- Already created: idx_employees_dept, idx_employees_salary

-- Good: Uses index
EXPLAIN SELECT * FROM employees WHERE department_id = 1;

-- Bad: Function on indexed column prevents index use
EXPLAIN SELECT * FROM employees WHERE UPPER(first_name) = 'JOHN';

-- Better: Create functional index or change query
-- CREATE INDEX idx_emp_first_upper ON employees(UPPER(first_name));

-- =====================================================
-- 3. Avoid SELECT *
-- =====================================================

-- Bad: Fetches all columns
SELECT * FROM employees WHERE department_id = 1;

-- Good: Only needed columns
SELECT employee_id, first_name, last_name
FROM employees
WHERE department_id = 1;

-- =====================================================
-- 4. Use appropriate JOINs
-- =====================================================

-- Use INNER JOIN when you only need matching rows
SELECT e.first_name, d.department_name
FROM employees e
INNER JOIN departments d ON e.department_id = d.department_id;

-- Use LEFT JOIN only when you need all rows from left table

-- =====================================================
-- 5. Optimize subqueries
-- =====================================================

-- Slow: Correlated subquery (runs for each row)
EXPLAIN SELECT e.first_name, e.salary,
  (SELECT AVG(salary) FROM employees e2 WHERE e2.department_id = e.department_id) AS dept_avg
FROM employees e;

-- Faster: JOIN with derived table
EXPLAIN SELECT e.first_name, e.salary, da.dept_avg
FROM employees e
JOIN (
  SELECT department_id, AVG(salary) AS dept_avg
  FROM employees
  GROUP BY department_id
) da ON e.department_id = da.department_id;

-- =====================================================
-- 6. Use EXISTS instead of IN for large datasets
-- =====================================================

-- Potentially slow with large result set
SELECT * FROM employees
WHERE department_id IN (SELECT department_id FROM departments WHERE budget > 500000);

-- Better with EXISTS (stops at first match)
SELECT * FROM employees e
WHERE EXISTS (
  SELECT 1 FROM departments d
  WHERE d.department_id = e.department_id
    AND d.budget > 500000
);

-- =====================================================
-- 7. Limit result sets
-- =====================================================

-- Always use LIMIT when you only need subset
SELECT * FROM orders ORDER BY order_date DESC LIMIT 10;

-- =====================================================
-- 8. Avoid OR conditions when possible
-- =====================================================

-- Slow: OR may prevent index usage
SELECT * FROM employees
WHERE department_id = 1 OR department_id = 2;

-- Better: Use IN
SELECT * FROM employees
WHERE department_id IN (1, 2);

-- Or use UNION for complex cases
SELECT * FROM employees WHERE department_id = 1
UNION ALL
SELECT * FROM employees WHERE department_id = 2;

-- =====================================================
-- 9. Use appropriate data types
-- =====================================================

-- Join on same data types (avoid implicit conversion)
-- Use INT for IDs, not VARCHAR
-- Use DATE for dates, not VARCHAR

-- =====================================================
-- 10. Denormalize for read-heavy workloads
-- =====================================================

-- Create summary tables for frequently run reports
-- Use materialized views (not supported in H2, but common in other DBs)

-- Example: Pre-computed order totals
CREATE TABLE order_totals_cache AS
SELECT
  o.order_id,
  o.order_date,
  SUM(oi.quantity * oi.unit_price * (1 - oi.discount)) AS total
FROM orders o
JOIN order_items oi ON o.order_id = oi.order_id
GROUP BY o.order_id, o.order_date;

SELECT * FROM order_totals_cache WHERE total > 500;

-- Clean up
DROP TABLE IF EXISTS order_totals_cache;

-- =====================================================
-- 11. Batch operations
-- =====================================================

-- Instead of multiple single inserts
-- INSERT INTO table VALUES (1); INSERT INTO table VALUES (2);

-- Use multi-row insert
-- INSERT INTO table VALUES (1), (2), (3);

-- =====================================================
-- Summary: Query optimization checklist
-- =====================================================
-- 1. Check EXPLAIN output
-- 2. Add indexes for WHERE, JOIN, ORDER BY columns
-- 3. Avoid SELECT *, use specific columns
-- 4. Rewrite subqueries as JOINs when possible
-- 5. Use LIMIT to restrict results
-- 6. Avoid functions on indexed columns in WHERE
-- 7. Use appropriate isolation level
-- 8. Consider denormalization for read-heavy scenarios
