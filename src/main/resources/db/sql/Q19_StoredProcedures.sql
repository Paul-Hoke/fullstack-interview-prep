-- =====================================================
-- Q19: What are stored procedures and functions?
-- =====================================================

-- H2 uses a slightly different syntax for procedures and functions.
-- H2 supports "aliases" which can reference Java methods,
-- and simple procedures.

-- Create a simple function to calculate order total
CREATE ALIAS IF NOT EXISTS CALCULATE_ORDER_TOTAL AS $$
double calculateOrderTotal(java.sql.Connection conn, int orderId) throws Exception {
    java.sql.PreparedStatement ps = conn.prepareStatement(
        "SELECT SUM(quantity * unit_price * (1 - discount)) FROM order_items WHERE order_id = ?");
    ps.setInt(1, orderId);
    java.sql.ResultSet rs = ps.executeQuery();
    if (rs.next()) {
        return rs.getDouble(1);
    }
    return 0.0;
}
$$;

-- Use the function
SELECT order_id, CALCULATE_ORDER_TOTAL(order_id) AS total
FROM orders
LIMIT 5;

-- Create a function to get employee count by department
CREATE ALIAS IF NOT EXISTS GET_DEPT_EMP_COUNT AS $$
int getDeptEmpCount(java.sql.Connection conn, int deptId) throws Exception {
    java.sql.PreparedStatement ps = conn.prepareStatement(
        "SELECT COUNT(*) FROM employees WHERE department_id = ?");
    ps.setInt(1, deptId);
    java.sql.ResultSet rs = ps.executeQuery();
    if (rs.next()) {
        return rs.getInt(1);
    }
    return 0;
}
$$;

-- Use the function
SELECT department_id, department_name, GET_DEPT_EMP_COUNT(department_id) AS emp_count
FROM departments;

-- In other databases (MySQL/PostgreSQL style - for reference):
/*
-- MySQL Stored Procedure example:
DELIMITER //
CREATE PROCEDURE GetEmployeesByDept(IN dept_id INT)
BEGIN
    SELECT first_name, last_name, salary
    FROM employees
    WHERE department_id = dept_id
    ORDER BY salary DESC;
END //
DELIMITER ;

-- Call it: CALL GetEmployeesByDept(1);

-- PostgreSQL Function example:
CREATE OR REPLACE FUNCTION get_salary_stats(dept_id INTEGER)
RETURNS TABLE(min_sal NUMERIC, max_sal NUMERIC, avg_sal NUMERIC) AS $$
BEGIN
    RETURN QUERY
    SELECT MIN(salary), MAX(salary), ROUND(AVG(salary), 2)
    FROM employees
    WHERE department_id = dept_id;
END;
$$ LANGUAGE plpgsql;

-- Call it: SELECT * FROM get_salary_stats(1);
*/

-- Benefits of stored procedures:
-- 1. Encapsulate business logic in database
-- 2. Reduce network traffic (one call vs multiple)
-- 3. Improved security (grant execute without table access)
-- 4. Code reuse across applications
-- 5. Easier maintenance (change once, affects all callers)

-- Using subqueries as inline "functions" (standard SQL alternative)
SELECT
  order_id,
  (SELECT SUM(quantity * unit_price * (1 - discount))
   FROM order_items oi
   WHERE oi.order_id = o.order_id) AS order_total
FROM orders o
LIMIT 5;

-- Drop an alias
-- DROP ALIAS IF EXISTS CALCULATE_ORDER_TOTAL;
