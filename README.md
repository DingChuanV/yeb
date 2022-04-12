# yeb

## 🐈‍⬛项目背景
本项目目的是实现中小型企业的在线办公系统，云E办在线办公系统是一个用来管理日常的办公事务的一个系统，他能够管的内容有：日常的各种流程审批，新闻，通知，公告，文件信息，财务，人事，费用，资产，行政，项
目，移动办公等等。它的作用就是通过软件的方式，方便管理，更加简单，更加扁平。更加高效，更加规范，能够提高整体的管理运营水平。

本项目在技术方面采用最主流的前后端分离开发模式，使用业界最流行、社区非常活跃的开源框架SpringBoot来构建后端，旨在实现云E办在线办公系统。

包括职位管理、职称管理、部门管理、员工管理、工资管理、在线聊天等模块。项目中还会使用业界主流的第三方组件扩展大家的知识面和技能池。

## 🐈技术栈
<img src="https://bearbrick0.oss-cn-qingdao.aliyuncs.com/images/img/202204101420283.png" alt="image-20220410141656163" style="zoom:50%;" />

## 🐕登陆功能

采用的技术是SpringSecurity+JWT。SpringSecurity主要解决的访问的控制和授权、使用Jwt生成的token对用户访问资源的的一种认证机制。

通过SpringSecurity的访问控制，使用户必须安全访问该系统，在实现自定义登录逻辑的基础上使用Jwt这种认证框架，使用户的认证（Token认证，对用户访问资源的一种机制）和授权（还是SpringSecurity做的，在用户通过我们自定义的登录逻辑之后，用户就被授权了）得到有效的解决。

登陆的流程：

既然要使用SpringSecurity和JWT，首先是前端传用户名（username）和密码（password
）给我们，后端会先去校验这个用户名和密码，如果用户名和密码，有错误，我们要给前端响应错误信息。
如果没有错误，我们会生成token，传给前端，前端拿到这个token（令牌），就会放在它的请求头里面，后面前端的任何请求都会携带这个Jwt令牌（token），后端会有响应的拦截器对这个token
验证，验证通过之后才会访问我们对应的接口。如果jwt验证不通过，又可能token过期了（失效），那就要提示用户重新登陆。


## 🐓相关技术学习

#### 🦩RabbitMQ教学视频笔记和源码

https://www.cnblogs.com/bearbrick0/category/2098894.html
https://github.com/bearbrick0/rabbitmq

具体使用干啥了，正在补充.....


#### 🐇分布式文件系统FastDFS学习

[2021B站最新FastDFS教程 --分布式文件系统FastDFS入门到实战视频精讲_哔哩哔哩_bilibili](https://www.bilibili.com/video/BV1gh411z7kJ?spm_id_from=333.337.search-card.all.click)

正在补充...


#### 🦢SpringSecurity学习

介绍SpringSecurity：https://www.cnblogs.com/bearbrick0/p/16129311.html

SpringSecurty中的CSRF在工作中的使用方式：https://www.cnblogs.com/bearbrick0/p/16130738.html

SpringSecurty_demo整体的代码仓库：https://github.com/bearbrick0/springsecurity_demo

#### 🦚webScoket学习

正在补充...

#### 🐁Mybatis-Plus学习测试案例


自己学习的代码和测试案例：https://github.com/bearbrick0/mybatis_plus


#### 🐿JWT和OAuth2的学习和理解

笔记：
https://www.cnblogs.com/bearbrick0/p/16133119.html
https://www.cnblogs.com/bearbrick0/p/16136429.html

代码：我的仓库中有。



