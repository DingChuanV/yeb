# yeb

## 项目背景
本项目目的是实现中小型企业的在线办公系统，云E办在线办公系统是一个用来管理日常的办公事务的一个系统，他能够管的内容有：日常的各种流程审批，新闻，通知，公告，文件信息，财务，人事，费用，资产，行政，项
目，移动办公等等。它的作用就是通过软件的方式，方便管理，更加简单，更加扁平。更加高效，更加规范，能够提高整体的管理运营水平。

本项目在技术方面采用最主流的前后端分离开发模式，使用业界最流行、社区非常活跃的开源框架SpringBoot来构建后端，旨在实现云E办在线办公系统。

包括职位管理、职称管理、部门管理、员工管理、工资管理、在线聊天等模块。项目中还会使用业界主流的第三方组件扩展大家的知识面和技能池。

## 技术栈
<img src="https://bearbrick0.oss-cn-qingdao.aliyuncs.com/images/img/202204101420283.png" alt="image-20220410141656163" style="zoom:50%;" />

## 登陆功能

采用SpringSecurity+JwtToken实现登陆功能。
### 添加依赖
```xml
        <!--security依赖-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
        <!--Jwt依赖-->
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt</artifactId>
            <version>0.9.0</version>
        </dependency>
```
### 修改配置 application.yml

```yaml
jwt:
  # Jwt存储的请求头 授权
  tokenHeader: Authorization
  # Jwt加密秘钥
  secret: yeb-secret
  # Jwt 的超期限时间（60*60）*24
  expiration: 604800
  # Jwt负载中拿到开头
  tokenHead: Bearer
```
### 添加JwtTokenUtil工具类




## 相关技术学习

#### RabbitMQ教学视频笔记和源码

https://www.cnblogs.com/bearbrick0/category/2098894.html
https://github.com/bearbrick0/rabbitmq

具体使用干啥了，正在补充.....


#### 分布式文件系统FastDFS学习

[2021B站最新FastDFS教程 --分布式文件系统FastDFS入门到实战视频精讲_哔哩哔哩_bilibili](https://www.bilibili.com/video/BV1gh411z7kJ?spm_id_from=333.337.search-card.all.click)

正在补充...


#### SpringSecurity学习

https://www.cnblogs.com/bearbrick0/p/16129311.html

#### webScoket学习

正在补充...

#### Mybatis-Plus学习测试案例

https://github.com/bearbrick0/mybatis_plus


#### JWT和Token的学习和理解
正在补充...

