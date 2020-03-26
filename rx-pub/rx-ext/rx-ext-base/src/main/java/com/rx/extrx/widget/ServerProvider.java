package com.rx.extrx.widget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rx.ext.Define;
import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtConfig;

@ExtClass(alter="Rx.widget.ServerProvider")
public class ServerProvider extends com.rx.ext.Base{
	@ExtConfig
	private String url;
	
	@ExtConfig
	private String namespace;
	
	@ExtConfig
	private List<String> permissions;
	
	@ExtConfig
	Map<String,List<ServerMethod>> actions;
	
	
	private String actionName;
	
	private List<ServerMethod> methods;
	
	
	
	public ServerProvider(){
		
	}
	
	@Override
	public void setTargetClazz(Class<?> targetClazz) {
	super.setTargetClazz(targetClazz);
	actionName = targetClazz.getSimpleName();
	String jsName = Define.getJSClazzName(targetClazz);
	if(jsName.indexOf(".") == -1){
			actionName = jsName;
			
	}
	this.setNamespace(targetClazz.getPackage().getName());
	}
	
	
	public List<String> getPermissions() {
		
	if(permissions == null){
			permissions = new ArrayList<String>();
	}
	return permissions;
	}
	
	public void addPermission(String permission){
	this.getPermissions().add(permission);
	}

	public void setPermissions(List<String> permissions) {
	this.permissions = permissions;
	}
	public String getUrl() {
	return url;
	}
	public void setUrl(String url) {
	this.url = url;
	}
	public String getNamespace() {
	return namespace;
	}
	public void setNamespace(String namespace) {
	this.namespace = namespace;
	}

	
	public void addServerMethod(ServerMethod method){
	this.getActions();
	this.getMethods().add(method);
	}
	
	public Map<String, List<ServerMethod>> getActions() {
	if(actions == null){
			actions = new HashMap<String,List<ServerMethod>>();
	}
	return actions;
	}
	public void setActions(Map<String, List<ServerMethod>> actions) {
	this.actions = actions;
	}
	public List<ServerMethod> getMethods() {
	if(methods == null){
			methods = new ArrayList<ServerMethod>();
			this.getActions().put(actionName,this.getMethods());
	}
	return methods;
	}
	public void setMethods(List<ServerMethod> methods) {
	this.methods = methods;
	}
	
	public ServerMethod getMethod(String methodName) {
	if(methods != null){
			for(ServerMethod sm:methods){
				if(sm.getName().equals(methodName)){
					return sm;
				}
			}
	}
	return null;
	}
	
	public String getActionName() {
	return actionName;
	}
	public void setActionName(String actionName) {
	this.actionName = actionName;
	}

}
