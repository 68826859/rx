package com.rx.ext;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public interface CompApplyer {
	public void applyAnnotation(Component comp,Annotation annotation, Field field, Object value) throws Exception;
}
