package com.jt.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD) 		//注解修饰范围
@Retention(RetentionPolicy.RUNTIME) //作用范围
public @interface Cache_Find {
	//key null自动生成一个动态的key, !null使用用户自己定义的
	String key() default "";
	int seconds() default 0; //0表示数据不过期 
}



