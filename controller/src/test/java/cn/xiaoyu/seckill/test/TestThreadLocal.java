package cn.xiaoyu.seckill.test;

public class TestThreadLocal {
	public static void main(String[] args) {
		MyThread thread = new MyThread();
		Thread t1 = new Thread(thread);
		Thread t2 = new Thread(thread);

		t1.start();
		t2.start();
	}
}

class MyThread implements Runnable {
	private ThreadLocal<Long> longThreadLocal = new ThreadLocal<Long>() {
		@Override
		protected Long initialValue() {
			return Thread.currentThread().getId();
		}
	};
	private ThreadLocal<String> strThreadLocal = new ThreadLocal<String>() {
		@Override
		protected String initialValue() {
			return Thread.currentThread().getName();
	    }
	};

	@Override
	public void run() {
		System.out.println(longThreadLocal.get());
		System.out.println(strThreadLocal.get());
		System.out.println("------");

		// 上线通过匿名内部类覆盖initalValue方法，所以这个可以不用使用set方法
		longThreadLocal.set(Math.round(Math.random() * 100));
		strThreadLocal.set(Thread.currentThread().getName());

		System.out.println(longThreadLocal.get());
		System.out.println(strThreadLocal.get());
		System.out.println("------");
	}
}