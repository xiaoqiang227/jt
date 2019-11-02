package com.jt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	//测试伪静态 
	@RequestMapping("/index")
	public String index() {
		return "index";
	}
}
