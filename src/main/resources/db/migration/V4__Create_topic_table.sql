create table topic(
    id bigint primary key auto_increment,
    name varchar(10) unique ,
    avatar_url varchar(50),
    follow_count bigint default 0,
    question_count bigint default 0
);
insert into topic (name) values ('默认');
insert into topic (name, avatar_url) values ('c++','https://alicdn.bestsort.cn/icon/cplusplus.png');
insert into topic (name, avatar_url) values ('java','https://alicdn.bestsort.cn/icon/java.png');
insert into topic (name, avatar_url) values ('mybatis','https://alicdn.bestsort.cn/icon/mybatis.png');
insert into topic (name, avatar_url) values ('redis','https://alicdn.bestsort.cn/icon/redis.png');
insert into topic (name, avatar_url) values ('mysql','https://alicdn.bestsort.cn/icon/mysql.png');
insert into topic (name, avatar_url) values ('氢论坛','https://alicdn.bestsort.cn/icon/bbs.png');
insert into topic (name, avatar_url) values ('python','https://alicdn.bestsort.cn/icon/python.png');
insert into topic (name, avatar_url) values ('springboot','https://alicdn.bestsort.cn/icon/springboot.png');




