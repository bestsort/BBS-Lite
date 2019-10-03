create table count_num(
    id bigint primary key auto_increment,
    count_type varchar(20),
    total bigint,
    gmt_create bigint,
    gmt_modified bigint
);