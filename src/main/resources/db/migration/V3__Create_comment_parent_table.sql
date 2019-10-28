create table comment_parent
(
    id bigint auto_increment primary key ,
    comment_by_id bigint,
    question_id bigint,
    gmt_modified bigint,
    gmt_create bigint,
    content varchar(255),
    like_count bigint default 0
);