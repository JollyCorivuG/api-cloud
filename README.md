# 项目介绍
本项目实现了一个提供 API 接口供开发者调用的平台。用户可以注册登录后开通接口调用权限、浏览接口、
在线调试、还能使用**客户端 SDK**轻松在代码中调用接口；管理员可以接入并发布接口，统计各接口调用情况。
> SDK 地址：https://github.com/JollyCorivuG/api-cloud-client-sdk

# 项目架构图
![img.png](https://github.com/JollyCorivuG/api-cloud/blob/main/docs/%E9%A1%B9%E7%9B%AE%E6%9E%B6%E6%9E%84%E5%9B%BE.PNG)

# 技术选型
- Java 17 + Spring Boot 3.1.2
- Spring Boot Starter（SDK 开发）
- Dubbo（RPC 远程调用）
- Nacos（服务注册中心）
- Spring Cloud Gateway（网关、限流、日志实现）
