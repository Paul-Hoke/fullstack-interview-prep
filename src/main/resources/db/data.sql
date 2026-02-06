-- =====================================================
-- SQL Interview Prep - Sample Data
-- =====================================================

-- =====================================================
-- DEPARTMENTS Data
-- =====================================================
INSERT INTO departments (department_id, department_name, location, budget) VALUES
(1, 'Engineering', 'San Francisco', 5000000.00),
(2, 'Sales', 'New York', 3000000.00),
(3, 'Marketing', 'Los Angeles', 2000000.00),
(4, 'Human Resources', 'Chicago', 1000000.00),
(5, 'Finance', 'New York', 2500000.00),
(6, 'Research', 'Boston', 4000000.00),
(7, 'Customer Support', 'Austin', 1500000.00),
(8, 'Operations', 'Seattle', 2000000.00);

-- =====================================================
-- EMPLOYEES Data
-- =====================================================
-- CEO (no manager)
INSERT INTO employees (employee_id, first_name, last_name, email, phone, hire_date, job_title, salary, commission_pct, manager_id, department_id) VALUES
(1, 'John', 'Smith', 'john.smith@company.com', '555-0101', '2010-01-15', 'CEO', 250000.00, NULL, NULL, 1);

-- Department Heads
INSERT INTO employees (employee_id, first_name, last_name, email, phone, hire_date, job_title, salary, commission_pct, manager_id, department_id) VALUES
(2, 'Sarah', 'Johnson', 'sarah.johnson@company.com', '555-0102', '2012-03-20', 'VP Engineering', 180000.00, NULL, 1, 1),
(3, 'Michael', 'Williams', 'michael.williams@company.com', '555-0103', '2013-06-10', 'VP Sales', 170000.00, 0.15, 1, 2),
(4, 'Emily', 'Brown', 'emily.brown@company.com', '555-0104', '2014-02-28', 'VP Marketing', 160000.00, NULL, 1, 3),
(5, 'David', 'Jones', 'david.jones@company.com', '555-0105', '2015-08-15', 'VP HR', 150000.00, NULL, 1, 4),
(6, 'Jessica', 'Davis', 'jessica.davis@company.com', '555-0106', '2014-11-30', 'VP Finance', 165000.00, NULL, 1, 5);

-- Senior Engineers
INSERT INTO employees (employee_id, first_name, last_name, email, phone, hire_date, job_title, salary, commission_pct, manager_id, department_id) VALUES
(7, 'Robert', 'Miller', 'robert.miller@company.com', '555-0107', '2016-04-12', 'Senior Engineer', 130000.00, NULL, 2, 1),
(8, 'Jennifer', 'Wilson', 'jennifer.wilson@company.com', '555-0108', '2017-01-25', 'Senior Engineer', 125000.00, NULL, 2, 1),
(9, 'William', 'Moore', 'william.moore@company.com', '555-0109', '2018-07-18', 'Senior Engineer', 120000.00, NULL, 2, 1);

-- Engineers
INSERT INTO employees (employee_id, first_name, last_name, email, phone, hire_date, job_title, salary, commission_pct, manager_id, department_id) VALUES
(10, 'Lisa', 'Taylor', 'lisa.taylor@company.com', '555-0110', '2019-03-05', 'Software Engineer', 95000.00, NULL, 7, 1),
(11, 'James', 'Anderson', 'james.anderson@company.com', '555-0111', '2020-06-15', 'Software Engineer', 90000.00, NULL, 7, 1),
(12, 'Patricia', 'Thomas', 'patricia.thomas@company.com', '555-0112', '2021-02-20', 'Junior Engineer', 75000.00, NULL, 8, 1),
(13, 'Christopher', 'Jackson', 'chris.jackson@company.com', '555-0113', '2022-09-01', 'Junior Engineer', 70000.00, NULL, 8, 1);

