package cn.xiaoyu.seckill.distributed.lock;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis创建分布式锁的测试用例
 *
 * @author Roin_zhang
 * @date 2018/4/7 14:20
 */
public class TestRedisDistributedLock {
    public static void main(String[] args) {
        SKService service = new SKService();
        for (int i = 0; i < 50; i++) {
            SeckillThread thread = new SeckillThread(service);
            thread.start();
        }
    }
}

class SeckillThread extends Thread {
    private SKService service;

    public SeckillThread(SKService service) {
        this.service = service;
    }

    @Override
    public void run() {
        try {
            service.seckill();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class SKService {
    private static JedisPool pool = null;
    private RedisDistributedLock lock = new RedisDistributedLock(pool);
    int n = 500;

    static {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(200);    // 设置最大连接数
        config.setMaxIdle(8);   // 设置最大空闲数
        config.setMaxWaitMillis(1000 * 100); // 设置最大等待时间为100秒

        // 在borrow一个jedis实例时，是否需要验证，若为true，则所有的jedis实例均是可用的
        config.setTestOnBorrow(true);
        pool = new JedisPool(config, "127.0.0.1", 6379, 3000);
    }

    public void seckill() throws InterruptedException {
        // 返回锁的value，供释放锁的时候进行判断
        String identifier = lock.lockWithTimeout("resource", 5000, 1000);
        System.out.println(Thread.currentThread().getName() + " get lock!");
        System.out.println(--n);    // 获取分布式锁之后，进行业务操作
        Thread.sleep(40);
        lock.releaseLock("resource", identifier);
        System.out.println(Thread.currentThread().getName() + " release lock!");
        System.out.println("-----");
    }
}