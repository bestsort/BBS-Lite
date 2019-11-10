create table oauth_user
(
    id bigint auto_increment primary key not null,
    uuid varchar(50),
    user_id bigint,
    username varchar(20),
    nickname varchar(20),
    avatar varchar(100),
    blog varchar(50),
    remark varchar(256),
    source varchar(20),
    email varchar(30),
    gmt_created bigint,
    gmt_modified bigint
);