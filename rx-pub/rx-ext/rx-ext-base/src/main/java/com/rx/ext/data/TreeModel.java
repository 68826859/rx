package com.rx.ext.data;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import com.rx.ext.annotation.ExtTreeField;
import com.rx.ext.enums.TreeField;

public class TreeModel extends Model {
	@Override
	public void applyAnnotation(Annotation annotation,Field field,Object value) throws Exception{
	if(annotation instanceof ExtTreeField){
			ExtTreeField modelField = (ExtTreeField)annotation;
			if(modelField.treeField() != TreeField.NULL){
				TreeField tf = modelField.treeField();
				Class<? extends com.rx.ext.data.field.Field> cls = tf.getClazz();
				if(tf == TreeField.parentId){
					cls = Model.getDefaultFieldType(field.getType());
				}
				com.rx.ext.data.field.Field f = cls.newInstance();
				f.applyAnnotation(modelField,field,value);
				f.setMapping(field.getName());
				f.setName(tf.name());
				this.addField(f);
			}
	}
	super.applyAnnotation(annotation, field, value);
	}
}
