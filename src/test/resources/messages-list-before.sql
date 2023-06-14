DELETE
FROM dg_message;

INSERT INTO dg_message(id, text, tag, user_id)
VALUES (1, 'first', 'tag', 1),
       (2, 'second', 'another tag', 1),
       (3, 'third', 'tag', 1),
       (4, 'fourth', 'another text', 2);

ALTER SEQUENCE dg_message_id_seq RESTART WITH 10;