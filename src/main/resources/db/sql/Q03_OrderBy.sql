-- =====================================================
-- Q03: How do you sort results using ORDER BY?
-- =====================================================

-- Basic ascending order (default)
SELECT first_name, last_name, salary
FROM employees
ORDER BY salary;

-- Explicit ascending order
SELECT first_name, last_name, salary
FROM employees
ORDER BY salary ASC;

-- Descending order
SELECT first_name, last_name, salary
FROM employees
ORDER BY salary DESC;

-- Order by multiple columns
SELECT first_name, last_name, department_id, salary
FROM employees
ORDER BY department_id ASC, salary DESC;

-- Order by column position (not recommended for readability)
SELECT first_name, last_name, salary
FROM employees
ORDER BY 3 DESC;  -- 3rd column (salary)

-- Order by expression
SELECT first_name, last_name, salary, commission_pct,
       salary * (1 + COALESCE(commission_pct, 0)) AS total_compensation
FROM employees
ORDER BY salary * (1 + COALESCE(commission_pct, 0)) DESC;

-- Order by alias
SELECT first_name, last_name,
       salary * 12 AS annual_salary
FROM employees
ORDER BY annual_salary DESC;

-- NULL handling in ORDER BY
-- NULLS FIRST / NULLS LAST (H2 supports this)
SELECT first_name, last_name, commission_pct
FROM employees
ORDER BY commission_pct NULLS LAST;

SELECT first_name, last_name, manager_id
FROM employees
ORDER BY manager_id NULLS FIRST;

-- Combining with WHERE and LIMIT
SELECT first_name, last_name, salary
FROM employees
WHERE department_id = 1
ORDER BY salary DESC
LIMIT 3;
