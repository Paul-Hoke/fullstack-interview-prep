-- =====================================================
-- Q27: What are deadlocks and how do you prevent them?
-- =====================================================

-- A DEADLOCK occurs when two or more transactions permanently block
-- each other by each holding locks the other needs.

-- =====================================================
-- Understanding Locks
-- =====================================================

-- Databases use locks to manage concurrent access:
-- - Shared Lock (S): Multiple readers, no writers
-- - Exclusive Lock (X): One writer, no other readers/writers

-- H2 lock information
SELECT * FROM INFORMATION_SCHEMA.LOCKS;

-- =====================================================
-- Deadlock Scenario (conceptual)
-- =====================================================

/*
Transaction 1:
  BEGIN;
  UPDATE accounts SET balance = balance - 100 WHERE id = 1;  -- Locks row 1
  -- Waits for lock on row 2
  UPDATE accounts SET balance = balance + 100 WHERE id = 2;  -- BLOCKED!
  COMMIT;

Transaction 2:
  BEGIN;
  UPDATE accounts SET balance = balance - 50 WHERE id = 2;   -- Locks row 2
  -- Waits for lock on row 1
  UPDATE accounts SET balance = balance + 50 WHERE id = 1;   -- BLOCKED!
  COMMIT;

Both transactions wait for each other = DEADLOCK!
*/

-- =====================================================
-- Preventing Deadlocks
-- =====================================================

-- 1. Always access tables/rows in the same order
-- Good: All transactions lock accounts in ID order

SET AUTOCOMMIT OFF;

-- Transaction always locks lower ID first
UPDATE employees SET salary = salary + 100 WHERE employee_id = 1;
UPDATE employees SET salary = salary - 100 WHERE employee_id = 2;
COMMIT;

SET AUTOCOMMIT ON;

-- 2. Keep transactions short
-- Long transactions = more lock holding time

-- 3. Use appropriate isolation level
-- Lower isolation = fewer locks

-- 4. Use SELECT FOR UPDATE carefully
SET AUTOCOMMIT OFF;

SELECT * FROM employees
WHERE employee_id = 1
FOR UPDATE;  -- Acquires exclusive lock

-- Do some processing...

UPDATE employees SET salary = salary * 1.01 WHERE employee_id = 1;
COMMIT;

SET AUTOCOMMIT ON;

-- 5. Set lock timeout
SET LOCK_TIMEOUT 5000;  -- 5 seconds (H2 specific)

-- =====================================================
-- Lock Types and Their Use
-- =====================================================

-- Row-level locks (most common in modern DBs)
-- Fine-grained, allows concurrent access to different rows

-- Table-level locks
-- Coarse-grained, simpler but less concurrent

-- In H2, use explicit table locking for maintenance:
-- LOCK TABLE employees WRITE;  -- Exclusive lock
-- ... maintenance operations ...
-- UNLOCK TABLES;

-- =====================================================
-- Detecting Deadlocks
-- =====================================================

-- Databases automatically detect deadlocks and abort one transaction
-- The aborted transaction can be retried

-- Example retry logic (pseudo-code):
/*
int retries = 3;
while (retries > 0) {
    try {
        // Execute transaction
        connection.commit();
        break;  // Success, exit loop
    } catch (DeadlockException e) {
        connection.rollback();
        retries--;
        Thread.sleep(random(100, 500));  // Wait before retry
    }
}
*/

-- =====================================================
-- Best Practices
-- =====================================================

-- 1. Access objects in consistent order (alphabetical, by ID)
-- 2. Minimize transaction duration
-- 3. Avoid user interaction during transactions
-- 4. Use row-level locking when possible
-- 5. Use optimistic locking for low-contention scenarios
-- 6. Index columns used in WHERE clauses (reduces lock scope)
-- 7. Batch large updates

-- Example: Batch update instead of long transaction
-- Instead of one big update:
-- UPDATE products SET unit_price = unit_price * 1.1;

-- Use batches:
UPDATE products SET unit_price = unit_price * 1.1 WHERE product_id BETWEEN 1 AND 10;
UPDATE products SET unit_price = unit_price * 1.1 WHERE product_id BETWEEN 11 AND 20;
-- etc.

-- Restore prices
UPDATE products SET unit_price = unit_price / 1.1;
