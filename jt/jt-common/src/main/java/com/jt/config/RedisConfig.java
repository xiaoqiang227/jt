package com.jt.config;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;

//标识配置类
@Configuration
//引入配置文件
@PropertySource("classpath:/properties/redis.properties")
public class RedisConfig {

	@Value("${redis.nodes}")
	private String nodes; //node,node,node

	
	/**
	 * 配置redis集群
	 * 
	 */
	@Bean
	public JedisCluster jedisCluster() {
		Set<HostAndPort> nodeSet = new HashSet<HostAndPort>();
		String[] arrayNodes = nodes.split(",");
		for (String node : arrayNodes) { //node=host:port
			String host = node.split(":")[0];
			int port = Integer.parseInt(node.split(":")[1]);
			nodeSet.add(new HostAndPort(host, port));
		}
		return new JedisCluster(nodeSet);
	}
	
	
	
	
	
	
	
	
	
	
	/**
	 * 	配置哨兵
	 */
	
	//@Autowired
	//private JedisSentinelPool sentinelPool;
	/*
	 * @Bean
	 * 
	 * @Scope("prototype") //多例对象 public Jedis jedis(JedisSentinelPool
	 * sentinelPool){
	 * 
	 * return sentinelPool.getResource(); }
	 */
	
	/*
	 * @Bean //单例 public JedisSentinelPool jedisSentinelPool() { Set<String>
	 * sentinels = new HashSet<>(); sentinels.add(nodes); JedisSentinelPool
	 * sentinelPool = new JedisSentinelPool("mymaster", sentinels); return
	 * sentinelPool; }
	 */


	/*
	 * @Bean public ShardedJedis shardedJedis() { List<JedisShardInfo> shards =
	 * getList(); return new ShardedJedis(shards); }
	 * 
	 * private List<JedisShardInfo> getList() { List<JedisShardInfo> list = new
	 * ArrayList<>(); String[] arrayNodes = nodes.split(","); //node=ip:port for
	 * (String node : arrayNodes) { String host = node.split(":")[0]; int port =
	 * Integer.parseInt(node.split(":")[1]); list.add(new JedisShardInfo(host,
	 * port)); } return list; }
	 */

	/*List<JedisShardInfo> list = new ArrayList<JedisShardInfo>();
	list.add(new JedisShardInfo("192.168.8.128",6379)); //
	list.add(new JedisShardInfo("192.168.8.128",6380)); //1
	list.add(new JedisShardInfo("192.168.8.128",6381)); //
	ShardedJedis jedis = new ShardedJedis(list);
	jedis.set("1906","redis分片测试");
	System.out.println(jedis.get("1906"));*/








	/*
	 * @Value("${redis.host}") private String host;
	 * 
	 * @Value("${redis.port}") private Integer port;
	 */

	/* <bean id="jedis" class="com.jt.config.Jedis" /> */
	/*
	 * @Bean public Jedis jedis() {
	 * 
	 * return new Jedis(host, port); }
	 */
}
