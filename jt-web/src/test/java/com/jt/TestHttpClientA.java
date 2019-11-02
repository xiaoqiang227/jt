package com.jt;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jt.util.HttpClientService;

@SpringBootTest
@RunWith(SpringRunner.class)//spring容器启动
public class TestHttpClientA {
	
	@Autowired
	private HttpClientService service;
	
	@Test
	public void testJedis() {
		String url = "http://www.baidu.com";
		Map<String,String> parmas = new HashMap<>();
		parmas.put("id", "10086");
		parmas.put("name","大吉大利,不配你玩了!!");
		String result = service.doGet(url, parmas, null);
		System.out.println(result);
		
	}
}
