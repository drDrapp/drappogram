CREATE EXTENSION IF NOT EXISTS pgcrypto;
UPDATE dg_user
SET password = crypt(password, gen_salt('bf', 8));