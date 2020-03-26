package com.rx.ext.ux;

import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtConfig;
import com.rx.ext.data.Model;
import com.rx.ext.data.Store;
import com.rx.ext.form.field.Base;

@ExtClass(alias="widget.treepicker")
public class TreePicker extends com.rx.ext.form.field.Picker{
	
	@ExtConfig
	private Store store;
	
	@ExtConfig()
	private String displayField;
	
	@ExtConfig()
	private Integer minPickerHeight;
	
	
	public Store getStore() {
	return store;
	}
	public void setStore(Store store) {
	if(store != null){
			store.setAutoLoad(Boolean.TRUE);
			if(this.displayField == null && store.getModel() != null){
				Model md = null;
				try {
					md = (Model)Base.forClass(store.getModel());
				} catch (Exception e) {
					e.printStackTrace();
				}
				this.setDisplayField(md.getDisplayProperty());
			}
	}
	this.store = store;
	}
	public String getDisplayField() {
	return displayField;
	}
	public void setDisplayField(String displayField) {
	this.displayField = displayField;
	}
	public Integer getMinPickerHeight() {
		return minPickerHeight;
	}
	public void setMinPickerHeight(Integer minPickerHeight) {
		this.minPickerHeight = minPickerHeight;
	}
	
	}
