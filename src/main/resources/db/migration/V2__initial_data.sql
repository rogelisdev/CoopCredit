-- V2: Insert initial test data

-- Insert admin user (password: Admin123!)
-- BCrypt hash for "Admin123!"
INSERT INTO users (username, password, role, affiliate_id)
VALUES ('admin', '$2a$10$8By9rVaM3TUT8LB9KeMvO.6jspfZL95JWyTQ5Yuuk8zP6QDCRHA9m', 'ADMIN', NULL);

-- Insert analyst user (password: Analyst123!)
INSERT INTO users (username, password, role, affiliate_id)
VALUES ('analyst', '$2a$10$VVh1kUfd.4qOSzEAiV.LIe3GomgZOo19LMYqpXVRJOugHkjwlGkhC', 'ANALYST', NULL);

-- Insert sample affiliate
INSERT INTO affiliate (document, first_name, last_name, email, salary, status, registration_date)
VALUES ('123456789', 'John', 'Doe', 'john.doe@example.com', 5000.00, 'ACTIVE', CURRENT_DATE - INTERVAL '6 MONTH');

-- Insert affiliate user linked to the affiliate (password: Affiliate123!)
INSERT INTO users (username, password, role, affiliate_id)
VALUES ('john.doe', '$2a$10$taAcrPTC0QPjl7shbrvvuOSIyi1D/59fP7QagFuI6VCfe7wCSHSve', 'AFILIADO', 1);

-- Insert another sample affiliate for testing
INSERT INTO affiliate (document, first_name, last_name, email, salary, status, registration_date)
VALUES ('987654321', 'Jane', 'Smith', 'jane.smith@example.com', 6000.00, 'ACTIVE', CURRENT_DATE - INTERVAL '12 MONTH');

INSERT INTO users (username, password, role, affiliate_id)
VALUES ('jane.smith', '$2a$10$taAcrPTC0QPjl7shbrvvuOSIyi1D/59fP7QagFuI6VCfe7wCSHSve', 'AFILIADO', 2);
