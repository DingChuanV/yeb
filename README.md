# yeb

## 🐈‍项目背景
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

针对上面的提到的拦截器做补充，当登陆成功，后端把token，传给前端，前端将这个token放在请求体，每次前端向后端发起请求，后端会有相应的拦截器（每次会对这个请求拦截，判断请求体中token
是否有效）拦截。

后面补充了验证码的功能。并在SpringSecurity的配置类中配置了忽略验证码的请求路径，也就是给放行。

## 🐔Swagger的相关配置

因为是前后端分离的方式，所以我们要准备Swagger接口文档。（还提供接口调试的功能）。

![](https://bearbrick0.oss-cn-qingdao.aliyuncs.com/images/img/202204130955021.png)

接口文档的地址：http://localhost:8081//doc.html


接口文档的地址：https://www.cnblogs.com/bearbrick0/p/16138856.html


## 🐧测试登陆（认证和授权）功能

![image-20220413104039532](https://bearbrick0.oss-cn-qingdao.aliyuncs.com/images/img/202204131040370.png)

<img src="https://bearbrick0.oss-cn-qingdao.aliyuncs.com/images/img/202204131048056.png" alt="image-20220413104257118" style="zoom:50%;" />

这个tokenHead:我们配置的是Bearer，在配置文档里面写的。下面的是token。

![image-20220413104704876](https://bearbrick0.oss-cn-qingdao.aliyuncs.com/images/img/202204131048798.png)

此时给用户授权。

![image-20220413104800430](https://bearbrick0.oss-cn-qingdao.aliyuncs.com/images/img/202204131048237.png)

## 🐒权限控制

根据请求的url分析请求所需角色。

判断用户角色。

## 🐣推荐一个好用的插件

我们在开发的时候，有时候会忘记一些Api的使用，我们通常会有两种办法，一种就是去百度，另一种就是command+b,进去看看，是怎么定义的。
有时候会看到里面全是英文。
这个时候，有个插件，可以看到英文，而且翻译的和原本英文要表达的意思差不多。

![image-20220414101234036](https://bearbrick0.oss-cn-qingdao.aliyuncs.com/images/img/202204141012394.png)

<img src="https://bearbrick0.oss-cn-qingdao.aliyuncs.com/images/img/202204141014517.png" alt="image-20220414101423864" style="zoom:50%;" />

![image-20220414101509264](https://bearbrick0.oss-cn-qingdao.aliyuncs.com/images/img/202204141015380.png)

个人感觉还是很ok的。

## 🐥RESTFUL API

我个人认为，要弄清楚什么是RESTful API,首先要弄清楚什么是REST。REST 全称：REpresentational State Transfer，英文翻译过来就是“表现层状态转化”。
如果单看这个概念，估计大家很难理解。那下面就让我来用一句话通俗解释一下。 

RESTful:用URL定位资源、用HTTP动词（GET、POST、PUT、DELETE)描述操作。只要记住这句话也就不难理解了。

RESTful API就是REST风格的API，即rest是一种架构风格，跟编程语言无关，跟平台无关，采用HTTP做传输协议.

那么在什么场景下使用RESTful API呢?

在当今的互联网应用的前端展示媒介很丰富。有手机、有平板电脑还有PC以及其他的展示媒介，从而你的系统的客户端要支持浏览器、Android、ios等。此时你肯定不会单独为每个客户端写一个后台系统，而是写一个后台系统提供rest风格的URI，这三个客户端都请求该同一个后台系统。那么这些前端接收到的用户请求统一由一个后台来处理并返回给不同的前端肯定是最科学和最经济的方式如。RESTful API就是一套协议来规范多种形式的前端和同一个后台的交互方式。

![image-20220414103223620](https://bearbrick0.oss-cn-qingdao.aliyuncs.com/images/img/202204141032526.png)

前后端分离之后，我们就不用在前端页面中写jstl，el表达式了，甚至连jsp都不需要使用了，直接html即可，前端跟后端的交互使用ajax+json这样符合rest风格的架构即可.

好处就是：
- 项目的分工更加明确了
- 实现了前后端的解耦
- 可以将前端和后端部署到不同的服务器上来减轻服务器的压力
- 前后端代码在不同的服务器上，可以提高安全性

RESTful API由后台也就是SERVER来提供前端来调用。前端调用API向后台发起HTTP请求，后台响应请求将处理结果反馈给前端。也就是说RESTful 是典型的基于HTTP的协议。

那么RESTful API有哪些设计原则和规范呢？

1. 资源。首先是弄清楚资源的概念。资源就是网络上的一个实体，一段文本，一张图片或者一首歌曲。资源总是要通过一种载体来反应它的内容。文本可以用TXT，也可以用HTML或者XML、图片可以用JPG格式或者PNG格式，JSON是现在最常用的资源表现形式。
2. 统一接口。RESTful风格的数据元操CRUD（create,read,update,delete）分别对应HTTP方法：GET用来获取资源，POST用来新建资源（也可以用于更新资源），PUT用来更新资源，DELETE用来删除资源，这样就统一了数据操作的接口。
3. URI。可以用一个URI（统一资源定位符）指向资源，即每个URI都对应一个特定的资源。要获取这个资源访问它的URI就可以，因此URI就成了每一个资源的地址或识别符。一般的，每个资源至少有一个URI与之对应，最典型的URI就是URL。

名次解释：

URI（统一资源标识符）：可以唯一标识一个资源

URL（统一资源定位符）：可以提供找到某个资源的路径，比如平时最常见的网址：
一般一个URL也是一个URI，比如上面的网址，即URL可以看做是URI的子集，在图书领域中一本书都有唯一的一个isbn编号，这个编号其实也是URI。


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



