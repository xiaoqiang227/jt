package com.jt.thro;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.jt.vo.SysResult;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice 	//对Controller层异常有效
@Slf4j	//引入日志
public class SystemException {
	
	//只对运行时异常有效
	@ExceptionHandler(RuntimeException.class)
	public SysResult exception(Throwable throwable) {
		throwable.printStackTrace();
		log.info(throwable.getMessage());
		return SysResult.fail("调用失败!!!!");
	}
}
