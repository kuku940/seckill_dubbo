package cn.xiaoyu.seckill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 拥有@SpringBootApplication注解的类中，使用SpringApplication的run方法可以通过JAR启动项目
 *
 * @author roin
 */

@SpringBootApplication
public class SeckillApplication {
    public static void main(String[] args) {
        SpringApplication.run(SeckillApplication.class, args);
    }
}
