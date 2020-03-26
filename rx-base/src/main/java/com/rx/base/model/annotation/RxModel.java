package com.rx.base.model.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import com.rx.base.model.RxModelApplyer;
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface RxModel {
	public static final String NULL = "";
	
	public String name() default NULL;
	public String text() default NULL;//文本
    public String referColAlias() default NULL;
    
    
    public Class<? extends RxModelApplyer>[] applyer() default {};
	
	public int tag() default 0;
	
	public RxConfig[] cfg() default {};//参数
    
}


	
