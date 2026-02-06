-- =====================================================
-- Q01: What is SQL and basic SELECT statement?
-- =====================================================
-- SQL (Structured Query Language) is a standard language for
-- managing and manipulating relational databases.

-- Basic SELECT - retrieve all columns
SELECT * FROM employees;

-- SELECT specific columns
SELECT first_name, last_name, salary FROM employees;

-- SELECT with column aliases
SELECT
  first_name AS "First Name",
  last_name AS "Last Name",
  salary AS "Annual Salary"
FROM employees;

-- SELECT with expressions
SELECT
  first_name,
  last_name,
  salary,
  salary * 12 AS annual_salary,
  salary * 1.1 AS salary_with_10pct_raise
FROM employees;

-- SELECT DISTINCT - remove duplicates
SELECT DISTINCT department_id FROM employees;

SELECT DISTINCT job_title FROM employees;

-- LIMIT results (H2/MySQL syntax)
SELECT first_name, last_name, salary
FROM employees
LIMIT 5;

-- OFFSET for pagination
SELECT first_name, last_name, salary
FROM employees
LIMIT 5 OFFSET 10;