-- Sales Team
INSERT INTO employees (employee_id, first_name, last_name, email, phone, hire_date, job_title, salary, commission_pct, manager_id, department_id) VALUES
(14, 'Nancy', 'White', 'nancy.white@company.com', '555-0114', '2017-05-10', 'Sales Manager', 100000.00, 0.12, 3, 2),
(15, 'Daniel', 'Harris', 'daniel.harris@company.com', '555-0115', '2018-08-22', 'Sales Representative', 65000.00, 0.10, 14, 2),
(16, 'Karen', 'Martin', 'karen.martin@company.com', '555-0116', '2019-11-30', 'Sales Representative', 62000.00, 0.10, 14, 2),
(17, 'Steven', 'Garcia', 'steven.garcia@company.com', '555-0117', '2020-04-15', 'Sales Representative', 60000.00, 0.08, 14, 2),
(18, 'Betty', 'Martinez', 'betty.martinez@company.com', '555-0118', '2021-07-20', 'Sales Representative', 58000.00, 0.08, 14, 2);

-- Marketing Team
INSERT INTO employees (employee_id, first_name, last_name, email, phone, hire_date, job_title, salary, commission_pct, manager_id, department_id) VALUES
(19, 'Mark', 'Robinson', 'mark.robinson@company.com', '555-0119', '2018-03-12', 'Marketing Manager', 95000.00, NULL, 4, 3),
(20, 'Sandra', 'Clark', 'sandra.clark@company.com', '555-0120', '2019-09-25', 'Marketing Specialist', 70000.00, NULL, 19, 3),
(21, 'Paul', 'Rodriguez', 'paul.rodriguez@company.com', '555-0121', '2020-12-01', 'Marketing Specialist', 68000.00, NULL, 19, 3);

-- HR Team
INSERT INTO employees (employee_id, first_name, last_name, email, phone, hire_date, job_title, salary, commission_pct, manager_id, department_id) VALUES
(22, 'Margaret', 'Lewis', 'margaret.lewis@company.com', '555-0122', '2017-06-18', 'HR Manager', 85000.00, NULL, 5, 4),
(23, 'Kenneth', 'Lee', 'kenneth.lee@company.com', '555-0123', '2020-02-28', 'HR Specialist', 55000.00, NULL, 22, 4);

-- Finance Team
INSERT INTO employees (employee_id, first_name, last_name, email, phone, hire_date, job_title, salary, commission_pct, manager_id, department_id) VALUES
(24, 'Donna', 'Walker', 'donna.walker@company.com', '555-0124', '2016-10-05', 'Finance Manager', 110000.00, NULL, 6, 5),
(25, 'Edward', 'Hall', 'edward.hall@company.com', '555-0125', '2019-04-15', 'Financial Analyst', 80000.00, NULL, 24, 5),
(26, 'Carol', 'Allen', 'carol.allen@company.com', '555-0126', '2021-01-10', 'Accountant', 65000.00, NULL, 24, 5);

-- Research Team
INSERT INTO employees (employee_id, first_name, last_name, email, phone, hire_date, job_title, salary, commission_pct, manager_id, department_id) VALUES
(27, 'George', 'Young', 'george.young@company.com', '555-0127', '2015-07-22', 'Research Director', 140000.00, NULL, 1, 6),
(28, 'Ruth', 'King', 'ruth.king@company.com', '555-0128', '2018-11-08', 'Senior Researcher', 110000.00, NULL, 27, 6),
(29, 'Frank', 'Wright', 'frank.wright@company.com', '555-0129', '2020-05-30', 'Researcher', 85000.00, NULL, 28, 6);

-- Inactive employee (for IS_ACTIVE demonstrations)
INSERT INTO employees (employee_id, first_name, last_name, email, phone, hire_date, job_title, salary, commission_pct, manager_id, department_id, is_active) VALUES
(30, 'Alice', 'Scott', 'alice.scott@company.com', '555-0130', '2018-03-15', 'Software Engineer', 95000.00, NULL, 7, 1, FALSE);

