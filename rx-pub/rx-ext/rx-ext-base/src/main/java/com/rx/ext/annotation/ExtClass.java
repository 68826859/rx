package com.rx.ext.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import com.rx.ext.Base;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface ExtClass{
	public static String NULL = "";
	//public String xtype() default NULL;//类型名
	public String alias() default NULL;//别名
	public String alter() default NULL;//JS别称
	public String[] alternateClassName() default {};//取个别名
	public Class<? extends Base> extend() default Base.class;
	public String[] requires() default {};
	public Class<? extends Base>[] uses() default {};
	public Class<? extends Base>[] mixins() default {};
	public boolean singleton() default false;
}

