create table user
(
    id bigint auto_increment primary key not null,
    account_id varchar(20) unique ,
    email varchar(30),
    name varchar(20),
    token varchar(36),
    bio varchar(256),
    fans_count bigint default 0,
    avatar_url varchar(100),
    password varchar(20) default null,
    html_url varchar(50),
    gmt_create bigint,
    gmt_modified bigint
);