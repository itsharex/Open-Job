# Open-Job

<p align="center">
🔥2022 最新的轻量级分布式任务调度系统
</p>

<p align="center">
  <a href="https://search.maven.org/search?q=g:com.saucesubfresh%20a:open-starter-*">
    <img alt="maven" src="https://img.shields.io/github/v/release/lijunping365/Open-Job?include_prereleases&logo=Open-Job&style=plastic">
  </a>

  <a href="https://www.apache.org/licenses/LICENSE-2.0">
    <img alt="licenses" src="https://img.shields.io/badge/license-Apache%202-4EB1BA.svg?style=flat-square">
  </a>

  <a href="https://github.com/lijunping365/Open-Job">
    <img alt="github" src="https://badgen.net/github/stars/lijunping365/Open-Job?icon=github" >
  </a>
  
  <a href="https://github.com/lijunping365/Open-Job">
      <img alt="forks" src="https://badgen.net/github/forks/lijunping365/Open-Job?icon=github&color=4ab8a1" >
    </a>
</p>

## 🎨 Open-Job 介绍

[项目官方文档地址](https://lijunping365.github.io/#/)

## ✨ 已实现功能点

1. 定时任务基于 redis 实现，支持动态修改任务状态，同时支持拓展其他实现方式

2. 客户端与服务端通信采用 Grpc，同时支持 Netty

3. 注册中心支持 Nacos、Zookeeper，同时支持拓展其他注册中心，而且支持节点动态上线下线

4. 客户端集群部署支持负载均衡，默认提供了一致性hash、随机权重算法，支持多种容错机制，默认提供了失败重试、故障转移等机制，负载均衡和容错都支持拓展

5. 任务监控报警能力支持

6. 前后端分离，管理后台基于 antd-pro 搭建

## 🍪 快速开始

### 1 搭建任务管理系统

1. 下载本项目

git clone https://github.com/lijunping365/Open-Job.git

2. 创建数据库表

sql 文件在 doc/open_job.sql

3. 下载前端项目

git clone https://github.com/lijunping365/Open-Job-Admin.git

需安装 node.js，

4. 启动服务端，启动前端项目

登录任务管理系统创建任务，之后便可以进行任务管理了

### 2 搭建任务执行模块

创建任务执行模块可按照项目中客户端示例工程搭建

1. 在任务执行项目中加入以下依赖

```xml
<dependency>
    <groupId>com.saucesubfresh</groupId>
    <artifactId>open-rpc-server</artifactId>
    <version>1.0.4</version>
</dependency>
```

2. 创建任务执行类并实现 JobHandler

示例1（类模式）

```java
@Slf4j
@JobHandler(name = "job-one")
@Component
public class OpenJobHandlerOne implements OpenJobHandler {

    @Override
    public void handler(String params) {
        log.info("JobHandlerOne 处理任务");
    }
}
```

示例2（方法模式）

```java
@Slf4j
@Component
public class OpenJobHandlerMethodOne{

    @JobHandler(name = "job-method-one1")
    public void handlerOne1(String params) {
        log.info("JobHandlerOne 处理任务, 任务参数 {}", params);
    }

    @JobHandler(name = "job-method-one2")
    public void handlerOne2(String params) {
        log.info("JobHandlerOne 处理任务, 任务参数 {}", params);
    }
}
```

注意：

1. handler 方法的参数 params 为 json 字符串格式，可进行 json 序列化

2. 启动后端服务端与后端客户端无先后顺序

3. 详细说明请参阅官方文档：https://lijunping365.github.io/#/

## ❓ FAQ

有问题可以提 issues，我会及时解答

## 🎉收尾

欢迎使用，欢迎 star
