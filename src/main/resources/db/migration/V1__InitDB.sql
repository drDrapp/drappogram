CREATE SEQUENCE dg_user_id_seq
    START WITH 10;

CREATE SEQUENCE dg_message_id_seq
    START WITH 10;


CREATE TABLE dg_user
(
    id              bigint DEFAULT NEXTVAL('dg_user_id_seq'::regclass) NOT NULL
        PRIMARY KEY,
    activation_code varchar(255),
    active          boolean,
    email           varchar(255)                                       NOT NULL,
    first_name      varchar(255),
    password        varchar(255)                                       NOT NULL,
    last_name       varchar(255),
    login           varchar(255)                                       NOT NULL,
    state           varchar(255)
);

ALTER SEQUENCE dg_user_id_seq OWNED BY dg_user.id;

CREATE TABLE IF NOT EXISTS dg_message
(
    id       bigint DEFAULT NEXTVAL('dg_message_id_seq'::regclass) NOT NULL
        PRIMARY KEY,
    filename varchar(255),
    tag      varchar(255),
    text     varchar(2048)                                         NOT NULL,
    user_id  bigint
        CONSTRAINT dg_message_dg_user_id_fk
            REFERENCES dg_user
            ON UPDATE RESTRICT ON DELETE CASCADE
);

ALTER SEQUENCE dg_message_id_seq OWNED BY dg_message.id;

CREATE TABLE dg_user_role
(
    user_id bigint NOT NULL
        CONSTRAINT dg_user_role_dg_user_id_fk
            REFERENCES dg_user
            ON UPDATE RESTRICT ON DELETE CASCADE,
    roles   varchar(255)
);


CREATE TABLE persistent_logins
(
    username  varchar(64) NOT NULL,
    series    varchar(64) NOT NULL
        PRIMARY KEY,
    token     varchar(64) NOT NULL,
    last_used timestamp   NOT NULL
);