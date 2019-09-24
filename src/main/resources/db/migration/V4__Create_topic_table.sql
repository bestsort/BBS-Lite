create table topic(
    id bigint primary key auto_increment,
    name varchar(10),
    avatar_url varchar(50),
    question_count bigint default 0,
    follow bigint default 0
);

insert into topic (name, avatar_url) values ('c++','https://alicdn.bestsort.cn/icon/c++.jpg');
insert into topic (name, avatar_url) values ('java','https://alicdn.bestsort.cn/icon/java.jpeg');
insert into topic (name, avatar_url) values ('mybatis','https://alicdn.bestsort.cn/icon/mybatis.jpg');
insert into topic (name, avatar_url) values ('redis','https://alicdn.bestsort.cn/icon/redis.jpg');
insert into topic (name, avatar_url) values ('mysql','https://alicdn.bestsort.cn/icon/mysql.png');
insert into topic (name, avatar_url) values ('acm','https://alicdn.bestsort.cn/icon/acm.jpg');
insert into topic (name, avatar_url) values ('python','https://alicdn.bestsort.cn/icon/python.jpg');
insert into topic (name, avatar_url) values ('springboot','https://alicdn.bestsort.cn/icon/springboot.jpg');