-- =====================================================
-- CATEGORIES Data
-- =====================================================
INSERT INTO categories (category_id, category_name, description, parent_category_id) VALUES
(1, 'Electronics', 'Electronic devices and accessories', NULL),
(2, 'Computers', 'Desktop and laptop computers', 1),
(3, 'Smartphones', 'Mobile phones and tablets', 1),
(4, 'Accessories', 'Electronic accessories', 1),
(5, 'Clothing', 'Apparel and fashion', NULL),
(6, 'Men''s Clothing', 'Clothing for men', 5),
(7, 'Women''s Clothing', 'Clothing for women', 5),
(8, 'Books', 'Books and publications', NULL),
(9, 'Fiction', 'Fiction books', 8),
(10, 'Non-Fiction', 'Non-fiction books', 8),
(11, 'Home & Garden', 'Home and garden products', NULL),
(12, 'Furniture', 'Home furniture', 11),
(13, 'Kitchen', 'Kitchen appliances and tools', 11);

-- =====================================================
-- SUPPLIERS Data
-- =====================================================
INSERT INTO suppliers (supplier_id, supplier_name, contact_name, email, phone, address, city, country) VALUES
(1, 'Tech Solutions Inc', 'John Doe', 'john@techsolutions.com', '555-1001', '123 Tech Blvd', 'San Jose', 'USA'),
(2, 'Global Electronics', 'Jane Smith', 'jane@globalelec.com', '555-1002', '456 Circuit Ave', 'Shenzhen', 'China'),
(3, 'Fashion Forward', 'Mike Johnson', 'mike@fashionforward.com', '555-1003', '789 Style St', 'Milan', 'Italy'),
(4, 'Book World Publishers', 'Sarah Wilson', 'sarah@bookworld.com', '555-1004', '321 Library Lane', 'New York', 'USA'),
(5, 'Home Essentials', 'Tom Brown', 'tom@homeess.com', '555-1005', '654 Comfort Rd', 'Chicago', 'USA');

-- =====================================================
-- PRODUCTS Data
-- =====================================================
INSERT INTO products (product_id, product_name, category_id, supplier_id, unit_price, units_in_stock, units_on_order, reorder_level, discontinued) VALUES
-- Electronics - Computers
(1, 'Laptop Pro 15', 2, 1, 1299.99, 50, 20, 10, FALSE),
(2, 'Desktop Workstation', 2, 1, 1899.99, 25, 10, 5, FALSE),
(3, 'Gaming Laptop', 2, 2, 1599.99, 30, 15, 8, FALSE),
(4, 'Budget Laptop', 2, 2, 499.99, 100, 50, 20, FALSE),
-- Electronics - Smartphones
(5, 'Smartphone X', 3, 2, 999.99, 200, 100, 50, FALSE),
(6, 'Smartphone Lite', 3, 2, 499.99, 150, 75, 30, FALSE),
(7, 'Tablet Pro', 3, 1, 799.99, 75, 25, 15, FALSE),
-- Electronics - Accessories
(8, 'Wireless Mouse', 4, 1, 49.99, 500, 200, 100, FALSE),
(9, 'Mechanical Keyboard', 4, 1, 129.99, 200, 100, 50, FALSE),
(10, 'USB-C Hub', 4, 2, 79.99, 300, 150, 75, FALSE),
(11, 'Laptop Bag', 4, 3, 59.99, 250, 100, 50, FALSE),
-- Clothing - Men's
(12, 'Men''s Dress Shirt', 6, 3, 49.99, 300, 150, 75, FALSE),
(13, 'Men''s Jeans', 6, 3, 69.99, 200, 100, 50, FALSE),
(14, 'Men''s Jacket', 6, 3, 129.99, 100, 50, 25, FALSE),
-- Clothing - Women's
(15, 'Women''s Blouse', 7, 3, 44.99, 250, 125, 60, FALSE),
(16, 'Women''s Dress', 7, 3, 89.99, 150, 75, 35, FALSE),
(17, 'Women''s Cardigan', 7, 3, 59.99, 180, 90, 45, FALSE),
-- Books
(18, 'The Great Novel', 9, 4, 24.99, 500, 200, 100, FALSE),
(19, 'Mystery Tales', 9, 4, 19.99, 400, 150, 80, FALSE),
(20, 'Business Strategy', 10, 4, 34.99, 300, 100, 50, FALSE),
(21, 'Cooking Mastery', 10, 4, 29.99, 250, 100, 50, FALSE),
-- Home & Garden
(22, 'Office Chair', 12, 5, 299.99, 50, 25, 10, FALSE),
(23, 'Standing Desk', 12, 5, 499.99, 30, 15, 8, FALSE),
(24, 'Coffee Maker', 13, 5, 89.99, 150, 75, 30, FALSE),
(25, 'Blender Pro', 13, 5, 149.99, 100, 50, 25, FALSE),
-- Discontinued products
(26, 'Old Phone Model', 3, 2, 299.99, 10, 0, 5, TRUE),
(27, 'Legacy Laptop', 2, 1, 699.99, 5, 0, 5, TRUE);

