create table question_count(
    id bigint primary key auto_increment,
    question_id bigint unique ,
    view bigint default 0,
    follow bigint default 0,
    thumb_up bigint default 0,
    comment bigint default 0,
    gmt_create bigint,
    gmt_modified bigint
);