package com.jt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {
	
	/**
	 * restFul风格
	 * 	语法:
	 * 		1.参数必须使用/分割
	 *      2.参数使用{}号包裹
	 *      3.使用@PathVariable注解修饰
	 *  规则:
	 *  	如果参数名称与对象的属性一致 可以直接
	 *  	使用对象接收
	 *  
	 *  高级应用:
	 *  	例子1:userById/100  查询id=100查询
	 *  	例子2:userById/100 删除id=100的数据
	 *  
	 *  请求方式4种:
	 *  	get	  	查询操作
	 *  	post 	入库操作
	 *  	put  	更新操作
	 *  	delete  删除操作
	 *  
	 * @param moduleName
	 * @return
	 */
	@RequestMapping("/page/{moduleName}")
	public String module(@PathVariable String moduleName) {
		
		return moduleName;
	}
	
	//查询操作
	/*
	 * @GetMapping("/userById/{id}") public xxxx findUserById(@PathVariable Integer
	 * id) {
	 * 
	 * }
	 */
	
	//删除操作
	//@RequestMapping(value = "/userById/{id}",
			//method = RequestMethod.DELETE)
	/*
	 * @DeleteMapping("/userById/{id}") public xxxx deleteUser(@PathVariable Integer
	 * id) {
	 * 
	 * }
	 */
	
	
	
	//url:localhost:8091/addUser/100/tomcat/18/男
	/*
	 * @RequestMapping("/addUser/{id}/{name}/{age}/{sex}") public XXXX addUser(User
	 * user) {
	 * 
	 * }
	 */
}
