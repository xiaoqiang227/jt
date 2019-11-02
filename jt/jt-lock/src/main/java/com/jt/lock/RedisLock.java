package com.jt.lock;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.jt.mapper.MysqlMapper;

import redis.clients.jedis.JedisCluster;
@Component("redisLock")
public class RedisLock implements Lock{
	
	@Autowired
	private JedisCluster jedisCluster;
	private ThreadLocal<String> thread = new ThreadLocal<>();
	private static final String key = "JT_LOCK";
	//表示尝试加锁
	@Override
	public boolean tryLock() {
		try {
			String value = UUID.randomUUID().toString();
			String result = jedisCluster.set(key, value, "NX", "PX", 200);
			if(StringUtils.isEmpty(result) || !result.equalsIgnoreCase("ok")) {
				return false;
			}
			thread.set(value);
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
			e.printStackTrace();
		}
		lock();
	}

	@Override
	public void unlock() {
		String	uuid = thread.get();
		String  value = jedisCluster.get(key);
		if(!StringUtils.isEmpty(value) & uuid.equals(value)) {
			jedisCluster.del(key);
		}
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
