create table user
(
    id int auto_increment primary key not null,
    account_id int,
    name varchar(50),
    token varchar(36),
    gmt_create bigint,
    gmt_modified bigint,
    bio varchar(256),
    avatar_url varchar(100)
);