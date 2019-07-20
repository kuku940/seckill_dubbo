
## springboot整合swagger UI生成api帮助文档

1. **引入swagger的mvn引用**

        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger2</artifactId>
            <version>2.9.2</version>
        </dependency>
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger-ui</artifactId>
            <version>2.9.2</version>
        </dependency>
        

2. **添加配置文件SwaggerConfig文件**

3. **添加SwaggerController**  
添加restful风格API文档的注释

4. **启动服务，并访问接口**  
[SwaggerUI地址](http://localhost:8080/swagger-ui.html#/)  
![restful风格文档](./src/resources/image/Swagger-Restful风格文档.jpg)