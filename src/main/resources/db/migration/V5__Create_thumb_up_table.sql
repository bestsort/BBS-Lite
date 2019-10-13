create table thumb_up(
    id bigint primary key auto_increment,
    thumb_up_to bigint,
    thumb_up_by bigint,
    type tinyint,
    status tinyint default 1,
    gmt_create bigint,
    gmt_modified bigint
)