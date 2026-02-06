-- =====================================================
-- Q29: What are the common SQL data types?
-- =====================================================

-- =====================================================
-- Numeric Types
-- =====================================================

-- Integer types
-- TINYINT: -128 to 127 (1 byte)
-- SMALLINT: -32,768 to 32,767 (2 bytes)
-- INT/INTEGER: ~-2 billion to ~2 billion (4 bytes)
-- BIGINT: very large integers (8 bytes)

SELECT
  CAST(100 AS TINYINT) AS tiny,
  CAST(30000 AS SMALLINT) AS small,
  CAST(2000000000 AS INT) AS regular,
  CAST(9000000000000000000 AS BIGINT) AS big;

-- Decimal/Numeric (exact precision)
-- DECIMAL(precision, scale) or NUMERIC(precision, scale)
-- precision: total digits, scale: digits after decimal

SELECT
  CAST(123.45 AS DECIMAL(5,2)) AS dec_5_2,      -- 123.45
  CAST(123.456 AS DECIMAL(10,2)) AS dec_10_2;   -- 123.46 (rounded)

-- Used in our schema for money: salary DECIMAL(10, 2)

-- Floating point (approximate)
-- REAL/FLOAT: 4 bytes
-- DOUBLE: 8 bytes

SELECT
  CAST(3.14159265359 AS REAL) AS real_val,
  CAST(3.14159265359 AS DOUBLE) AS double_val;

-- =====================================================
-- String Types
-- =====================================================

-- CHAR(n): Fixed length, padded with spaces
-- VARCHAR(n): Variable length, no padding
-- TEXT/CLOB: Large text

SELECT
  CAST('Hello' AS CHAR(10)) AS fixed,      -- 'Hello     ' (padded)
  CAST('Hello' AS VARCHAR(10)) AS variable, -- 'Hello'
  LENGTH(CAST('Hello' AS CHAR(10))) AS char_len,
  LENGTH(CAST('Hello' AS VARCHAR(10))) AS varchar_len;

-- Used in our schema: first_name VARCHAR(50)

-- =====================================================
-- Date and Time Types
-- =====================================================

-- DATE: Date only (year, month, day)
-- TIME: Time only (hour, minute, second)
-- TIMESTAMP/DATETIME: Date and time
-- INTERVAL: Time duration

SELECT
  CURRENT_DATE AS today,
  CURRENT_TIME AS now_time,
  CURRENT_TIMESTAMP AS now_full,
  CAST('2024-12-25' AS DATE) AS christmas,
  CAST('14:30:00' AS TIME) AS afternoon;

-- Date arithmetic
SELECT
  CURRENT_DATE AS today,
  CURRENT_DATE + 7 AS next_week,
  CURRENT_DATE - 30 AS last_month;

-- =====================================================
-- Boolean Type
-- =====================================================

SELECT
  TRUE AS yes,
  FALSE AS no,
  NOT TRUE AS negated;

-- Used in our schema: is_active BOOLEAN DEFAULT TRUE

-- =====================================================
-- Binary Types
-- =====================================================

-- BINARY(n): Fixed-length binary
-- VARBINARY(n): Variable-length binary
-- BLOB: Large binary objects (images, files)

-- =====================================================
-- Special Types
-- =====================================================

-- UUID: Universally Unique Identifier
SELECT RANDOM_UUID() AS new_uuid;

-- ARRAY (H2 supports)
SELECT ARRAY[1, 2, 3, 4, 5] AS int_array;

-- JSON (in databases that support it)
-- SELECT '{"name": "John", "age": 30}'::JSON;

-- =====================================================
-- Type Conversion
-- =====================================================

-- CAST function
SELECT
  CAST('123' AS INT) AS str_to_int,
  CAST(123 AS VARCHAR) AS int_to_str,
  CAST('2024-01-15' AS DATE) AS str_to_date,
  CAST(123.456 AS INT) AS decimal_to_int;  -- Truncates to 123

-- Implicit conversion
SELECT
  '100' + 50 AS implicit_add,  -- 150 (string converted to number)
  100 || ' items' AS implicit_concat;  -- '100 items'

-- =====================================================
-- Choosing the Right Data Type
-- =====================================================

-- Best practices:
-- 1. Use smallest type that fits your data
-- 2. Use DECIMAL for money (not FLOAT)
-- 3. Use DATE/TIMESTAMP for dates (not VARCHAR)
-- 4. Use BOOLEAN for true/false (not INT or CHAR)
-- 5. Use VARCHAR for variable-length strings
-- 6. Consider storage and performance implications

-- Example: Our schema choices
-- employee_id INT - sufficient for employee count
-- salary DECIMAL(10, 2) - exact for money
-- hire_date DATE - proper date handling
-- email VARCHAR(100) - variable length text
-- is_active BOOLEAN - clear true/false
