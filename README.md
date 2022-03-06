# Open-Job

🔥开源分布式任务调度系统，[https://lijunping365.github.io/#/][项目官方文档地址]

## 🎨 Open-Job 介绍

简洁而高效的轻量级分布式任务调度系统

## 🔧 已实现功能点

1. 定时任务基于 redis 实现，支持动态修改任务状态，同时支持拓展其他实现方式

2. 客户端与服务端通信采用 Grpc，同时支持拓展其他通信方式

3. 注册中心支持 Nacos、Zookeeper，同时支持拓展其他注册中心，而且支持节点动态上线下线

4. 客户端集群部署支持负载均衡，默认提供了一致性hash、随机权重算法，支持多种容错机制，默认提供了失败重试、故障转移等机制，负载均衡和容错都支持拓展

5. 任务监控报警能力支持

6. 前后端分离，管理后台基于 antd-pro 搭建

## ✨ 项目模块说明

-job-admin: 任务后台管理系统，可进行添加、修改、删除任务，启动、停止任务，查看并监控任务执行情况。

-job-common: 整个项目用到的公共模块，包括一些工具包，常量等

-job-core: Grpc 通信核心模块，里面定义了通信协议

-job-client-sample: 任务执行模块，支持集群部署

-job-starter: 包含了一些自研插件，不仅可以在本项目中可以使用，而且也可以用在其他项目中的插件

--job-starter-alarm: 报警功能，默认提供钉钉报警通知

--job-starter-schedule: 基于 redis 的定时任务，轻量级，易拓展

--job-starter-security: 支持多种登录方式，支持生成验证 token，并支持多种 token 生成存储策略

--job-starter-captcha: 支持多种验证码的生成，储存，校验（包括图片验证码，短信验证码，二维码）

--job-starter-server: 任务分发器，发现执行器并调用执行器进行任务分发

--job-starter-client: 任务执行器，支持集群部署，拥有多种注册到服务端的方式

## 🔨 实现原理说明

1. 本项目是基于 spring-boot 搭建的分布式应用

## 🍪目前还未完成的功能

1. 任务的监控功能

2. 任务的报警功能

## ❓ FAQ

<br/>
<div align="center">
    <a href="https://github.com/lijunping365/Open-Job">关注公众号，你可以学到的更多！</a>
</div>
<br/>  

- **加我微信**
  ![](assets/img/wechat/wechat.png)

- **加群交流**

- **公众号**



## 后续
开源分布式任务调度框架，本项目是从本人之前做的一个系统中抽离出来的，开源不易，欢迎 star

使用文档及功能说明会在后续补充上

欢迎 star，欢迎讨论交流，欢迎分享此项目


[]: https://lijunping365.github.io/#/