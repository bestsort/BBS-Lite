create table question
(
    id BIGINT auto_increment primary key,
    title varchar(50),
    description text,
    gmt_create bigint,
    gmt_modified bigint,
    creator BIGINT,
    comment_count int default 0,
    view_count int default 0,
    like_count int default 0,
    tag varchar(256),
    top tinyint default 0,
    topic varchar(10),
    category varchar(10)
);