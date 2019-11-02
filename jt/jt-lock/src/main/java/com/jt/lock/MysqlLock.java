package com.jt.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.jt.mapper.MysqlMapper;
@Service("mysqlLock")
public class MysqlLock implements Lock{

	@Autowired
	private MysqlMapper mapper;
	
	//表示尝试加锁
	@Override
	public boolean tryLock() {
		try {
			mapper.insert();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	//表示加锁机制
	@Override
	public void lock() {
		if(tryLock()) {
			//表示加锁成功
			return;
		}
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		lock();

	}

	@Override
	public void unlock() {
		
		mapper.delete();
	}
	@Override
	public void lockInterruptibly() throws InterruptedException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Condition newCondition() {
		// TODO Auto-generated method stub
		return null;
	}
}
