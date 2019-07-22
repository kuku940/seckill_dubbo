package cn.xiaoyu.seckill.controller;

import cn.xiaoyu.seckill.domain.Seckill;
import cn.xiaoyu.seckill.service.seckill.SeckillService;
import com.alibaba.dubbo.config.annotation.Reference;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

/**
 * @author Roin zhang
 * @date 2019/7/20
 */
@RestController
@RequestMapping("/swagger")
@Api(tags = "SwaggerController", value = "Restful风格CRUD")
public class SwaggerController {
    private static Logger logger = LoggerFactory.getLogger(SwaggerController.class);
    @Reference(version = "1.0.0")
    private SeckillService seckillService;

    @ApiOperation(value = "获取详情", notes = "获取详情")
    @GetMapping(value = "/{id}")
    public Seckill get(@PathVariable Long id) {
        Seckill seckill = seckillService.getById(id);
        logger.info(seckill.toString());
        return seckill;
    }

    @ApiOperation(value = "添加商品", notes = "添加商品")
    @PostMapping()
    public Seckill add(@RequestBody Seckill seckill) {
        logger.info(seckill.toString());
        return seckill;
    }

    @ApiOperation(value = "删除商品", notes = "删除商品")
    @DeleteMapping("/{id}")
    public Seckill del(@PathVariable Long id) {
        Seckill seckill = seckillService.getById(id);
        logger.info(seckill.toString());
        return seckill;
    }

    @ApiOperation(value = "更新商品", notes = "更新商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "商品Id", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "seckill", value = "商品", required = true, dataType = "Seckill")
    })
    @PutMapping("/{id}")
    public Seckill upate(@PathVariable Long id, @RequestBody Seckill seckill) {
        logger.info(seckill.toString());
        return seckill;
    }
}
