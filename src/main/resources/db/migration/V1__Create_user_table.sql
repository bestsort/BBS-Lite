create table user
(
    id int auto_increment primary key not null,
    account_id int,
    name varchar(50) default '',
    token varchar(36) default '',
    gmt_create bigint,
    gmt_modified bigint,
    bio varchar(256) default '',
    avatar_url varchar(100)
);