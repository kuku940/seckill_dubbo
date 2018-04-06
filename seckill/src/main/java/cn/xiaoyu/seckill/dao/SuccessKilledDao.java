package cn.xiaoyu.seckill.dao;

import cn.xiaoyu.seckill.domain.SuccessKilled;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author Roin_zhang
 * @date 2018/3/17 19:36
 */
@Mapper
public interface SuccessKilledDao {

    /**
     * 插入购买明细,可过滤重复
     *
     * @param seckillId 秒杀Id
     * @param userPhone 手机
     * @return 插入的行数
     */
    int insertSuccessKilled(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);


    /**
     * 根据秒杀商品的id查询明细SuccessKilled对象(该对象携带了Seckill秒杀产品对象)
     *
     * @param seckillId 秒杀Id
     * @return 秒杀明细
     */
    SuccessKilled queryByIdWithSeckill(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);
}