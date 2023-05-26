CREATE SEQUENCE hibernate_sequence START 1 INCREMENT 1;

CREATE TABLE persistent_logins
(
    username  VARCHAR(64) NOT NULL,
    series    VARCHAR(64) NOT NULL
        PRIMARY KEY,
    token     VARCHAR(64) NOT NULL,
    last_used TIMESTAMP   NOT NULL
);

ALTER TABLE persistent_logins
    OWNER TO postgres;

CREATE TABLE dg_user
(
    id              BIGINT       NOT NULL
        PRIMARY KEY,
    activation_code VARCHAR(255),
    active          BOOLEAN      NOT NULL,
    email           VARCHAR(255),
    first_name      VARCHAR(255),
    hash_password   VARCHAR(255) NOT NULL,
    last_name       VARCHAR(255),
    login           VARCHAR(255) NOT NULL,
    state           VARCHAR(255)
);

ALTER TABLE dg_user
    OWNER TO postgres;

CREATE TABLE dg_message
(
    id       BIGINT NOT NULL
        PRIMARY KEY,
    filename VARCHAR(255),
    tag      VARCHAR(255),
    text     VARCHAR(255) NOT NULL,
    user_id  BIGINT
        CONSTRAINT message__user_fk
            REFERENCES dg_user
            ON UPDATE RESTRICT ON DELETE CASCADE
);

ALTER TABLE dg_message
    OWNER TO postgres;

CREATE TABLE dg_user_role
(
    user_id BIGINT NOT NULL
        CONSTRAINT user_role__user_fk
            REFERENCES dg_user
            ON UPDATE RESTRICT ON DELETE CASCADE,
    roles   VARCHAR(255)
);

ALTER TABLE dg_user_role
    OWNER TO postgres;