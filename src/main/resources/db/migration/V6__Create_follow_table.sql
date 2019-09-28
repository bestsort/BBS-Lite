create table follow(
    id bigint auto_increment primary key ,
    follow_by bigint,
    follow_to bigint,
    type tinyint,
    gmt_create bigint,
    gmt_modified bigint,
    status tinyint default 1
);