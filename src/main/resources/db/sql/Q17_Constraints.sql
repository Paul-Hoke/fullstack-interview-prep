-- =====================================================
-- Q17: What are constraints in SQL?
-- =====================================================

-- Constraints enforce data integrity rules at the database level.

-- PRIMARY KEY - uniquely identifies each row
-- Already defined in schema.sql for all tables
-- Example: employee_id INT PRIMARY KEY

-- FOREIGN KEY - ensures referential integrity
-- Example from schema.sql:
-- FOREIGN KEY (department_id) REFERENCES departments(department_id)

-- Demonstrate foreign key constraint
-- This would fail: INSERT INTO employees (employee_id, first_name, last_name, hire_date, department_id)
-- VALUES (999, 'Test', 'User', CURRENT_DATE, 999);  -- dept 999 doesn't exist

-- UNIQUE constraint
-- email VARCHAR(100) UNIQUE in employees table
-- This would fail:
-- INSERT INTO employees (employee_id, first_name, last_name, email, hire_date)
-- VALUES (999, 'Test', 'User', 'jsmith@company.com', CURRENT_DATE);  -- email exists

-- NOT NULL constraint
-- first_name VARCHAR(50) NOT NULL
-- This would fail:
-- INSERT INTO employees (employee_id, first_name, last_name, hire_date)
-- VALUES (999, NULL, 'User', CURRENT_DATE);

-- DEFAULT constraint
-- is_active BOOLEAN DEFAULT TRUE
INSERT INTO employees (employee_id, first_name, last_name, email, hire_date, department_id)
VALUES (100, 'Default', 'Test', 'default.test@company.com', CURRENT_DATE, 1);

SELECT employee_id, first_name, is_active FROM employees WHERE employee_id = 100;
-- is_active will be TRUE (default)

-- CHECK constraint (create a new table to demonstrate)
CREATE TABLE salary_grades (
  grade_id INT PRIMARY KEY,
  grade_name VARCHAR(20) NOT NULL,
  min_salary DECIMAL(10,2) NOT NULL,
  max_salary DECIMAL(10,2) NOT NULL,
  CONSTRAINT chk_salary_range CHECK (max_salary >= min_salary),
  CONSTRAINT chk_min_positive CHECK (min_salary > 0)
);

INSERT INTO salary_grades VALUES (1, 'Junior', 30000, 50000);
INSERT INTO salary_grades VALUES (2, 'Mid', 50000, 80000);
INSERT INTO salary_grades VALUES (3, 'Senior', 80000, 120000);

SELECT * FROM salary_grades;

-- This would fail the check constraint:
-- INSERT INTO salary_grades VALUES (4, 'Invalid', 100000, 50000);  -- max < min

-- Adding constraints to existing table
ALTER TABLE products
ADD CONSTRAINT chk_price_positive CHECK (unit_price > 0);

ALTER TABLE products
ADD CONSTRAINT chk_stock_non_negative CHECK (units_in_stock >= 0);

-- Viewing constraints (H2 specific)
SELECT
  TABLE_NAME,
  CONSTRAINT_NAME,
  CONSTRAINT_TYPE
FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS
WHERE TABLE_SCHEMA = 'PUBLIC'
ORDER BY TABLE_NAME, CONSTRAINT_TYPE;

-- Composite primary key example (order_items table)
-- PRIMARY KEY (order_id, product_id)

-- Clean up test data
DELETE FROM employees WHERE employee_id = 100;

-- Constraint naming best practices:
-- pk_tablename - Primary key
-- fk_tablename_column - Foreign key
-- uk_tablename_column - Unique key
-- chk_tablename_rule - Check constraint
