package cn.xiaoyu.seckill.dao;

import cn.xiaoyu.seckill.domain.Seckill;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;

import java.util.Date;
import java.util.List;

/**
 * @author Roin_zhang
 * @date 2018/3/17 19:35
 * <p>
 * https://upload-images.jianshu.io/upload_images/5796734-d08eeb2884aafaa9.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/631
 */
@Mapper
public interface SeckillDao {

    /**
     * 减库存
     * 并更新缓存中的信息
     *
     * @param seckillId
     * @param killTime
     * @return 如果影响行数>1，表示更新库存的记录行数
     */
    int reduceNumber(@Param("seckillId") long seckillId, @Param("killTime") Date killTime);

    /**
     * 根据id查询秒杀的商品信息
     * 并将指定的信息缓存到Redis中
     *
     * @param seckillId
     * @return
     */
    Seckill queryById(@Param("seckillId") long seckillId);

    /**
     * 根据偏移量查询秒杀商品列表
     *
     * @param offset
     * @param limit
     * @return
     */
    List<Seckill> queryAll(@Param("offset") int offset, @Param("limit") int limit);

}
