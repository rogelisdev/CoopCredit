-- V2: Insert initial test data

-- Insert admin user (password: Admin123!)
-- BCrypt hash for "Admin123!"
INSERT INTO users (username, password, role, affiliate_id)
VALUES ('admin', '$2a$10$rBV2cXlHW7VqYNj8vQx3/.K1YqZ5YqZ5YqZ5YqZ5YqZ5YqZ5YqZ5Ye', 'ROLE_ADMIN', NULL);

-- Insert analyst user (password: Analyst123!)
INSERT INTO users (username, password, role, affiliate_id)
VALUES ('analyst', '$2a$10$rBV2cXlHW7VqYNj8vQx3/.K1YqZ5YqZ5YqZ5YqZ5YqZ5YqZ5YqZ5Ye', 'ROLE_ANALYST', NULL);

-- Insert sample affiliate
INSERT INTO affiliate (document, first_name, last_name, email, salary, status, registration_date)
VALUES ('123456789', 'John', 'Doe', 'john.doe@example.com', 5000.00, 'ACTIVE', DATEADD('MONTH', -6, CURRENT_DATE));

-- Insert affiliate user linked to the affiliate (password: Affiliate123!)
INSERT INTO users (username, password, role, affiliate_id)
VALUES ('john.doe', '$2a$10$rBV2cXlHW7VqYNj8vQx3/.K1YqZ5YqZ5YqZ5YqZ5YqZ5YqZ5YqZ5Ye', 'ROLE_AFILIADO', 1);

-- Insert another sample affiliate for testing
INSERT INTO affiliate (document, first_name, last_name, email, salary, status, registration_date)
VALUES ('987654321', 'Jane', 'Smith', 'jane.smith@example.com', 6000.00, 'ACTIVE', DATEADD('MONTH', -12, CURRENT_DATE));

INSERT INTO users (username, password, role, affiliate_id)
VALUES ('jane.smith', '$2a$10$rBV2cXlHW7VqYNj8vQx3/.K1YqZ5YqZ5YqZ5YqZ5YqZ5YqZ5YqZ5Ye', 'ROLE_AFILIADO', 2);
