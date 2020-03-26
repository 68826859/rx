package com.rx.ext.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.rx.base.enm.RxDatePattern;
import com.rx.ext.enums.BooleanValue;
import com.rx.ext.enums.FormFieldVType;
import com.rx.ext.enums.FormInputType;
import com.rx.ext.enums.NullEnum;
import com.rx.ext.form.field.Base;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExtFormField{
	
	public static final String NULL = "";
	
	public static final String default_labelSeparator = ":";
	
	public int ordinal() default 0;//排序，越大越靠前
	public String label() default NULL;//Base
	public String name() default NULL;//Base
	public String itemId() default NULL;//Component
	
	//public String ct() default NULL;//所属容器
	
	public String emptyText() default NULL;//Base
	
	public int labelWidth() default 0;
	public boolean hideLabel() default false;
	public int width() default 0;
	public int height() default 0;
	
	public String labelSeparator() default default_labelSeparator; //Base
	
	public FormInputType inputType() default FormInputType.文本;//输入类型 Base
	
	public FormFieldVType vType() default FormFieldVType.无;
	
	
	public boolean allowBlank() default true;//是否允许为空 Text
	
	public BooleanValue editable() default BooleanValue.NULL;//是否可编辑 Text
	
	public boolean readOnly() default false;//是否只读  Base,Text
	
	public String cellCls() default NULL;//for layout table
	
	public Class<? extends Enum<?>> em() default NullEnum.class;//枚举类
	
	public Class<? extends Base> comp() default Base.class;//Base
	
	public String[] args() default {};//给到组件的参数
	
	public RxDatePattern datePattern() default RxDatePattern.NULL;
	
	public ExtConfig[] config() default {};//其它属性
	
	
	public boolean allowDecimals() default true;//如果是number控件，是否允许有小数位
	
	public float maxValue() default Float.MAX_VALUE;//如果是number控件，允许输入的最大值
	
	public float minValue() default Float.MIN_VALUE;//如果是number控件，允许输入的最小值
	
	public float step() default 1;//如果是number控件，每按一次右边按钮的步进大小
	
	public int decimalPrecision() default 2;  //如果是number控件，小数点位数
}

