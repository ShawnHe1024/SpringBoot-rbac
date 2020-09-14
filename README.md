# RBAC

本项目为前后端分离的管理系统中后台接口部分，封装了接口级别的权限拦截功能，开箱即用。

###### 目录结构
```
├── README.md   
├── pom.xml     
├── rbac.sql
└── src
    ├── main
    │ ├── java
    │ │ └── pers
    │ │     └── shawn
    │ │         └── rbac
    │ │             ├── RbacApplication.java
    │ │             ├── bean            通用bean对象
    │ │             ├── common          常量
    │ │             ├── module          具体业务代码，module中的不同业务子系统都有自己的包结构
    │ │             │ └── rbac          基于rbac的权限接口
    │ │             │     ├── controller    
    │ │             │     ├── entity        
    │ │             │     ├── mapper        
    │ │             │     │ └── xml
    │ │             │     ├── service
    │ │             │     │ └── impl
    │ │             │     └── vo        与前端交互的视图对象
    │ │             ├── system          系统配置部分
    │ │             │ ├── config        项目配置
    │ │             │ ├── controller    通用接口（如参数校验，登录等）
    │ │             │ └── interceptor   拦截器
    │ │             └── util            工具类
    │ └── resources
    └── test
```

###### 权限系统设计思路
每个url都是一个资源，需要添加到resources表中，用户通过角色获取到拥有的资源，缓存在redis中，每次请求时查询，有则允许访问。  
由于是前后端分离项目，资源也分为接口类型和页面类型，用户登录后通过接口获取到拥有的页面资源信息，由前端进行相关处理。  

