package cn.xiaoyu.seckill.test;

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Semaphore;

/**
 * 控制10内某资源只能被调用100次，10秒时间是动态的
 *
 * @author Roin_zhang
 * @date 2018/5/9 19:24
 */
public class ServiceTest {
    private int maxUsedLimited = 100;
    private int timeLimited = 10000; // ms
    private Semaphore m_semaphore = new Semaphore(maxUsedLimited * 1000 / timeLimited);

    ConcurrentLinkedDeque<Long> staticticsInfo = new ConcurrentLinkedDeque<Long>();

    public synchronized Object consume() {
        if (staticticsInfo.size() >= maxUsedLimited) {
            long ti = staticticsInfo.getLast() - staticticsInfo.getFirst();
            if (ti < timeLimited) {
                System.out.println("资源访问受限，当前已在" + timeLimited + "ms内访问了" + staticticsInfo.size() + "次资源");
                safeSleep(timeLimited - ti);
            }
            staticticsInfo.removeFirst();
        }

        try {
            m_semaphore.acquire();
        } catch (Exception e) {
            e.printStackTrace();
        }
        staticticsInfo.add(System.currentTimeMillis());
        return null;
    }

    private void safeSleep(long l) {
        try {
            Thread.sleep(l);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}

class MyTestThread extends Thread {
    private ServiceTest serviceTest = null;

    public MyTestThread(ServiceTest serviceTest) {
        this.serviceTest = serviceTest;
    }

    @Override
    public void run() {
        long i = 0;

    }
}