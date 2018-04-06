package cn.xiaoyu.seckill.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * SpringBoot web配置
 *
 * @author xiaoyu
 * @date 2017/11/16
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    /**
     * 添加静态资源文件映射关系
     * 这种方式可以访问static的二级目录，properties配置文件的方式无法访问二级目录
     * <p>
     * 如果不配置该方法，则按照如下的路径进行访问
     * 默认的优先级： META/resources > resources > static > public
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //将所有/static/** 访问都映射到classpath:/static/ 目录下
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");


//        //将所有/static/** 访问都映射到绝对路径 F:/static/目录下
//        registry.addResourceHandler("/static/**").addResourceLocations("file:F:/static/");
    }

//    /**
//     * 多个拦截器组成一个拦截器链
//     * addPathPatterns      用于添加拦截规则
//     * excludePathPatterns  用户排除拦截
//     *
//     * @param registry
//     */
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new LoginInterceptor())
//                .addPathPatterns("/**").excludePathPatterns("/static/**");
////        // 可以添加多个拦截器
////        registry.addInterceptor(new OtherInterceptor())
////                .addPathPatterns("/**").excludePathPatterns("/static/**");
//        super.addInterceptors(registry);
//    }
}
