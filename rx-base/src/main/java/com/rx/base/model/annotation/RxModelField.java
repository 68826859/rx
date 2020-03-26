package com.rx.base.model.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.rx.base.enm.RxDatePattern;
import com.rx.base.enm.RxNullEnum;
import com.rx.base.model.RxModelFieldApplyer;
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface RxModelField {
	public static final String NULL = "";
	
	public String name() default NULL;
	public String text() default NULL;//文本
	public boolean isID() default false;//是否主键
	public boolean isDisplay() default false;//是否是显示域
	public boolean isParentId() default false;//是否是属性父ID
    public String referColAlias() default NULL;
    public Class<?> fk() default Object.class;//外键PO类
    public Class<? extends Enum<?>> em() default RxNullEnum.class;//枚举类
    public RxDatePattern datePattern() default RxDatePattern.NULL;
    
    public String convert() default NULL;
    
    //public ModelApplyEnum[] type() default {ModelApplyEnum.SELECT};
	
	public Class<? extends RxModelFieldApplyer>[] applyer() default {};
	
	public String tar() default "";
	
	public int tag() default 0;
	
	public RxConfig[] cfg() default {};//参数
}


	
