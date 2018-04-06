package cn.xiaoyu.seckill.service.seckill.impl;

import cn.xiaoyu.seckill.dao.SeckillDao;
import cn.xiaoyu.seckill.dao.SuccessKilledDao;
import cn.xiaoyu.seckill.domain.Seckill;
import cn.xiaoyu.seckill.domain.SuccessKilled;
import cn.xiaoyu.seckill.dto.Exposer;
import cn.xiaoyu.seckill.dto.SeckillExecution;
import cn.xiaoyu.seckill.enums.SeckillStatEnum;
import cn.xiaoyu.seckill.exception.RepeatKillException;
import cn.xiaoyu.seckill.exception.SeckillCloseException;
import cn.xiaoyu.seckill.exception.SeckillException;
import cn.xiaoyu.seckill.service.seckill.SeckillService;
import com.alibaba.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 在实现类上需要加上Dubbo的@Service注解，从而dubbo会在项目启动的时候扫描到该注解，将它发布成一项RPC服务。
 *
 * @author Roin_zhang
 * @date 2018/3/17 20:47
 */
@Service(version = "1.0.0")
public class SeckillServiceImpl implements SeckillService {
    private Logger log = LoggerFactory.getLogger(SeckillServiceImpl.class);

    //加入混淆字符串(秒杀接口)的salt，避免用户猜出我们的md5值，值任意给，越复杂越好
    private final String salt = "shsds/ljdd'l.";

    @Resource
    private SeckillDao seckillDao;
    @Resource
    private SuccessKilledDao successKilledDao;

    @Override
    public List<Seckill> getSeckillList() {
        List<Seckill> seckills = seckillDao.queryAll(0, 10);
        return seckills;
    }

    @Override
    public Seckill getById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    @Override
    public Exposer exportSeckillUrl(long seckillId) {
        Seckill seckill = seckillDao.queryById(seckillId);
        if (seckill == null) {
            return new Exposer(false, seckillId);
        }

        // 若是秒杀开启
        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();

        // 系统当前时间
        Date nowTime = new Date();
        if (startTime.getTime() > nowTime.getTime() || endTime.getTime() < nowTime.getTime()) {
            // 秒杀未开始或者已经结束
            return new Exposer(false, seckillId, nowTime.getTime(), startTime.getTime(), endTime.getTime());
        }

        // 秒杀开启，返回秒杀商品id，用给接口加密的md5
        String md5 = getMd5(seckillId);
        return new Exposer(true, md5, seckillId);
    }

    private String getMd5(long seckillId) {
        String base = seckillId + "/" + salt;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    @Override
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillException, RepeatKillException, SeckillCloseException {
        // 秒杀是否成功，成功：减库存，增加明细；失败：抛出异常，事务回滚
        if (md5 == null || !md5.equals(getMd5(seckillId))) {
            // 秒杀数据被重写了
            throw new SeckillException("seckill data rewrite");
        }

        // 执行秒杀逻辑：减库存 + 增加购买明细
        Date nowTime = new Date();
        try {
            // 减库存
            int updateCount = seckillDao.reduceNumber(seckillId, nowTime);
            if (updateCount <= 0) {
                //没有更新库存记录，说明秒杀结束
                throw new SeckillCloseException("seckill is closed");
            } else {
                //否则更新了库存，秒杀成功,增加明细
                int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
                //看是否该明细被重复插入，即用户是否重复秒杀
                if (insertCount <= 0) {
                    throw new RepeatKillException("seckill repeated");
                } else {
                    //秒杀成功,得到成功插入的明细记录,并返回成功秒杀的信息
                    SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
                    return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS, successKilled);
                }
            }
        } catch (SeckillCloseException e1) {
            throw e1;
        } catch (RepeatKillException e2) {
            throw e2;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            //所以编译期异常转化为运行期异常
            throw new SeckillException("seckill inner error :" + e.getMessage());
        }
    }
}
