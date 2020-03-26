package com.rx.ext.data;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import com.rx.ext.annotation.ExtConfig;
import com.rx.ext.data.proxy.Proxy;
	public class ProxyStore<T> extends AbstractStore{
	
	
	@ExtConfig()
	private Proxy proxy;
	
	@ExtConfig()
	private Boolean autoLoad;
	
	@ExtConfig()
	private List<com.rx.ext.data.field.Field> fields;
	
	@ExtConfig()
	private Class<? extends T> model;
	
	public ProxyStore(){
	Type type = this.getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] ptype = ((ParameterizedType) type).getActualTypeArguments();
            //Class.class.isAssignableFrom(ptype[0]);
            //this.setModel((Class<? extends T>)ptype[0]);
        }
	}
	public ProxyStore(Class<? extends T> model,Proxy proxy){
	this.setProxy(proxy);
	this.setModel(model);
	}
	
	
	public List<com.rx.ext.data.field.Field> getFields() {
	if(this.fields == null){
			this.fields = new ArrayList<com.rx.ext.data.field.Field>();
	}
	return this.fields;
	}
	public void setFields(List<com.rx.ext.data.field.Field> fields) {
	this.fields = fields;
	}
	public void addField(com.rx.ext.data.field.Field field) {
	this.getFields().add(field);
	}
	
	public Class<? extends T> getModel() {
	return model;
	}
	public void setModel(Class<? extends T> model) {
	this.model = model;
	}
	public Proxy getProxy() {
	return proxy;
	}
	public void setProxy(Proxy proxy) {
	this.proxy = proxy;
	}
	public Boolean getAutoLoad() {
	return autoLoad;
	}
	public void setAutoLoad(Boolean autoLoad) {
	this.autoLoad = autoLoad;
	}
}
