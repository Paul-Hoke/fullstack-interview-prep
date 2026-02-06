-- =====================================================
-- Q30: What are prepared statements and why use them?
-- =====================================================

-- A PREPARED STATEMENT is a precompiled SQL template that can be
-- executed multiple times with different parameters.

-- =====================================================
-- Benefits of Prepared Statements
-- =====================================================

-- 1. SECURITY: Prevents SQL injection attacks
-- 2. PERFORMANCE: Query is parsed/compiled once, executed many times
-- 3. MAINTAINABILITY: Cleaner code with parameterized queries

-- =====================================================
-- SQL Injection Vulnerability (BAD!)
-- =====================================================

-- Vulnerable code (pseudo-code):
/*
String userId = request.getParameter("id");  // User input: "1 OR 1=1"
String sql = "SELECT * FROM employees WHERE employee_id = " + userId;
// Results in: SELECT * FROM employees WHERE employee_id = 1 OR 1=1
// This returns ALL employees!

Even worse input: "1; DROP TABLE employees;--"
// Could delete the table!
*/

-- =====================================================
-- Safe Prepared Statement (GOOD!)
-- =====================================================

-- In H2, we can demonstrate with PREPARE and EXECUTE:

-- Prepare a statement with parameters
PREPARE get_employee FROM 'SELECT * FROM employees WHERE employee_id = ?';

-- Execute with safe parameter binding
EXECUTE get_employee(1);
EXECUTE get_employee(5);

-- Even malicious input is safely handled as data, not code
-- EXECUTE get_employee('1 OR 1=1');  -- Treated as literal string, fails type check

-- Deallocate when done
DEALLOCATE get_employee;

-- =====================================================
-- Java Example (Reference)
-- =====================================================

/*
// UNSAFE - SQL Injection vulnerable
String sql = "SELECT * FROM employees WHERE employee_id = " + userInput;
Statement stmt = connection.createStatement();
ResultSet rs = stmt.executeQuery(sql);

// SAFE - Prepared Statement
String sql = "SELECT * FROM employees WHERE employee_id = ?";
PreparedStatement pstmt = connection.prepareStatement(sql);
pstmt.setInt(1, Integer.parseInt(userInput));  // Parameter binding
ResultSet rs = pstmt.executeQuery();
*/

-- =====================================================
-- Performance Benefits
-- =====================================================

-- Without prepared statement: Parse -> Plan -> Execute (each time)
-- With prepared statement: Parse -> Plan (once) -> Execute (reuse)

-- Prepare a complex query once
PREPARE order_summary FROM '
  SELECT
    o.order_id,
    c.first_name || '' '' || c.last_name AS customer,
    SUM(oi.quantity * oi.unit_price) AS total
  FROM orders o
  JOIN customers c ON o.customer_id = c.customer_id
  JOIN order_items oi ON o.order_id = oi.order_id
  WHERE o.customer_id = ?
  GROUP BY o.order_id, c.first_name, c.last_name
';

-- Execute multiple times efficiently
EXECUTE order_summary(1);
EXECUTE order_summary(2);
EXECUTE order_summary(3);

DEALLOCATE order_summary;

-- =====================================================
-- Prepared Statement with Multiple Parameters
-- =====================================================

PREPARE emp_search FROM '
  SELECT first_name, last_name, salary
  FROM employees
  WHERE department_id = ?
    AND salary BETWEEN ? AND ?
  ORDER BY salary DESC
';

EXECUTE emp_search(1, 50000, 80000);
EXECUTE emp_search(2, 60000, 90000);

DEALLOCATE emp_search;

-- =====================================================
-- Batch Operations with Prepared Statements
-- =====================================================

-- Prepared statements are ideal for batch inserts
/*
PreparedStatement pstmt = conn.prepareStatement(
    "INSERT INTO audit_log (table_name, operation, record_id) VALUES (?, ?, ?)"
);

for (LogEntry entry : entries) {
    pstmt.setString(1, entry.getTable());
    pstmt.setString(2, entry.getOperation());
    pstmt.setInt(3, entry.getRecordId());
    pstmt.addBatch();
}
pstmt.executeBatch();  // Execute all at once
*/

-- =====================================================
-- Best Practices
-- =====================================================

-- 1. ALWAYS use prepared statements for user input
-- 2. Use parameter binding, never string concatenation
-- 3. Reuse prepared statements for repeated queries
-- 4. Close/deallocate when finished
-- 5. Use batch operations for bulk inserts/updates

-- =====================================================
-- Summary
-- =====================================================

-- Prepared statements protect against SQL injection
-- Example attack vectors prevented:
-- ' OR '1'='1
-- '; DROP TABLE users; --
-- ' UNION SELECT password FROM users --

-- These are all treated as literal data, not SQL code,
-- when using prepared statements with parameter binding.
