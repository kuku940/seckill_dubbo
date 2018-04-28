package cn.xiaoyu.seckill.java8.demo;

/**
 * Lambda 表达式
 * parameter -> expression body
 * <p>
 * 可选类型声明 - 无需声明参数的类型，编译器可以从该参数的值推断
 * 可选圆括号参数 - 无需在括号中声明参数，对于多个参数，括号是必需的
 * 可选大括号 - 表达式主题没有必要使用大括号，如果主题中含有一个单独的语句
 * 可选return关键字 - 编译器会自动返回值，如果主题有一个表达式返回的值，花括号是必须的，以表明表达式返回一个值
 *
 * @author Roin_zhang
 * @date 2018/4/27 13:55
 */
public class LambdaTest {
    public static void main(String[] args) {
        LambdaTest java8Test = new LambdaTest();

        int[] arr = {1, 2, 23, 4, 4};


        MathOperation add = (int a, int b) -> a + b;
        MathOperation sub = (a, b) -> a - b;

        MathOperation mult = (int a, int b) -> {
            return a * b;
        };
        MathOperation div = (int a, int b) -> a / b;

        System.out.println("10 + 5 = " + java8Test.operate(10, 5, add));
        System.out.println("10 - 5 = " + java8Test.operate(10, 5, sub));
        System.out.println("10 * 5 = " + java8Test.operate(10, 5, mult));
        System.out.println("10 / 5 = " + java8Test.operate(10, 5, div));

        GreetingService greetingService1 = message -> System.out.println("hello " + message);
        GreetingService greetingService2 = (message) -> System.out.println("hello " + message);

        greetingService1.sayMessage("Java!");
        greetingService2.sayMessage("World!");
    }

    interface MathOperation {
        int operation(int a, int b);
    }

    interface GreetingService {
        void sayMessage(String message);
    }

    private int operate(int a, int b, MathOperation mathOperation) {
        return mathOperation.operation(a, b);
    }
}
