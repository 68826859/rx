package com.rx.extrx.widget;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtFormField;
import com.rx.ext.data.Store;
import com.rx.ext.data.proxy.Proxy;
import com.rx.ext.data.reader.Reader;
import com.rx.ext.form.field.ComboBox;

@ExtClass(alias="widget.formcombox",alter="Rx.widget.FormComBox")
public class FormCombox extends ComboBox{
	public void setStore(Store<?> store) {
		super.setStore(store);
		if(store != null){
			store.setAutoLoad(Boolean.FALSE);
		}
	}
	
	@Override
	public void applyAnnotation(Annotation annotation,Field field,Object value) throws Exception{
		
		if(annotation instanceof ExtFormField){
			String[] args = ((ExtFormField) annotation).args();
			if(args.length > 0){
				this.pageSize = Integer.parseInt(args[0]);
				Store<?> sto = this.getStore();
				if(sto != null) {
					sto.pageSize = this.pageSize;
					
					/*
					Proxy pr = sto.getProxy();
					if(pr != null) {
						Reader rd = pr.getReader();
						if(rd != null) {
							rd.setTotalProperty("data.totalRows");
							rd.setRootProperty("data.pageData");
						}
					}
					*/
				}
			}else {
				this.editable = Boolean.FALSE;
			}
		}
		super.applyAnnotation(annotation,field,value);
	}
}
