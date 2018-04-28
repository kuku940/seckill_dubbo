package cn.xiaoyu.seckill.java8.demo;

import java.util.ArrayList;
import java.util.List;

/**
 * 方法引用 - 方法参考可以指向下列类型
 * 静态方法
 * 实例方法
 * 使用new运算符的构造函数
 *
 * @author Roin_zhang
 * @date 2018/4/27 14:36
 */
public class MethodRef {
    public static void main(String[] args) {
        List<String> names = new ArrayList();
        names.add("zhao");
        names.add("qian");
        names.add("sun");
        names.add("li");
        names.add("zhou");
        // 实例方法
        names.forEach(System.out::println);

        // 静态方法
        names.forEach(MethodRef::sayHello);

        // 构造方法
        names.forEach(Person::new);
    }

    private static void sayHello(String msg) {
        System.out.println("Hello " + msg);
    }
}

class Person {
    private String lastName;

    public Person(String lastName) {
        this.lastName = lastName;
        this.toString();
    }

    @Override
    public String toString() {
        return "Person{" +
                "lastName='" + lastName + '\'' +
                '}';
    }
}
