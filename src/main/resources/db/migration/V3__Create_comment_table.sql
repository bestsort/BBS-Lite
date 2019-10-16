create table comment
(
    id bigint auto_increment primary key ,
    pid bigint comment '父类评论id',
    question_id bigint,
    comment_to bigint,
    comment_by bigint,
    commentator bigint comment '评论人id',
    gmt_modified bigint,
    gmt_create bigint,
    content varchar(255),
    like_count bigint default 0
);