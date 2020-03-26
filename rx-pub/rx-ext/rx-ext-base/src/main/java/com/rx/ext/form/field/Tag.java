package com.rx.ext.form.field;

import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtConfig;
import com.rx.ext.data.Store;
	@ExtClass(alias="widget.tagfield")
public class Tag extends ComboBox{
	
	public Tag(){
		
	}
	public Tag(Store<?> store){
		this.setStore(store);
	}
	
	@ExtConfig()
	private Boolean encodeSubmitValue;

	public Boolean getEncodeSubmitValue() {
		return encodeSubmitValue;
	}
	public void setEncodeSubmitValue(Boolean encodeSubmitValue) {
		this.encodeSubmitValue = encodeSubmitValue;
	}
	
	
	
}
