-- =====================================================
-- Q18: What are transactions and ACID properties?
-- =====================================================

-- A TRANSACTION is a sequence of operations that must be
-- executed as a single unit of work.

-- ACID Properties:
-- A - Atomicity: All or nothing
-- C - Consistency: Database stays valid
-- I - Isolation: Concurrent transactions don't interfere
-- D - Durability: Committed changes persist

-- Basic transaction (H2 auto-commits by default)
-- We need to set autocommit off for explicit transactions

SET AUTOCOMMIT OFF;

-- Start a transaction (implicit in most databases)
-- Transfer example: move an employee to a different department

-- Check initial state
SELECT employee_id, first_name, department_id FROM employees WHERE employee_id = 1;
SELECT department_name, budget FROM departments WHERE department_id IN (1, 2);

-- Begin transaction operations
UPDATE employees SET department_id = 2 WHERE employee_id = 1;

-- Verify change (before commit)
SELECT employee_id, first_name, department_id FROM employees WHERE employee_id = 1;

-- Rollback - undo all changes in this transaction
ROLLBACK;

-- Verify rollback
SELECT employee_id, first_name, department_id FROM employees WHERE employee_id = 1;

-- Now let's do a committed transaction
UPDATE employees SET department_id = 2 WHERE employee_id = 1;

-- Commit - make changes permanent
COMMIT;

-- Verify commit
SELECT employee_id, first_name, department_id FROM employees WHERE employee_id = 1;

-- Restore original state
UPDATE employees SET department_id = 1 WHERE employee_id = 1;
COMMIT;

-- Reset autocommit
SET AUTOCOMMIT ON;

-- SAVEPOINT example (partial rollback)
SET AUTOCOMMIT OFF;

SAVEPOINT before_changes;

UPDATE products SET unit_price = unit_price * 1.1 WHERE category_id = 1;
SAVEPOINT after_price_update;

UPDATE products SET units_in_stock = 0 WHERE product_id = 1;

-- Oops, we don't want to zero out stock, rollback to savepoint
ROLLBACK TO SAVEPOINT after_price_update;

-- Price changes kept, stock change undone
SELECT product_id, product_name, unit_price, units_in_stock
FROM products WHERE category_id = 1;

-- Rollback everything
ROLLBACK TO SAVEPOINT before_changes;
COMMIT;

SET AUTOCOMMIT ON;

-- Isolation levels (from least to most strict):
-- 1. READ UNCOMMITTED - can see uncommitted changes (dirty reads)
-- 2. READ COMMITTED - only see committed data
-- 3. REPEATABLE READ - same query returns same results in transaction
-- 4. SERIALIZABLE - transactions execute as if serial

-- Set isolation level (example)
-- SET SESSION CHARACTERISTICS AS TRANSACTION ISOLATION LEVEL READ COMMITTED;

-- Common transaction patterns:
-- 1. All-or-nothing updates across multiple tables
-- 2. Balance transfers (debit one, credit another)
-- 3. Order processing (create order, update inventory, record payment)
-- 4. Audit logging (main operation + audit record)
