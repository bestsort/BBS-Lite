create table message(
    id bigint primary key auto_increment,
    type tinyint comment '类型(被收藏/被关注/被回复等)',
    send_by bigint default 0,
    send_to bigint,
    status tinyint default 1 comment '是否可见(为0表示已删除)',
    is_read tinyint default 0,
    gmt_create bigint,
    gmt_modified bigint
);