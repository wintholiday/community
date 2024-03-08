# 稀土掘金社区项目(community2)

## 项目简介

项目采用Spring Boot框架和MyBatis持久层框架，后端数据库使用MySQL，支持Docker容器化部署。原本计划使用Spring Security进行安全性配置，但由于配置问题，该部分已被注释。

## 技术栈

- 后端：Spring Boot, MyBatis
- 数据库：MySQL
- 容器化：Docker
- 文件存储：阿里云OSS
- 安全认证：JWT

## 主要功能

### 首页

- 支持模糊查询文章
- 按时间和点击量显示文章榜

### 文章

- 获取文章内容
- 创建文章
- 点赞文章
- 发布评论以及对评论的评论
- 获取用户文章列表

### 用户

- 上传头像和修改头像
- 用户注册和登录
- 修改用户名和密码
- 获取用户点赞的文章列表
- 获取用户头像

## 未实现部分

- 联调测试尚未完成
- 缓存处理未实现
- 草稿箱功能未添加
- 实时预览功能未实现
- 登录拦截存在一些问题待解决

## 如何运行

1. 克隆仓库到本地

   ```
   git clone [仓库地址]
   ```

2. 使用Docker运行MySQL数据库

   ```
   docker run --name mysql -e MYSQL_ROOT_PASSWORD=my-secret-pw -d mysql:tag
   ```

3. 修改`application.properties`中的数据库连接配置

4. 在项目根目录下运行Spring Boot应用

   ```
   ./mvnw spring-boot:run
   ```

5. 访问`http://localhost:8080`查看项目首页

- pom.xml
- src
  - main
    - java
      - com
        - example
          - community
            - CommunityApplication.java
            - config
              - WebConfig.java
            - controller
              - ArticleController.java
              - AvatarController.java
              - CommentController.java
              - FavoriteController.java
              - GlobalExceptionHandler.java
              - HomePage.java
              - LikesController.java
              - LoginController.java
              - UsersController.java
            - filter
              - LoginCheckFilter.java
            - interceptor
              - LoginCheckInterceptor.java
            - mapper
              - ArticleMapper.java
              - CommentMapper.java
              - FavoriteMapper.java
              - LikeMapper.java
              - UserMapper.java
            - pojo
              - Article.java
              - Comment.java
              - LoginRequest.java
              - Result.java
              - User.java
            - service
              - ArticleService.java
              - CommentService.java
              - FavoriteService.java
              - LikesService.java
              - UsersService.java
            - utils
              - AliOSSProperties.java
              - AliOSSUtils.java
              - JwtUtils.java
              - SnowflakeIdGenerator.java
    - resources
      - application.properties
      - com
        - example
          - community
            - mapper
              - ArticleMapper.xml
              - LikeMapper.xml
              - UserMapper.xml
      - sql.md
      - static
      - templates
  - test
    - java
      - com
        - example
          - community
            - CommunityApplicationTests.java



项目结构如上

接口文档

https://apifox.com/apidoc/shared-7d025b66-c472-4d0a-b162-04ef1adba8d5

