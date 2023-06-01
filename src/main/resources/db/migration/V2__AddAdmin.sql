--  ADMIN       --------------------------------------------------
INSERT INTO dg_user (id, login, hash_password, active, state)
VALUES (1, 'z', '$2a$10$dxttCoL8wzUGBzONa8E1tuGL7qO6gE1XN2Kg9ITVBfGRGYZ5LjaQS', TRUE, 'ACTIVE');

INSERT INTO dg_user_role (user_id, roles)
VALUES (1, 'ADMIN'),
       (1, 'USER');