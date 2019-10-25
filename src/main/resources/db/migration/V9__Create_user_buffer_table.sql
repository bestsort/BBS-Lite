create table user_buffer
(
    account_id varchar(20) unique ,
    email varchar(30),
    token varchar(36) default '',
    password varchar(29)
);