package com.jt.dubbo.service;

import com.jt.pojo.User;

public interface DubboUserService {

	void saveUser(User user);

	String doLogin(User user,String ip);
	
}
