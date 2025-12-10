-- V1: Initial schema creation
-- Create affiliate table
CREATE TABLE affiliate (
    affiliate_id BIGSERIAL PRIMARY KEY,
    document VARCHAR(20) NOT NULL UNIQUE,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL,
    salary DECIMAL(10,2) NOT NULL CHECK (salary > 0),
    status VARCHAR(20) NOT NULL,
    registration_date DATE NOT NULL,
    CONSTRAINT chk_affiliate_status CHECK (status IN ('ACTIVE', 'INACTIVE', 'SUSPENDED'))
);

-- Create users table
CREATE TABLE users (
    user_id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,
    affiliate_id BIGINT,
    CONSTRAINT chk_user_role CHECK (role IN ('ROLE_AFILIADO', 'ROLE_ANALYST', 'ROLE_ADMIN')),
    CONSTRAINT fk_user_affiliate FOREIGN KEY (affiliate_id) REFERENCES affiliate(affiliate_id) ON DELETE SET NULL
);

-- Create token table
CREATE TABLE token (
    token_id BIGSERIAL PRIMARY KEY,
    token VARCHAR(500) NOT NULL UNIQUE,
    token_type VARCHAR(20) NOT NULL,
    revoked BOOLEAN NOT NULL DEFAULT FALSE,
    expired BOOLEAN NOT NULL DEFAULT FALSE,
    user_id BIGINT NOT NULL,
    CONSTRAINT fk_token_user FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Create risk_evaluation table
CREATE TABLE risk_evaluation (
    risk_evaluation_id BIGSERIAL PRIMARY KEY,
    score INTEGER NOT NULL CHECK (score >= 0 AND score <= 100),
    level VARCHAR(20) NOT NULL,
    recommendations VARCHAR(500),
    evaluated_at TIMESTAMP NOT NULL,
    CONSTRAINT chk_risk_level CHECK (level IN ('LOW', 'MEDIUM', 'HIGH'))
);

-- Create credit_application table
CREATE TABLE credit_application (
    credit_application_id BIGSERIAL PRIMARY KEY,
    amount DECIMAL(12,2) NOT NULL CHECK (amount > 0),
    term INTEGER NOT NULL CHECK (term > 0),
    status VARCHAR(20) NOT NULL,
    requested_date TIMESTAMP NOT NULL,
    evaluated_date TIMESTAMP,
    affiliate_id BIGINT NOT NULL,
    risk_evaluation_id BIGINT,
    CONSTRAINT chk_application_status CHECK (status IN ('PENDING', 'APPROVED', 'REJECTED')),
    CONSTRAINT fk_credit_app_affiliate FOREIGN KEY (affiliate_id) REFERENCES affiliate(affiliate_id) ON DELETE CASCADE,
    CONSTRAINT fk_credit_app_risk FOREIGN KEY (risk_evaluation_id) REFERENCES risk_evaluation(risk_evaluation_id) ON DELETE SET NULL
);

-- Create indexes for performance
CREATE INDEX idx_affiliate_document ON affiliate(document);
CREATE INDEX idx_user_username ON users(username);
CREATE INDEX idx_credit_app_status ON credit_application(status);
CREATE INDEX idx_credit_app_affiliate ON credit_application(affiliate_id);
CREATE INDEX idx_token_user ON token(user_id);
