CREATE TABLE app_user (
    net_id varchar(255) NOT NULL,
    hashed_password varchar(255) NOT NULL,
    CONSTRAINT app_user_pk PRIMARY KEY (net_id)
);

