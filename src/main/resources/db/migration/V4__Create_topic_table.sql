create table topic(
    id bigint primary key auto_increment,
    name varchar(10) unique ,
    avatar_url varchar(50),
);

insert into topic (name, avatar_url) values ('c++','https://alicdn.bestsort.cn/icon/cplusplus.png');
insert into topic (name, avatar_url) values ('java','https://alicdn.bestsort.cn/icon/java.png');
insert into topic (name, avatar_url) values ('mybatis','https://alicdn.bestsort.cn/icon/mybatis.png');
insert into topic (name, avatar_url) values ('redis','https://alicdn.bestsort.cn/icon/redis.png');
insert into topic (name, avatar_url) values ('mysql','https://alicdn.bestsort.cn/icon/mysql.png');
insert into topic (name, avatar_url) values ('acm','https://alicdn.bestsort.cn/icon/acm.png');
insert into topic (name, avatar_url) values ('python','https://alicdn.bestsort.cn/icon/python.png');
insert into topic (name, avatar_url) values ('springboot','https://alicdn.bestsort.cn/icon/springboot.png');




