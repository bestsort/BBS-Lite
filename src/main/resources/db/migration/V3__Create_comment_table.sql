create table comment
(
    id BIGINT auto_increment primary key ,
    pid BIGINT null comment '父类评论id',
    type tinyint null comment '父类类型',
    commentator BIGINT null comment '评论人id',
    like_count BIGINT default 0 null,
    gmt_modified BIGINT null,
    gmt_create BIGINT null,
    content VARCHAR(255)
);