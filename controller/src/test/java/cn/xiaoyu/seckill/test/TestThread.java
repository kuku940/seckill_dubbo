package cn.xiaoyu.seckill.test;

import java.util.ArrayList;
import java.util.concurrent.*;

/**
 * 启动5个线程，分别计算0-199,200-399,400-599,600-799,800-999的余数和
 * 然后统计五个线程的和并返回
 *
 * @author Roin_zhang
 * @date 2018/5/5 22:22
 */
public class TestThread {
    private int state = 0;

    public static void main(String args[]) throws ExecutionException, InterruptedException {
        ExecutorService pool = Executors.newCachedThreadPool();
        ArrayList<FutureTask> list = new ArrayList<>();
        TestThread testThread = new TestThread();

        for (int i = 0; i < 5; i++) {
            Task task = new Task(testThread);
            FutureTask<Double> futureTask = new FutureTask<>(task);
            pool.execute(futureTask);
            list.add(futureTask);
        }

        double sum = 0d;
        for (FutureTask task : list) {
            sum += (double) task.get();
        }
        System.out.println("--:" + sum);
    }

    public Double sum() throws InterruptedException {
        long startValue = 0;
        long endValue = 10;
        synchronized (TestThread.class) {
            startValue += state * 10;
            endValue += state * 10;
            state++;
        }
        Thread.sleep(1000);
        double sum = 0d;
        for (long i = startValue; i < endValue; i++) {
            sum += i;
        }
        return sum;
    }

}

class Task implements Callable<Double> {
    private TestThread testThread;

    public Task(TestThread testThread) {
        this.testThread = testThread;
    }

    @Override
    public Double call() throws Exception {
        return testThread.sum();
    }
}
