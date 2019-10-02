create table comment
(
    id BIGINT auto_increment primary key ,
    pid BIGINT null comment '父类评论id',
    user_avatar_url varchar(100) default 'https://alicdn.bestsort.cn/icon/default.jpeg',
    question_id BIGINT,
    commentator BIGINT null comment '评论人id',
    like_count BIGINT default 0 ,
    level TINYINT default 1,
    gmt_modified BIGINT null,
    gmt_create BIGINT null,
    content VARCHAR(255)
);