package com.rx.ext.form.field;

import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtConfig;
import com.rx.ext.data.Model;
import com.rx.ext.data.Store;
	@ExtClass(alias="widget.combo")
public class ComboBox extends Picker{
	
	public ComboBox(){
		
	}
	public ComboBox(Store<?> store){
		this.setStore(store);
	}
	
	@ExtConfig()
	public Integer pageSize;
	
	@ExtConfig()
	private Boolean autoLoadOnValue;
	
	
	@ExtConfig()
	private Store<?> store;
	
	public Store<?> getStore() {
		return store;
	}
	public void setStore(Store<?> store) {
		if(store != null){
				store.setAutoLoad(Boolean.TRUE);
				if(store.getModel() != null){
					Model md = null;
					try {
						md = (Model)Base.forClass(store.getModel());
					} catch (Exception e) {
						e.printStackTrace();
					}
					this.setValueField(md.getIdProperty());
					this.setDisplayField(md.getDisplayProperty());
				}
		}
		this.store = store;
	}
	
	
	@ExtConfig()
	private String displayField;
	
	@ExtConfig()
	private String valueField;
	
	
	public String getDisplayField() {
		return displayField;
	}
	public void setDisplayField(String displayField) {
		this.displayField = displayField;
	}
	public String getValueField() {
		return valueField;
	}
	public void setValueField(String valueField) {
		this.valueField = valueField;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Boolean getAutoLoadOnValue() {
		return autoLoadOnValue;
	}
	public void setAutoLoadOnValue(Boolean autoLoadOnValue) {
		this.autoLoadOnValue = autoLoadOnValue;
	}
}
