package com.rx.ext.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.rx.ext.CompApplyer;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.FIELD})
public @interface ExtComp {
	public static String NULL = "";
	public String id() default NULL;
	public int ordinal() default 0;//排序，越大越靠前
	public String itemId() default NULL;
    public DockEnum dock() default DockEnum.NULL;
    
    public String margin() default NULL;
    public String padding() default NULL;
    public int flex() default 0;
    
	public String anchor() default NULL;//for layout anchor
	public int rowspan() default 0;//for layout table
	public int colspan() default 0;//for layout table
    
    
    public String ct() default NULL;//所属容器
    
    
    public Class<? extends CompApplyer>[] applyer() default {};//组件装配者
    
    public enum DockEnum{
    	NULL,top,bottom,left,right;
    }
}



