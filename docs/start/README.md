## 前言

此论坛是我从2019年9月初开始写的，到此文发布时是11月份，前前后后将近两个月作用。完成了论坛的大部分功能后就将其上线了。目前还有两个模块和一些优化没有做。但是简单的使用应该是不会收到太大影响（事实上仍然有大量遗留Bug）。

目前虽然有**通过邮箱注册**和**GitHub直接登录**两种登录方式，但是还是推荐大家直接采用**GitHub**登录。因为个人中心模块还未完成的原因，所以通过邮箱登录的用户信息**暂时无法修改，只能为默认值**。

## 已知BUG

- 打开两个或多个相同页面且均点赞/收藏并进行刷新时会提交两次相同请求（也就是计数会+2）
- 缓存未及时刷新引起的一些BUG。

如果您在使用过程中有发现BUG可以提交Issue，我会尽快处理的。

## 功能
### 支持的功能
- 注册（通过邮箱点击激活链接激活账户，每日凌晨3点清空未激活的账户信息）
- 登录（账户登录、第三方登录）
- 发布文章（Markdown语法）
- 收藏、点赞
- 评论
- 查看个人中心（半成品，目前仅仅支持查看消息）
- 根据文章标题搜索
- 支持按照标签（进入文章详情页面点击对应标签）、话题（位于导航栏）查看相关问题
- 支持文章图片自动上传
- 缓存（支持随机TTL，计数，开关，但是目前尚未开发刷新、删除这几个功能）

## 部署
因为目前还未发布1.0版本，所以本论坛目前采用 springboot plugin 进行部署。如果您想尝试部署该论坛，可以参考以下步骤：

### Linux
1. 确保主机中已安装以下工具并且已加入PATH（可以在命令行中启动）：

  - openjdk-8
  - maven
  - git

2. 执行`git clone --depth=1 https://github.com/bestsort/BBS-Lite.git`从 GitHub 拷贝源码到本地
3. 
```bash
#进入项目目录
cd BBS-lite 
#拷贝配置文件到指定位置
mkdir config
cp src/main/java/resources/applicatiom.yml config/application.yml
cp pom.xml run.xml
```
4. 参考`config/application.yml`内的提示并将其改为自己的相关配置
5. 将`run.xml`中`<properties></properties>`的 jdbc 相关配置改为自己数据库相关配置（`<jdbc.url>`中仅需要改动主机、端口、数据库名即可，**数据库需自行创建且编码格式为`utf8m4`**）
6. 在BBS-lite根目录执行`mvn flyway:migrate`自动生成相关表
7. 执行`mvn -f run.pom spring-boot:run`即可启动应用。如需后台运行，目前可暂时使用`nohup mvn -f run.pom spring-boot:run &`替代。
8. 执行成功后,即可进入`localhost:60030`查看。（需确保端口未被占用/拦截）
#### 更新

在源码根目录下有一个`restart.sh`的脚本文件，执行`./restart.sh`会找到您论坛的id并且杀死这个进程，然后从[BBS-Lite](https://github.com/bestsort/BBS-Lite)进行 **pull** 操作后处理一些更新内容（如数据库字段修改等）并重启您的论坛。当然，这一切都是自动进行的，您只需要执行`./restart.sh`并确保`run.xml`中为您自己的配置信息即可



### 注意事项
`BBS-Lite/config` 文件夹已加入 `.gitignore` 中,在此文件夹内的配置文件不会因为源码的更新而更新。而且因为 spring-boot 配置文件优先级的关系,所以 spring-boot 会优先读取 `config/application.yml` 作为配置文件, 其中没有填写的部分配置会再次读取`main/java/resources/applicatiom.yml`。因此**强烈推荐**把您自己的配置文件写好后拷贝一份到 `config/application.yml` 中以防论坛重启时配置文件被重置。



