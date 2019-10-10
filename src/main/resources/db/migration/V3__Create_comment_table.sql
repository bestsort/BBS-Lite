create table comment
(
    id BIGINT auto_increment primary key ,
    pid BIGINT null comment '父类评论id',
    question_id BIGINT,
    commentator BIGINT null comment '评论人id',
    level TINYINT default 1,
    gmt_modified BIGINT null,
    gmt_create BIGINT null,
    content VARCHAR(255),
    like_count bigint default 0
);