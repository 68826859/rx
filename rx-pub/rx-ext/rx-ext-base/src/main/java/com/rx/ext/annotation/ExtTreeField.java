package com.rx.ext.annotation;

import com.rx.ext.enums.TreeField;

import java.lang.annotation.*;




@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExtTreeField {
	public TreeField treeField() default TreeField.NULL;
	
}

