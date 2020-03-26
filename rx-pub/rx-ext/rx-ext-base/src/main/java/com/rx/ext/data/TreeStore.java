package com.rx.ext.data;

import com.rx.ext.annotation.ExtConfig;
import com.rx.ext.data.proxy.Proxy;

public class TreeStore<T> extends Store<T> {

	public TreeStore() {
	}

	public TreeStore(Class<? extends T> model, Proxy proxy) {
		super(model, proxy);
	}

	/*
	 * public void setModel(Class<? extends T> model) { this.model = model; }
	 */

	@ExtConfig
	private Boolean rootVisible;
	
	@ExtConfig
	private T root;
	
	
	@ExtConfig
	private String parentIdProperty;

	public String getParentIdProperty() {
		return parentIdProperty;
	}

	public void setParentIdProperty(String parentIdProperty) {
		this.parentIdProperty = parentIdProperty;
	}

	public T getRoot() {
		return root;
	}

	public void setRoot(T root) {
		this.root = root;
	}

	public Boolean getRootVisible() {
		return rootVisible;
	}

	public void setRootVisible(Boolean rootVisible) {
		this.rootVisible = rootVisible;
	}
}
