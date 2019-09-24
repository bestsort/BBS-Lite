create table user
(
    id bigint auto_increment primary key not null,
    account_id varchar(20),
    password varchar(40),
    email varchar(30),
    name varchar(50) default '',
    token varchar(36) default '',
    gmt_create bigint,
    gmt_modified bigint,
    bio varchar(256) default '',
    avatar_url varchar(100),
    html_url varchar(100)
);