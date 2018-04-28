package cn.xiaoyu.seckill.java8.demo;


import java.util.Base64;

/**
 * 默认方法
 *
 * @author Roin_zhang
 * @date 2018/4/27 14:47
 */
public class DefaultMethod {
    public static void main(String[] args) {
        Vehicle vehicle = new DefaultMethod().new Car();
        vehicle.print();

        // Base64加密
        String encodeToString = Base64.getEncoder().encodeToString("kingdee china".getBytes());
        System.out.println(encodeToString);

        System.out.println("hello World!!!!".hashCode());
        String str = new String("hello World!!!!");
        System.out.println(str.hashCode());
    }

    interface Vehicle {
        default void print() {
            System.out.println("I am a vehicle!");
        }

        static void blowHorn() {
            System.out.println("Blowing horn!");
        }
    }

    interface FourWheeler {
        default void print() {
            System.out.println("I am a four wheeler!");
        }
    }

    class Car implements Vehicle, FourWheeler {

        @Override
        public void print() {
            Vehicle.super.print();
            FourWheeler.super.print();

            Vehicle.blowHorn();
            System.out.println("I'm a car!");
        }
    }
}
