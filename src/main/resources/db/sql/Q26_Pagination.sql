-- =====================================================
-- Q26: How do you implement pagination in SQL?
-- =====================================================

-- Pagination retrieves data in chunks (pages) for display.

-- =====================================================
-- Basic LIMIT and OFFSET
-- =====================================================

-- Page 1: First 10 rows
SELECT employee_id, first_name, last_name, salary
FROM employees
ORDER BY employee_id
LIMIT 10 OFFSET 0;

-- Page 2: Next 10 rows
SELECT employee_id, first_name, last_name, salary
FROM employees
ORDER BY employee_id
LIMIT 10 OFFSET 10;

-- Page 3: Next 10 rows
SELECT employee_id, first_name, last_name, salary
FROM employees
ORDER BY employee_id
LIMIT 10 OFFSET 20;

-- Formula: OFFSET = (page_number - 1) * page_size

-- =====================================================
-- Pagination with total count
-- =====================================================

-- Get results and total count in separate queries
SELECT COUNT(*) AS total_count FROM employees;

SELECT employee_id, first_name, last_name, salary
FROM employees
ORDER BY employee_id
LIMIT 10 OFFSET 0;

-- =====================================================
-- Using ROW_NUMBER() for pagination
-- =====================================================

SELECT *
FROM (
  SELECT
    employee_id,
    first_name,
    last_name,
    salary,
    ROW_NUMBER() OVER (ORDER BY employee_id) AS row_num
  FROM employees
) numbered
WHERE row_num BETWEEN 11 AND 20;  -- Page 2 (rows 11-20)

-- =====================================================
-- Keyset pagination (cursor-based) - More efficient for large datasets
-- =====================================================

-- Instead of OFFSET, use WHERE on sorted column
-- First page
SELECT employee_id, first_name, last_name, salary
FROM employees
ORDER BY employee_id
LIMIT 10;

-- Next page (assuming last ID from previous page was 10)
SELECT employee_id, first_name, last_name, salary
FROM employees
WHERE employee_id > 10  -- Start after last seen ID
ORDER BY employee_id
LIMIT 10;

-- Keyset with composite key (when sorting by non-unique column)
-- First page sorted by salary
SELECT employee_id, first_name, salary
FROM employees
ORDER BY salary DESC, employee_id
LIMIT 5;

-- Next page (after salary=72000, id=15)
SELECT employee_id, first_name, salary
FROM employees
WHERE (salary, employee_id) < (72000, 15)  -- Tuple comparison
ORDER BY salary DESC, employee_id
LIMIT 5;

-- =====================================================
-- Pagination with search/filter
-- =====================================================

-- Filtered pagination
SELECT employee_id, first_name, last_name, salary
FROM employees
WHERE department_id = 1
ORDER BY salary DESC
LIMIT 5 OFFSET 0;

-- =====================================================
-- Performance considerations
-- =====================================================

-- OFFSET becomes slow for large offsets (must skip all rows)
-- LIMIT 10 OFFSET 100000 still scans 100000 rows

-- Solutions:
-- 1. Use keyset/cursor pagination
-- 2. Cache result IDs
-- 3. Denormalize with row numbers

-- Analyze query plan
EXPLAIN
SELECT * FROM employees
ORDER BY employee_id
LIMIT 10 OFFSET 1000;

-- =====================================================
-- Pagination metadata
-- =====================================================

-- Calculate pagination info
SELECT
  COUNT(*) AS total_records,
  CEILING(COUNT(*) / 10.0) AS total_pages,  -- 10 items per page
  10 AS page_size
FROM employees;

-- Summary: Pagination approaches
-- 1. LIMIT/OFFSET: Simple, but slow for large offsets
-- 2. ROW_NUMBER(): Flexible, but still needs to number all rows
-- 3. Keyset: Most efficient, but can't jump to arbitrary page
