## 整体介绍

使用springboot+dubbo+mybatis+redis搭建项目

api - 公共类库，被所有模块依赖  
seckill - 秒杀服务  
redis - Redis封装的单独服务  
controller - 控制层

## 启动项目

1. 启动redis服务器，监听6379端口；
2. 启动zookeeper服务器，监听2181端口；
3. 启动tomcat服务器，内置dubbo-admin管理控制台；
4. 分别启动RedisApplication、SeckillApplication和ControllerApplication服务。



## 配置dubbo
springboot配置dubbo，参考[github地址](https://github.com/apache/incubator-dubbo-spring-boot-project)

    <dependency>
        <groupId>com.alibaba.boot</groupId>
        <artifactId>dubbo-spring-boot-project</artifactId>
        <version>0.1.0</version>
    </dependency>



## 多环境配置文件切换
[springboot使用maven的profiles实现多环境配置文件切换](https://my.oschina.net/scottCoder/blog/1541004)

## 参考项目
[SpringBoot-Dubbo-Docker-Jenkins](https://github.com/bz51/SpringBoot-Dubbo-Docker-Jenkins)

