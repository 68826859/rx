package com.rx.ext.form;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtConfig;
import com.rx.ext.annotation.ExtFormField;
import com.rx.ext.container.Container;
import com.rx.ext.layout.Layout;
@ExtClass(alias="widget.fieldset")
public class FieldSet extends Container{
	
	public FieldSet(){
		
	}
	public FieldSet(String title){
		this.title = title;
	}
	public FieldSet(String title,String layout){
		this.title = title;
		this.setLayout(layout);
	}
	public FieldSet(String title,Layout layout){
		this.title = title;
		this.setLayout(layout);
	}
	public FieldSet(Layout layout){
		this.setLayout(layout);
	}
	
	@ExtConfig
	protected Boolean checkboxToggle;
	
	@ExtConfig
	protected Boolean collapsible;
	
	@ExtConfig
	protected String descriptionText;
	
	@ExtConfig
	protected String title;
	
	
	
	@Override
	public void applyAnnotation(Annotation annotation,Field field,Object value) throws Exception{
		if(annotation instanceof ExtFormField){
			
		}else{
			super.applyAnnotation(annotation,field,value);
		}
	}



	public Boolean getCheckboxToggle() {
		return checkboxToggle;
	}



	public void setCheckboxToggle(Boolean checkboxToggle) {
		this.checkboxToggle = checkboxToggle;
	}



	public Boolean getCollapsible() {
		return collapsible;
	}



	public FieldSet setCollapsible(Boolean collapsible) {
		this.collapsible = collapsible;
		return this;
	}



	public String getDescriptionText() {
		return descriptionText;
	}



	public FieldSet setDescriptionText(String descriptionText) {
		this.descriptionText = descriptionText;
		return this;
	}



	public String getTitle() {
		return title;
	}



	public FieldSet setTitle(String title) {
		this.title = title;
		return this;
	}

}
