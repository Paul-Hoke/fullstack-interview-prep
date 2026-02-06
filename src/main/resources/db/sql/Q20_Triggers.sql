-- =====================================================
-- Q20: What are triggers?
-- =====================================================

-- A TRIGGER is a set of actions that automatically execute
-- when a specified event occurs (INSERT, UPDATE, DELETE).

-- H2 supports triggers through Java classes or simple SQL.

-- Create an audit trigger for employees table
CREATE TRIGGER IF NOT EXISTS trg_employee_audit
AFTER INSERT, UPDATE, DELETE ON employees
FOR EACH ROW
CALL "org.h2.api.Trigger";

-- Note: In H2, complex triggers need Java implementation.
-- For demonstration, we'll show the concept with simpler approaches.

-- Alternative: Use a view with INSTEAD OF trigger concept
-- Or handle audit in application layer

-- Here's how triggers work conceptually (pseudo-code for other DBs):

/*
-- MySQL trigger example:
DELIMITER //
CREATE TRIGGER trg_employee_salary_audit
AFTER UPDATE ON employees
FOR EACH ROW
BEGIN
    IF OLD.salary <> NEW.salary THEN
        INSERT INTO audit_log (table_name, operation, record_id, old_values, new_values, changed_at)
        VALUES ('employees', 'SALARY_CHANGE', NEW.employee_id,
                CONCAT('salary:', OLD.salary),
                CONCAT('salary:', NEW.salary),
                NOW());
    END IF;
END //
DELIMITER ;

-- PostgreSQL trigger example:
CREATE OR REPLACE FUNCTION audit_employee_changes()
RETURNS TRIGGER AS $$
BEGIN
    IF TG_OP = 'INSERT' THEN
        INSERT INTO audit_log (table_name, operation, record_id, new_values)
        VALUES ('employees', 'INSERT', NEW.employee_id, row_to_json(NEW)::text);
    ELSIF TG_OP = 'UPDATE' THEN
        INSERT INTO audit_log (table_name, operation, record_id, old_values, new_values)
        VALUES ('employees', 'UPDATE', NEW.employee_id,
                row_to_json(OLD)::text, row_to_json(NEW)::text);
    ELSIF TG_OP = 'DELETE' THEN
        INSERT INTO audit_log (table_name, operation, record_id, old_values)
        VALUES ('employees', 'DELETE', OLD.employee_id, row_to_json(OLD)::text);
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER employee_audit_trigger
AFTER INSERT OR UPDATE OR DELETE ON employees
FOR EACH ROW EXECUTE FUNCTION audit_employee_changes();
*/

-- Simulate trigger behavior manually (for H2)
-- Before updating salary, insert audit record
INSERT INTO audit_log (table_name, operation, record_id, old_values, new_values, changed_by)
SELECT 'employees', 'UPDATE', employee_id,
       'salary:' || salary, 'salary:' || (salary * 1.05),
       'system'
FROM employees WHERE employee_id = 1;

-- Now update
UPDATE employees SET salary = salary * 1.05 WHERE employee_id = 1;

-- View the audit log
SELECT * FROM audit_log ORDER BY changed_at DESC LIMIT 5;

-- Restore original salary
UPDATE employees SET salary = salary / 1.05 WHERE employee_id = 1;

-- Common trigger use cases:
-- 1. Audit logging (track changes)
-- 2. Data validation (beyond constraints)
-- 3. Derived column updates
-- 4. Cascading updates
-- 5. Maintaining summary tables
-- 6. Enforcing business rules

-- Trigger timing:
-- BEFORE - can modify data before it's written
-- AFTER - runs after the change is made
-- INSTEAD OF - replaces the operation (used with views)
