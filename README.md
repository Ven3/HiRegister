# HiRegister

## 简介  

简单医院挂号就诊系统, 采用 **Spring Boot** + **MyBatis** 实现

## 使用

### 初始化数据库

执行 [hiregister.sql](hiregister.sql) 初始化数据库  

初始化数据库包括    
- 创建数据库 **hiregister** 数据库;
- 创建用户 hiuser, 设置密码为 hipwd, 用户密码策略为是 mysql_native_password 的旧版密码策略; 
- 用户授权, 授予 hiuser 对 hiregister 库的所有权限; 

### 初始化工程

Idea / Eclipse 导入工程后, 刷新 Maven 即可, 执行调试, 本地通过 http://localhost:8080/ 即可访问  
初始用户名/密码为 scott/scott  