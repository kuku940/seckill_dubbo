package cn.xiaoyu.seckill.service.seckill;

import cn.xiaoyu.seckill.domain.Seckill;
import cn.xiaoyu.seckill.dto.Exposer;
import cn.xiaoyu.seckill.dto.SeckillExecution;
import cn.xiaoyu.seckill.exception.RepeatKillException;
import cn.xiaoyu.seckill.exception.SeckillCloseException;
import cn.xiaoyu.seckill.exception.SeckillException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Roin_zhang
 * @date 2018/3/17 20:40
 */
public interface SeckillService {

    /**
     * 查询全部的秒杀记录
     *
     * @return
     */
    List<Seckill> getSeckillList();

    /**
     * 查询单个秒杀记录
     *
     * @param seckillId
     * @return
     */
    Seckill getById(long seckillId);


    //再往下，是我们最重要的行为的一些接口

    /**
     * 在秒杀开启时输出秒杀接口的地址，否则输出系统时间和秒杀时间
     *
     * @param seckillId
     */
    Exposer exportSeckillUrl(long seckillId);


    /**
     * 执行秒杀操作，有可能失败，有可能成功，所以要抛出我们允许的异常
     *
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     */
//    @Transactional // 在dubbo中使用事务会使得生产者发布无效
    /**
     * 使用注解控制事务方法的优点:
     * 1.开发团队达成一致约定，明确标注事务方法的编程风格
     * 2.保证事务方法的执行时间尽可能短，不要穿插其他网络操作RPC/HTTP请求或者剥离到事务方法外部
     * 3.不是所有的方法都需要事务，如只有一条修改操作、只读操作不要事务控制
     */
    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
            throws SeckillException, RepeatKillException, SeckillCloseException;
}