package cn.xiaoyu.seckill.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 如何保证三个线程依照顺序执行 - 信号量Semaphore
 *
 * @author Roin_zhang
 * @date 2018/4/14 1:23
 */
public class TestThreadABC_03 {
    public static void main(String[] args) throws InterruptedException {
        // 采用新特性来启动和管理线程——内部使用线程池
        final Semaphore semp = new Semaphore(1);
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(new ThreadA(semp));
        exec.execute(new ThreadB(semp));
        exec.execute(new ThreadC(semp));

        // 关闭线程池
        exec.shutdown();
    }
}

class ThreadA implements Runnable {
    private Semaphore semp;

    public ThreadA(Semaphore semp) {
        this.semp = semp;
    }

    @Override
    public void run() {
        try {
            semp.acquire();
            Thread.sleep(500);
            System.out.println(Thread.currentThread().getName() + " run A");
            semp.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class ThreadB implements Runnable {
    private Semaphore semp;

    public ThreadB(Semaphore semp) {
        this.semp = semp;
    }

    @Override
    public void run() {
        try {
            semp.acquire();
            Thread.sleep(500);
            System.out.println(Thread.currentThread().getName() + " run B");
            semp.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class ThreadC implements Runnable {
    private Semaphore semp;

    public ThreadC(Semaphore semp) {
        this.semp = semp;
    }

    @Override
    public void run() {
        try {
            semp.acquire();
            Thread.sleep(500);
            System.out.println(Thread.currentThread().getName() + " run C");
            semp.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

