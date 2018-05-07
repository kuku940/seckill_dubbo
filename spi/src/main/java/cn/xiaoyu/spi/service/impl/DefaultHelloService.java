package cn.xiaoyu.spi.service.impl;

import cn.xiaoyu.spi.service.HelloService;

/**
 * @author Roin_zhang
 * @date 2018/4/28 23:33
 */
public class DefaultHelloService implements HelloService {
    @Override
    public void sayHello() {
        System.out.println("default say hello!");
    }
}
