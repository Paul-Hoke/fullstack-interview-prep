-- =====================================================
-- Q11: What are common string functions in SQL?
-- =====================================================

-- CONCAT / || - concatenate strings
SELECT
  first_name || ' ' || last_name AS full_name,
  CONCAT(first_name, ' ', last_name) AS full_name_concat
FROM employees;

-- LENGTH - string length
SELECT
  first_name,
  LENGTH(first_name) AS name_length
FROM employees
ORDER BY name_length DESC;

-- UPPER / LOWER - case conversion
SELECT
  first_name,
  UPPER(first_name) AS upper_name,
  LOWER(first_name) AS lower_name
FROM employees;

-- TRIM - remove whitespace
SELECT
  TRIM('  Hello World  ') AS trimmed,
  LTRIM('  Hello') AS left_trimmed,
  RTRIM('Hello  ') AS right_trimmed;

-- SUBSTRING / SUBSTR - extract part of string
SELECT
  email,
  SUBSTRING(email, 1, POSITION('@' IN email) - 1) AS username,
  SUBSTRING(email FROM POSITION('@' IN email) + 1) AS domain
FROM employees;

-- POSITION / LOCATE - find substring position
SELECT
  email,
  POSITION('@' IN email) AS at_position,
  LOCATE('.com', email) AS dotcom_position
FROM employees;

-- REPLACE - replace substring
SELECT
  email,
  REPLACE(email, '.com', '.org') AS modified_email
FROM employees;

-- LEFT / RIGHT - extract from start/end
SELECT
  product_name,
  LEFT(product_name, 10) AS first_10_chars,
  RIGHT(product_name, 5) AS last_5_chars
FROM products;

-- LPAD / RPAD - pad string to length
SELECT
  employee_id,
  LPAD(CAST(employee_id AS VARCHAR), 5, '0') AS padded_id
FROM employees;

-- REVERSE - reverse string
SELECT
  first_name,
  REVERSE(first_name) AS reversed
FROM employees;

-- REPEAT - repeat string
SELECT REPEAT('*', 10) AS stars;

-- String in WHERE clause
SELECT * FROM employees
WHERE LOWER(first_name) LIKE 'j%';

SELECT * FROM products
WHERE UPPER(product_name) LIKE '%PRO%';

-- Combining string functions
SELECT
  first_name,
  last_name,
  UPPER(LEFT(first_name, 1)) || LOWER(SUBSTRING(first_name, 2)) ||
  ' ' ||
  UPPER(LEFT(last_name, 1)) || LOWER(SUBSTRING(last_name, 2)) AS proper_case_name
FROM employees;

-- String aggregation (H2 uses LISTAGG or GROUP_CONCAT)
SELECT
  department_id,
  LISTAGG(first_name, ', ') WITHIN GROUP (ORDER BY first_name) AS employee_names
FROM employees
GROUP BY department_id;