-- =====================================================
-- CUSTOMERS Data
-- =====================================================
INSERT INTO customers (customer_id, first_name, last_name, email, phone, address, city, state, country, postal_code, registration_date, credit_limit) VALUES
(1, 'Alice', 'Johnson', 'alice.j@email.com', '555-2001', '123 Main St', 'New York', 'NY', 'USA', '10001', '2020-01-15', 5000.00),
(2, 'Bob', 'Williams', 'bob.w@email.com', '555-2002', '456 Oak Ave', 'Los Angeles', 'CA', 'USA', '90001', '2020-03-22', 7500.00),
(3, 'Charlie', 'Brown', 'charlie.b@email.com', '555-2003', '789 Pine Rd', 'Chicago', 'IL', 'USA', '60601', '2020-06-10', 10000.00),
(4, 'Diana', 'Davis', 'diana.d@email.com', '555-2004', '321 Elm Blvd', 'Houston', 'TX', 'USA', '77001', '2020-09-05', 6000.00),
(5, 'Edward', 'Miller', 'edward.m@email.com', '555-2005', '654 Cedar Ln', 'Phoenix', 'AZ', 'USA', '85001', '2021-01-20', 8000.00),
(6, 'Fiona', 'Wilson', 'fiona.w@email.com', '555-2006', '987 Birch St', 'Philadelphia', 'PA', 'USA', '19101', '2021-04-12', 12000.00),
(7, 'George', 'Moore', 'george.m@email.com', '555-2007', '147 Maple Dr', 'San Antonio', 'TX', 'USA', '78201', '2021-07-30', 4500.00),
(8, 'Hannah', 'Taylor', 'hannah.t@email.com', '555-2008', '258 Walnut Ave', 'San Diego', 'CA', 'USA', '92101', '2021-10-18', 9000.00),
(9, 'Ivan', 'Anderson', 'ivan.a@email.com', '555-2009', '369 Cherry Rd', 'Dallas', 'TX', 'USA', '75201', '2022-02-14', 5500.00),
(10, 'Julia', 'Thomas', 'julia.t@email.com', '555-2010', '741 Spruce Blvd', 'San Jose', 'CA', 'USA', '95101', '2022-05-28', 15000.00),
(11, 'Kevin', 'Jackson', 'kevin.j@email.com', '555-2011', '852 Ash Ln', 'Austin', 'TX', 'USA', '78701', '2022-08-09', 7000.00),
(12, 'Laura', 'White', 'laura.w@email.com', '555-2012', '963 Poplar St', 'Seattle', 'WA', 'USA', '98101', '2022-11-25', 11000.00),
(13, 'Michael', 'Harris', 'michael.h@email.com', '555-2013', '159 Willow Dr', 'Denver', 'CO', 'USA', '80201', '2023-01-05', 6500.00),
(14, 'Nina', 'Martin', 'nina.m@email.com', '555-2014', '357 Hickory Ave', 'Boston', 'MA', 'USA', '02101', '2023-03-17', 8500.00),
(15, 'Oscar', 'Garcia', 'oscar.g@email.com', '555-2015', '468 Beech Rd', 'Miami', 'FL', 'USA', '33101', '2023-06-22', 4000.00),
-- International customers
(16, 'Pierre', 'Dubois', 'pierre.d@email.com', '33-1-2345', '12 Rue de Paris', 'Paris', NULL, 'France', '75001', '2022-04-10', 10000.00),
(17, 'Yuki', 'Tanaka', 'yuki.t@email.com', '81-3-5678', '5-1 Shibuya', 'Tokyo', NULL, 'Japan', '150-0002', '2022-07-15', 12000.00),
(18, 'Hans', 'Mueller', 'hans.m@email.com', '49-30-9876', '45 Berliner Str', 'Berlin', NULL, 'Germany', '10115', '2023-02-28', 9500.00);

