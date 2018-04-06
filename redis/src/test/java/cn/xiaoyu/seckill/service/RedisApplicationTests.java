package cn.xiaoyu.seckill.service;

import cn.xiaoyu.seckill.domain.Seckill;
import cn.xiaoyu.seckill.service.redis.RedisService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisApplicationTests {
    @Autowired
    private RedisService redisService;

    @Test
    @Ignore
    public void contextLoads() throws InterruptedException {
        Seckill seckill = new Seckill();
        seckill.setName("测试Demo");
        seckill.setNumber(123456);

        redisService.set("seckill", seckill, 60L);

        System.out.println("立即取：" + redisService.get("seckill"));
        Thread.sleep(2000);
        System.out.println("2S后再取：" + redisService.get("seckill"));
    }

}
