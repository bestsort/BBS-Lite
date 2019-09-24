create table user
(
    id bigint auto_increment primary key not null,
    account_id varchar(20) default null,
    name varchar(50) default 'none',
    token varchar(36) default '',
    bio varchar(256),
    avatar_url varchar(100) default 'https://alicdn.bestsort.cn/avatar.png',
    password varchar(40) default null,
    gmt_create bigint,
    gmt_modified bigint,
    email varchar(40)
)  default charset = utf8;