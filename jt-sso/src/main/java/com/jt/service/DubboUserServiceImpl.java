package com.jt.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.dubbo.service.DubboUserService;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;
import com.jt.util.ObjectMapperUtil;

import redis.clients.jedis.JedisCluster;

//编辑接口的实现类
@Service
public class DubboUserServiceImpl implements DubboUserService {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private JedisCluster jedisCluster;

	//100亿     ccc 123456
	@Override
	public void saveUser(User user) {
		//防止eamil为null报错,使用电话号码代替
		//String solt = user.getPassword()+"cn.tedu";
		//需要将密码进行加密处理
		String md5Pass = 
				DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(md5Pass)
		.setEmail(user.getPhone())
		.setCreated(new Date())
		.setUpdated(user.getCreated());
		userMapper.insert(user);
	}

	/**
	 * 1.根据用户名和密码查询数据库
	 *    结果:  没有记录  说明用户名和密码不正确 return null
	 * 2.生成ticket(加密后的秘钥),userJSON串.将数据保存到redis中.
	 * 3.返回ticket.
	 */
	/*
	 * @Override public String doLogin(User user) { User userDB =
	 * findUserByUP(user); if(userDB != null) { //1.生成秘钥 String uuid =
	 * UUID.randomUUID().toString(); String ticket =
	 * DigestUtils.md5DigestAsHex(uuid.getBytes()); //2.将某些敏感数据进行脱敏处理
	 * userDB.setPassword("**********"); //3.将user对象转化为json串 String userJSON =
	 * ObjectMapperUtil.toJSON(userDB); jedisCluster.setex(ticket,7*24*3600,
	 * userJSON); return ticket; }
	 * 
	 * return null; }
	 */

	public User findUserByUP(User user) {
		String md5Pass = 
				DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(md5Pass);
		QueryWrapper<User> queryWrapper = 
				new QueryWrapper<User>(user);
		User userDB = userMapper.selectOne(queryWrapper);
		return userDB;
	}
	
	/**
	 * 1.校验用户是否正确
	 * 2.需要将用户信息写入redis中.
	 */
	@Override
	public String doLogin(User user,String ip) {
		String md5Pass = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(md5Pass);
		QueryWrapper<User> queryWrapper = new QueryWrapper<>(user);
		User userDB = userMapper.selectOne(queryWrapper);
		if(userDB == null) {
			//说明 用户名和密码不正确
			return null;
		}
		
		//表示用户信息正确. 保存ticket/ip/userjson
		String uuid = UUID.randomUUID().toString();
		String ticket = DigestUtils.md5DigestAsHex(uuid.getBytes());
		userDB.setPassword("你猜猜!!!!!!");
		String userJSON = ObjectMapperUtil.toJSON(userDB);
		Map<String, String> hash = new HashMap<String, String>();
		hash.put("JT_TICKET", ticket);
		hash.put("JT_USERJSON", userJSON);
		hash.put("JT_IP", ip);
		jedisCluster.hmset(user.getUsername(), hash);
		jedisCluster.expire(user.getUsername(), 7*24*3600);
		return ticket;
	}
	
	
	
	
	
	
}
