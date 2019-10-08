create table fans(
    id bigint primary key auto_increment,
    fans_by_id bigint,
    fans_to_id bigint,
    status tinyint default 1,
    gmt_created bigint,
    gmt_modified bigint
);