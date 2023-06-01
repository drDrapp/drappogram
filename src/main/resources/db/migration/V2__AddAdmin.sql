--  ADMIN       --------------------------------------------------
INSERT INTO dg_user (id, login, hash_password, active, state)
VALUES (1, 'z', 'z', TRUE, 'ACTIVE');

INSERT INTO dg_user_role (user_id, roles)
VALUES (1, 'ADMIN'),
       (1, 'USER');