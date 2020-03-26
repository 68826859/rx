package com.rx.ext.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.rx.base.enm.RxDatePattern;
import com.rx.ext.enums.NullEnum;
import com.rx.ext.grid.column.Column;


	@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExtGridColumn {
	
	public static final String NULL = "";
	
	public static final int default_width = 150;
	
	public int ordinal() default 0;//排序，越大越靠前
	
	public String itemId() default NULL;//itemId
	
	public String text() default NULL;
	public String dataIndex() default NULL;
	
	public Class<? extends Enum<?>> em() default NullEnum.class;
	
	public Class<? extends Column> type() default Column.class;
	
	public String[] args() default NULL;//传递的参数
	
	public boolean hidden() default false;//是否隐藏
	public boolean sortable() default false;//可否排序
	public boolean draggable() default true;
	public boolean enableColumnHide() default false;
	public boolean menuDisabled() default true;
	
	public int flex() default 0;
	
	public int width() default default_width;
	public int minWidth() default default_width;
	
	public RxDatePattern datePattern() default RxDatePattern.NULL;
	
	public ExtConfig[] config() default {};//其它属性
	}

