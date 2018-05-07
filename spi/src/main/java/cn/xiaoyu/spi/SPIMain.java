package cn.xiaoyu.spi;

import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.alibaba.dubbo.rpc.Protocol;

/**
 * @author Roin_zhang
 * @date 2018/4/28 23:53
 */
public class SPIMain {
    public static void main(String[] args) {
        ExtensionLoader<Protocol> protocolLoader = ExtensionLoader.getExtensionLoader(Protocol.class);
        Protocol protocol = protocolLoader.getAdaptiveExtension();
        System.out.println(protocol);

//        ServiceLoader<HelloService> services = ServiceLoader.load(HelloService.class);
//
//        for (HelloService service : services) {
//            service.sayHello();
//        }
    }
}