-- =====================================================
-- ORDERS Data
-- =====================================================
INSERT INTO orders (order_id, customer_id, employee_id, order_date, required_date, shipped_date, ship_address, ship_city, ship_country, status) VALUES
-- 2023 Orders
(1, 1, 15, '2023-01-10', '2023-01-17', '2023-01-12', '123 Main St', 'New York', 'USA', 'DELIVERED'),
(2, 2, 16, '2023-01-15', '2023-01-22', '2023-01-18', '456 Oak Ave', 'Los Angeles', 'USA', 'DELIVERED'),
(3, 3, 15, '2023-02-05', '2023-02-12', '2023-02-08', '789 Pine Rd', 'Chicago', 'USA', 'DELIVERED'),
(4, 1, 17, '2023-02-20', '2023-02-27', '2023-02-23', '123 Main St', 'New York', 'USA', 'DELIVERED'),
(5, 4, 15, '2023-03-10', '2023-03-17', '2023-03-14', '321 Elm Blvd', 'Houston', 'USA', 'DELIVERED'),
(6, 5, 18, '2023-03-25', '2023-04-01', '2023-03-28', '654 Cedar Ln', 'Phoenix', 'USA', 'DELIVERED'),
(7, 6, 16, '2023-04-12', '2023-04-19', '2023-04-15', '987 Birch St', 'Philadelphia', 'USA', 'DELIVERED'),
(8, 2, 15, '2023-04-28', '2023-05-05', '2023-05-02', '456 Oak Ave', 'Los Angeles', 'USA', 'DELIVERED'),
(9, 7, 17, '2023-05-15', '2023-05-22', '2023-05-18', '147 Maple Dr', 'San Antonio', 'USA', 'DELIVERED'),
(10, 8, 18, '2023-06-01', '2023-06-08', '2023-06-04', '258 Walnut Ave', 'San Diego', 'USA', 'DELIVERED'),
(11, 3, 15, '2023-06-20', '2023-06-27', '2023-06-23', '789 Pine Rd', 'Chicago', 'USA', 'DELIVERED'),
(12, 9, 16, '2023-07-08', '2023-07-15', '2023-07-11', '369 Cherry Rd', 'Dallas', 'USA', 'DELIVERED'),
(13, 10, 15, '2023-07-25', '2023-08-01', '2023-07-28', '741 Spruce Blvd', 'San Jose', 'USA', 'DELIVERED'),
(14, 1, 17, '2023-08-10', '2023-08-17', '2023-08-13', '123 Main St', 'New York', 'USA', 'DELIVERED'),
(15, 11, 18, '2023-08-28', '2023-09-04', '2023-09-01', '852 Ash Ln', 'Austin', 'USA', 'DELIVERED'),
(16, 12, 15, '2023-09-15', '2023-09-22', '2023-09-18', '963 Poplar St', 'Seattle', 'USA', 'DELIVERED'),
(17, 6, 16, '2023-10-02', '2023-10-09', '2023-10-05', '987 Birch St', 'Philadelphia', 'USA', 'DELIVERED'),
(18, 13, 17, '2023-10-20', '2023-10-27', '2023-10-23', '159 Willow Dr', 'Denver', 'USA', 'DELIVERED'),
(19, 14, 15, '2023-11-05', '2023-11-12', '2023-11-08', '357 Hickory Ave', 'Boston', 'USA', 'DELIVERED'),
(20, 2, 18, '2023-11-22', '2023-11-29', '2023-11-25', '456 Oak Ave', 'Los Angeles', 'USA', 'DELIVERED'),
-- 2024 Orders
(21, 15, 15, '2024-01-08', '2024-01-15', '2024-01-11', '468 Beech Rd', 'Miami', 'USA', 'DELIVERED'),
(22, 16, 16, '2024-01-20', '2024-01-27', '2024-01-24', '12 Rue de Paris', 'Paris', 'France', 'DELIVERED'),
(23, 1, 17, '2024-02-05', '2024-02-12', '2024-02-08', '123 Main St', 'New York', 'USA', 'DELIVERED'),
(24, 17, 15, '2024-02-18', '2024-02-25', '2024-02-21', '5-1 Shibuya', 'Tokyo', 'Japan', 'DELIVERED'),
(25, 3, 18, '2024-03-01', '2024-03-08', '2024-03-04', '789 Pine Rd', 'Chicago', 'USA', 'DELIVERED'),
(26, 18, 16, '2024-03-15', '2024-03-22', '2024-03-19', '45 Berliner Str', 'Berlin', 'Germany', 'DELIVERED'),
(27, 10, 15, '2024-04-02', '2024-04-09', '2024-04-05', '741 Spruce Blvd', 'San Jose', 'USA', 'DELIVERED'),
(28, 5, 17, '2024-04-18', '2024-04-25', '2024-04-22', '654 Cedar Ln', 'Phoenix', 'USA', 'DELIVERED'),
(29, 8, 18, '2024-05-05', '2024-05-12', '2024-05-08', '258 Walnut Ave', 'San Diego', 'USA', 'DELIVERED'),
(30, 12, 15, '2024-05-20', '2024-05-27', '2024-05-23', '963 Poplar St', 'Seattle', 'USA', 'DELIVERED'),
-- Pending/Processing orders
(31, 4, 16, '2024-06-01', '2024-06-08', NULL, '321 Elm Blvd', 'Houston', 'USA', 'PROCESSING'),
(32, 7, 17, '2024-06-05', '2024-06-12', NULL, '147 Maple Dr', 'San Antonio', 'USA', 'PENDING'),
(33, 11, 18, '2024-06-08', '2024-06-15', NULL, '852 Ash Ln', 'Austin', 'USA', 'PENDING');

