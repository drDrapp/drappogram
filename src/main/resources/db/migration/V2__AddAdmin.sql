--  ADMIN       --------------------------------------------------
INSERT INTO dg_user (id, login, password, active, state, email)
VALUES (1, 'z', 'z', TRUE, 'ACTIVE', 'z@z.z');

INSERT INTO dg_user_role (user_id, roles)
VALUES (1, 'ADMIN'),
       (1, 'USER');