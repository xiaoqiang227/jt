package com.jt;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

//模拟窗口卖票业务
@SpringBootTest
@RunWith(SpringRunner.class)
public class MysqlLockTest{
	private int num = 100;
	@Autowired
	@Qualifier("mysqlLock")
	private Lock lock;

	class window implements Runnable {
		//定义线程卖票操作
		@Override
		public void run() {
			while(true) {
				try {
					lock.lock();
					if(num>0) {
						System.out.println(Thread.currentThread().getName()+"卖出第"+(101-num)+"张票");
						num--;
						Thread.sleep(100);
					}else {
						break;//跳出循环
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					lock.unlock();
				}
			}
		}
	}


	@Test
	public void test01() {
		Runnable window = new window();
		Thread thread1 = new Thread(window, "窗口A");
		Thread thread2 = new Thread(window, "窗口B");
		Thread thread3 = new Thread(window, "窗口C");
		thread1.start();
		thread2.start();
		thread3.start();
		for(;;); //防止线程提前结束
	}

}
