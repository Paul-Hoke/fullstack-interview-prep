-- =====================================================
-- Q12: What are common date/time functions in SQL?
-- =====================================================

-- Current date and time
SELECT
  CURRENT_DATE AS today,
  CURRENT_TIME AS current_time,
  CURRENT_TIMESTAMP AS current_timestamp,
  NOW() AS now_function;

-- Extract parts of a date
SELECT
  hire_date,
  EXTRACT(YEAR FROM hire_date) AS hire_year,
  EXTRACT(MONTH FROM hire_date) AS hire_month,
  EXTRACT(DAY FROM hire_date) AS hire_day,
  EXTRACT(DAYOFWEEK FROM hire_date) AS day_of_week,
  EXTRACT(QUARTER FROM hire_date) AS quarter
FROM employees;

-- YEAR, MONTH, DAY functions
SELECT
  hire_date,
  YEAR(hire_date) AS hire_year,
  MONTH(hire_date) AS hire_month,
  DAY(hire_date) AS hire_day,
  DAYOFWEEK(hire_date) AS day_of_week,
  DAYNAME(hire_date) AS day_name,
  MONTHNAME(hire_date) AS month_name
FROM employees;

-- Date arithmetic
SELECT
  hire_date,
  DATEADD('YEAR', 1, hire_date) AS one_year_later,
  DATEADD('MONTH', 6, hire_date) AS six_months_later,
  DATEADD('DAY', 30, hire_date) AS thirty_days_later
FROM employees;

-- Date difference
SELECT
  first_name,
  last_name,
  hire_date,
  DATEDIFF('YEAR', hire_date, CURRENT_DATE) AS years_employed,
  DATEDIFF('MONTH', hire_date, CURRENT_DATE) AS months_employed,
  DATEDIFF('DAY', hire_date, CURRENT_DATE) AS days_employed
FROM employees
ORDER BY years_employed DESC;

-- Formatting dates (H2 uses FORMATDATETIME)
SELECT
  order_date,
  FORMATDATETIME(order_date, 'yyyy-MM-dd') AS formatted_date,
  FORMATDATETIME(order_date, 'MMMM dd, yyyy') AS long_format,
  FORMATDATETIME(order_date, 'MM/dd/yy') AS short_format
FROM orders;

-- Truncate date to specific precision
SELECT
  order_date,
  TRUNC(order_date, 'MONTH') AS month_start,
  TRUNC(order_date, 'YEAR') AS year_start
FROM orders;

-- Group by date parts
SELECT
  YEAR(order_date) AS order_year,
  MONTH(order_date) AS order_month,
  COUNT(*) AS order_count
FROM orders
GROUP BY YEAR(order_date), MONTH(order_date)
ORDER BY order_year, order_month;

-- Find orders in last 30 days
SELECT *
FROM orders
WHERE order_date >= DATEADD('DAY', -30, CURRENT_DATE);

-- Date ranges
SELECT
  first_name,
  hire_date,
  CASE
    WHEN hire_date >= DATEADD('YEAR', -1, CURRENT_DATE) THEN 'New Hire'
    WHEN hire_date >= DATEADD('YEAR', -3, CURRENT_DATE) THEN 'Recent'
    WHEN hire_date >= DATEADD('YEAR', -5, CURRENT_DATE) THEN 'Experienced'
    ELSE 'Veteran'
  END AS tenure_category
FROM employees;

-- Orders by day of week
SELECT
  DAYNAME(order_date) AS day_name,
  COUNT(*) AS order_count
FROM orders
GROUP BY DAYOFWEEK(order_date), DAYNAME(order_date)
ORDER BY DAYOFWEEK(order_date);

-- Calculate age in years
SELECT
  first_name,
  last_name,
  hire_date,
  DATEDIFF('YEAR', hire_date, CURRENT_DATE) AS years_of_service
FROM employees
ORDER BY years_of_service DESC;
