CREATE EXTENSION IF NOT EXISTS pgcrypto;
UPDATE dg_user
SET hash_password = crypt(hash_password, gen_salt('bf', 8));