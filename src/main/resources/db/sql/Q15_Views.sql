-- =====================================================
-- Q15: What are views and why use them?
-- =====================================================

-- A VIEW is a virtual table based on the result of a SELECT statement.
-- Views provide abstraction, security, and simplification.

-- View already created in schema.sql: v_employee_details
-- Let's query it
SELECT * FROM v_employee_details;

-- Query view with filtering
SELECT * FROM v_employee_details
WHERE department_name = 'Engineering';

-- Query view with aggregation
SELECT
  department_name,
  COUNT(*) AS employee_count,
  ROUND(AVG(salary), 2) AS avg_salary
FROM v_employee_details
GROUP BY department_name;

-- Use the order summary view
SELECT * FROM v_order_summary
ORDER BY total_amount DESC;

-- Use the product sales view
SELECT * FROM v_product_sales
WHERE total_quantity_sold > 10
ORDER BY total_revenue DESC;

-- Creating a new view
CREATE VIEW v_high_value_customers AS
SELECT
  c.customer_id,
  c.first_name || ' ' || c.last_name AS customer_name,
  c.email,
  c.city,
  COUNT(o.order_id) AS order_count,
  SUM(oi.quantity * oi.unit_price * (1 - oi.discount)) AS total_spent
FROM customers c
JOIN orders o ON c.customer_id = o.customer_id
JOIN order_items oi ON o.order_id = oi.order_id
GROUP BY c.customer_id, c.first_name, c.last_name, c.email, c.city
HAVING SUM(oi.quantity * oi.unit_price * (1 - oi.discount)) > 500;

-- Query the new view
SELECT * FROM v_high_value_customers
ORDER BY total_spent DESC;

-- View for inventory status
CREATE VIEW v_inventory_status AS
SELECT
  p.product_id,
  p.product_name,
  c.category_name,
  p.units_in_stock,
  p.reorder_level,
  p.discontinued,
  CASE
    WHEN p.discontinued THEN 'Discontinued'
    WHEN p.units_in_stock = 0 THEN 'Out of Stock'
    WHEN p.units_in_stock <= p.reorder_level THEN 'Low Stock'
    ELSE 'In Stock'
  END AS stock_status
FROM products p
JOIN categories c ON p.category_id = c.category_id;

SELECT * FROM v_inventory_status
WHERE stock_status IN ('Out of Stock', 'Low Stock');

-- View for department statistics
CREATE VIEW v_department_stats AS
SELECT
  d.department_id,
  d.department_name,
  d.location,
  d.budget,
  COUNT(e.employee_id) AS employee_count,
  COALESCE(SUM(e.salary), 0) AS total_salary,
  ROUND(COALESCE(AVG(e.salary), 0), 2) AS avg_salary,
  d.budget - COALESCE(SUM(e.salary), 0) AS remaining_budget
FROM departments d
LEFT JOIN employees e ON d.department_id = e.department_id AND e.is_active = TRUE
GROUP BY d.department_id, d.department_name, d.location, d.budget;

SELECT * FROM v_department_stats
ORDER BY remaining_budget DESC;

-- Drop a view
-- DROP VIEW IF EXISTS v_high_value_customers;

-- Benefits of views:
-- 1. Simplify complex queries
-- 2. Provide data abstraction
-- 3. Implement row-level security
-- 4. Maintain backward compatibility when schema changes
-- 5. Encapsulate business logic
