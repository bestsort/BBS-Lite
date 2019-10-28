create table comment_kid
(
    id bigint auto_increment primary key ,
    comment_by_id bigint,
    pid bigint,
    gmt_modified bigint,
    gmt_create bigint,
    content varchar(255)
);