package cn.xiaoyu.seckill.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * SwaggerUI生成Restful API文档和测试页面
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    /**
     * 可以通过变量设置swagger-ui是否显示，比如测试环境可以暴露api文档，生产环境我们就关闭
     */
    @Value("${swagger.enable}")
    private boolean enableSwagger;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.xiaoyu.seckill.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("springboot整合swagger构建api文档")
                .description("restful风格的问的那个")
                .contact(new Contact("联系人姓名", "seckill_dubbo.xiaoyu.cn", "联系人邮箱"))
                .version("1.0.0-SNAPSHOT")
                .build();
    }
}
