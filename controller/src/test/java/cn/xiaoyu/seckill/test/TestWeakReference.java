package cn.xiaoyu.seckill.test;

import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AopConfigException;
import org.springframework.aop.framework.AopProxy;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * @author Roin_zhang
 * @date 2018/4/12 20:17
 */
public class TestWeakReference {
    public static void main(String args[]) throws InterruptedException {

        SoftReference<Object> softReference = new SoftReference<Object>(new Object());
        System.out.println(softReference.get());    // 匿名对象存在

        System.gc();
        System.out.println(softReference.get());    // 匿名对象依然存在


        WeakReference<Object> weakReference = new WeakReference<Object>(new Object());
        System.out.println(weakReference.get());
        // 进行一次gc处理
        System.gc();
        System.out.println(weakReference.get());
    }


}
