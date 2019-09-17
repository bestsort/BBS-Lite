<h1 align="center">Welcome to BBS-Lite 👋
<span align="center">

<img src="https://img.shields.io/badge/version-1.0-blue.svg?cacheSeconds=2592000"></img>
<img src="https://codebeat.co/badges/4c86e787-ca80-4e4b-8d94-29388044a3b4"></img>
<img src="https://img.shields.io/github/last-commit/bestsort/bbs-lite"></img>
<img src="https://img.shields.io/github/license/bestsort/bbs-lite"></img>
</span>
</h1>



> 这是一个基于 Spring Boot 搭建的轻论坛

## 快速开始
```bash
git clone https://github.com/bestsort/BBS-Lite.git
cd BBS-lite
```
将该项目导入到 IDEA 或者其他的IDE中,并配置`/src/main/resources/application.properties`
和`/src/main/resources/generatorConfig.xml`和`pom.xml`中102-105行中的数据库信息为自己的数据库配置然后执行以下命令
```bash
mvn package
mvn flyway:migrate
mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate
```
最后,配置好 Tomcat 即可启动项目

## 优点

- 代码简易，逻辑清晰
- 对 Spring Boot, MyBatis, Spring MVC 等常用框架都有应用
- 方便上手

## 采用技术及工具
- Spring Boot(快速构建应用)
    - spring-boot-starter-jdbc
    - spring-boot-starter-web
    - spring-boot-starter-mybatis(SQL 模板引擎)
    - spring-boot-starter-thymeleaf(HTML 模板引擎)
- Fastjson(JSON 文本处理)
- OkHttp3(处理网络请求)
- Flyway(数据库版本控制)
- Bootstrap3(前端框架)
- Druid(数据库连接池)
- Maven(包管理)

---
- IntelliJ IDEA
- MySQL Workbench
- Git
- Chrome
- Lombook
## 作者
**bestsort**
