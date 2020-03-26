package com.rx.web.user;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface RxAnUserSession{
	public static String NULL = "";
	
	public String cookieKey() default NULL;//
	
	public String tokenKey() default NULL;//
	
	public long httpTimeout() default 0;//
	
	public int cookieTimeout() default 0;// 单位秒   默认一天  60 * 60 * 24 
	
	public long tokenTimeout() default 0;//
	
	public boolean sso() default false;
	
	public boolean userThreadCache() default true;
	
	public String attribute() default NULL;//用户登录状态存储在session上的属性名
}

