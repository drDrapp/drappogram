DELETE
FROM dg_user_role;
DELETE
FROM dg_user;


INSERT INTO dg_user (id, login, password, active, state, email)
VALUES (1, 'z', '$2a$08$qzBsj1k2lNDq2TjD.0SjWOtWNL1VmEjJ4Pa00axKI/kVWivR1Gdmu', TRUE, 'ACTIVE', 'z@z.z'),
       (2, 'x', '$2a$08$fNUHI3FnO3cbT6VAcClJOOsIq93f2101ud2RAKiZFAh7Y2h.oFRzC', TRUE, 'ACTIVE', 'x@x.ru');

INSERT INTO dg_user_role(user_id, roles)
VALUES (1, 'ADMIN'),
       (1, 'USER'),
       (2, 'USER');