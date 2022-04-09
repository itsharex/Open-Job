# Open-Job

开源分布式任务调度系统，[项目官方文档地址](https://lijunping365.github.io/#/)

## 🎨 Open-Job 介绍

🔥2022 最新的轻量级分布式任务调度系统

## ✨ 已实现功能点

1. 定时任务基于 redis 实现，支持动态修改任务状态，同时支持拓展其他实现方式

2. 客户端与服务端通信采用 Grpc，同时支持拓展其他通信方式

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
    <groupId>com.saucesubfresh.job</groupId>
    <artifactId>job-starter-client</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

2. 创建任务执行类并实现 JobHandler

示例1

```java
@Slf4j
@Component
public class JobHandlerOne implements JobHandler {

    @Override
    public String bindingJobHandlerName() {
        return JobHandlerNameConstants.JOB_ONE;
    }
    
    @Override
    public void handler(String params) {
        log.info("JobHandlerOne 处理任务");
    }
}
```

示例2

```java
@Slf4j
@Component
public class JobHandlerTwo implements JobHandler {

    @Override
    public String bindingJobHandlerName() {
        return JobHandlerNameConstants.JOB_TWO;
    }

    @Override
    public void handler(String params) {
        log.info("JobHandlerTwo 处理任务");
    }
}
```

注意：

1. handler 方法的参数 params 为 json 字符串格式，可进行 json 序列化

2. 启动后端服务端与后端客户端无先后顺序

3. 详细说明请参阅官方文档：https://lijunping365.github.io/#/

## 🔨 目前还未完成的功能

1. 任务的监控功能

2. 任务的报警功能

## ❓ FAQ

有问题可以提 issues，我会及时解答

## 🎉收尾

欢迎使用，欢迎 star

# 版本重大更新，文档后续会更新
