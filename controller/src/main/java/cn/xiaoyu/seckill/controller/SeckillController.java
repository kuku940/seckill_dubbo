package cn.xiaoyu.seckill.controller;

import cn.xiaoyu.seckill.domain.Seckill;
import cn.xiaoyu.seckill.dto.Exposer;
import cn.xiaoyu.seckill.dto.SeckillExecution;
import cn.xiaoyu.seckill.dto.SeckillResult;
import cn.xiaoyu.seckill.enums.SeckillStatEnum;
import cn.xiaoyu.seckill.exception.RepeatKillException;
import cn.xiaoyu.seckill.exception.SeckillCloseException;
import cn.xiaoyu.seckill.service.redis.RedisService;
import cn.xiaoyu.seckill.service.seckill.SeckillService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author Roin_zhang
 * @date 2018/3/17 23:46
 */
@RestController
@RequestMapping("/seckill")
public class SeckillController {
    @Reference(version = "1.0.0")
    private SeckillService seckillService;
    @Reference(version = "1.0.0")
    private RedisService redisService;

    // 从 application.properties 中读取配置，如取不到默认值为Hello Angel
    @Value("${application.hello:Hello Angel}")
    private String hello;

    /**
     * 获取列表页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        List<Seckill> list = seckillService.getSeckillList();
        model.addAttribute("list", list);
        return "list";
    }

    @RequestMapping(value = "/{seckillId}/detail", method = RequestMethod.GET)
    public String detail(@PathVariable("seckillId") Long seckillId, Model model) {
        if (seckillId == null) {
            return "redirect:/seckill/list";
        }

        // 先从redis缓存中获取，没有则从数据库中获取，然后存入redis缓存中
        Seckill seckill = (Seckill) redisService.get("seckill:" + seckillId);
        if (seckill == null) {
            seckill = seckillService.getById(seckillId);
            redisService.set("seckill:" + seckillId, seckill);
        }
        if (seckill == null) {
            return "forward:/seckill/list";
        }

        model.addAttribute("seckill", seckill);
        return "detail";
    }

    /**
     * ajax ,json暴露秒杀接口的方法
     *
     * @param seckillId
     * @return
     */
    @RequestMapping(value = "/{seckillId}/exposer", method = RequestMethod.GET,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<Exposer> exposer(@PathVariable("seckillId") Long seckillId) {
        try {
            Exposer exposer = seckillService.exportSeckillUrl(seckillId);
            return new SeckillResult<>(true, exposer);
        } catch (Exception e) {
            e.printStackTrace();
            return new SeckillResult<>(false, e.getMessage());
        }
    }

    @RequestMapping(value = "/{seckillId}/{md5}/execution", method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId") Long seckillId,
                                                   @PathVariable("md5") String md5,
                                                   @CookieValue(value = "userPhone", required = false) Long phone) {
        if (phone == null) {
            return new SeckillResult<>(false, "未注册");
        }

        try {
            SeckillExecution execution = seckillService.executeSeckill(seckillId, phone, md5);
            return new SeckillResult<>(true, execution);
        } catch (RepeatKillException e1) {
            SeckillExecution execution = new SeckillExecution(seckillId, SeckillStatEnum.REPEAT_KILL);
            return new SeckillResult<>(false, execution);
        } catch (SeckillCloseException e2) {
            SeckillExecution execution = new SeckillExecution(seckillId, SeckillStatEnum.END);
            return new SeckillResult<>(false, execution);
        } catch (Exception e) {
            SeckillExecution execution = new SeckillExecution(seckillId, SeckillStatEnum.INNER_ERROR);
            return new SeckillResult<>(false, execution);
        }
    }

    /**
     * 获取系统时间
     *
     * @return
     */
    @RequestMapping(value = "/time/now", method = RequestMethod.GET)
    @ResponseBody
    public SeckillResult<Long> time() {
        Date now = new Date();
        return new SeckillResult<>(true, now.getTime());
    }

    @RequestMapping(value = "/hello/{name}", method = RequestMethod.POST)
    @ResponseBody
    public String hello(@PathVariable("name") String name, String age) {
        return hello;
    }
}