-- =====================================================
-- ORDER_ITEMS Data
-- =====================================================
INSERT INTO order_items (order_id, product_id, quantity, unit_price, discount) VALUES
-- Order 1
(1, 1, 1, 1299.99, 0.00),
(1, 8, 2, 49.99, 0.00),
-- Order 2
(2, 5, 1, 999.99, 0.05),
(2, 10, 1, 79.99, 0.00),
-- Order 3
(3, 22, 1, 299.99, 0.00),
(3, 23, 1, 499.99, 0.10),
-- Order 4
(4, 18, 2, 24.99, 0.00),
(4, 20, 1, 34.99, 0.00),
-- Order 5
(5, 3, 1, 1599.99, 0.05),
(5, 9, 1, 129.99, 0.00),
(5, 8, 1, 49.99, 0.00),
-- Order 6
(6, 12, 3, 49.99, 0.00),
(6, 13, 2, 69.99, 0.00),
-- Order 7
(7, 15, 2, 44.99, 0.00),
(7, 16, 1, 89.99, 0.10),
(7, 17, 1, 59.99, 0.00),
-- Order 8
(8, 7, 1, 799.99, 0.00),
-- Order 9
(9, 24, 1, 89.99, 0.00),
(9, 25, 1, 149.99, 0.05),
-- Order 10
(10, 4, 2, 499.99, 0.00),
(10, 11, 2, 59.99, 0.00),
-- Order 11
(11, 2, 1, 1899.99, 0.10),
(11, 9, 2, 129.99, 0.00),
-- Order 12
(12, 6, 2, 499.99, 0.00),
-- Order 13
(13, 1, 1, 1299.99, 0.05),
(13, 22, 1, 299.99, 0.00),
-- Order 14
(14, 19, 3, 19.99, 0.00),
(14, 21, 2, 29.99, 0.00),
-- Order 15
(15, 14, 1, 129.99, 0.00),
(15, 12, 2, 49.99, 0.00),
-- Order 16
(16, 5, 1, 999.99, 0.08),
(16, 10, 2, 79.99, 0.00),
-- Order 17
(17, 23, 1, 499.99, 0.00),
-- Order 18
(18, 3, 1, 1599.99, 0.00),
(18, 8, 3, 49.99, 0.00),
-- Order 19
(19, 16, 2, 89.99, 0.05),
(19, 15, 3, 44.99, 0.00),
-- Order 20
(20, 7, 1, 799.99, 0.10),
(20, 6, 1, 499.99, 0.00),
-- Order 21
(21, 24, 2, 89.99, 0.00),
(21, 25, 1, 149.99, 0.00),
-- Order 22
(22, 1, 1, 1299.99, 0.00),
(22, 9, 1, 129.99, 0.00),
-- Order 23
(23, 5, 2, 999.99, 0.05),
-- Order 24
(24, 2, 1, 1899.99, 0.10),
(24, 8, 5, 49.99, 0.00),
-- Order 25
(25, 12, 4, 49.99, 0.00),
(25, 14, 2, 129.99, 0.05),
-- Order 26
(26, 3, 1, 1599.99, 0.00),
-- Order 27
(27, 22, 2, 299.99, 0.00),
(27, 23, 1, 499.99, 0.00),
-- Order 28
(28, 18, 5, 24.99, 0.00),
(28, 19, 3, 19.99, 0.00),
(28, 20, 2, 34.99, 0.00),
-- Order 29
(29, 6, 1, 499.99, 0.00),
(29, 10, 1, 79.99, 0.00),
-- Order 30
(30, 4, 1, 499.99, 0.05),
(30, 11, 1, 59.99, 0.00),
-- Order 31 (Processing)
(31, 1, 1, 1299.99, 0.00),
-- Order 32 (Pending)
(32, 5, 1, 999.99, 0.00),
(32, 8, 2, 49.99, 0.00),
-- Order 33 (Pending)
(33, 22, 1, 299.99, 0.00);

-- =====================================================
-- INVENTORY Data (Stock movement history)
-- =====================================================
INSERT INTO inventory (product_id, quantity_change, change_type, change_date, notes) VALUES
(1, 100, 'INITIAL', '2023-01-01 09:00:00', 'Initial stock'),
(1, -5, 'SALE', '2023-01-15 14:30:00', 'Order fulfillment'),
(1, 20, 'RESTOCK', '2023-02-01 10:00:00', 'Monthly restock'),
(5, 300, 'INITIAL', '2023-01-01 09:00:00', 'Initial stock'),
(5, -25, 'SALE', '2023-01-20 11:45:00', 'Order fulfillment'),
(5, -30, 'SALE', '2023-02-15 16:20:00', 'Order fulfillment'),
(5, 100, 'RESTOCK', '2023-03-01 09:30:00', 'Quarterly restock');
