create table comment
(
    id bigint auto_increment primary key ,
    comment_to bigint,
    comment_by bigint,
    pid bigint,
    gmt_modified bigint,
    gmt_create bigint,
    content varchar(255),
    like_count bigint default 0
);