package com.rx.ext.utils;

import java.util.HashMap;
import java.util.Map;

public class ExtFieldHelper {
	
	
	static Map<String,String> modelTypes = new HashMap<String,String>(){
	private static final long serialVersionUID = 1L;
	{
			put("java.lang.Object","auto");
			put("java.lang.String","string");
			put("java.lang.Integer","integer");
			put("java.lang.Float","number");
			put("java.lang.Boolean","bool");
			put("java.util.Date","date");
			put("long","integer");
	}
	};
	
	public static void register(String className, String type) {
	modelTypes.put(className, type);
	}
	public static void register(Class<?> e) {
	modelTypes.put(e.getClass().getName(),"enum");
	}
	
	
	public static String getTypeName(Class<?> clazz){
	String className = clazz.getName();
	String type = modelTypes.get(className);
	return type == null?"auto":type;
	}
	
	
	
	/*
	
	
	
	
	
	static List<SysPermissionVO> list = new ArrayList<SysPermissionVO>();
	
	public static String find(String key, int tag) {
	for (PermissionEumn pe : PermissionEumn.values()) {
			if (pe.value.equals(key)) {
				switch (tag) {
				case 0 : return pe.name();
				case 1 : return pe.component;
				case 2 : return pe.desc;
				default : break;
				}
			}
	}
	return null;
	}
	public static String findName(String key){
	for(PermissionEumn pe :PermissionEumn.values()){
			if(pe.value.equals(key)) return pe.name();
	}
	return null;
	}
	
	public static String findComponent(String key){
	for(PermissionEumn pe :PermissionEumn.values()){
			if(pe.value.equals(key)) return pe.component();
	}
	return null;
	}
	
	public static String findDesc(String key){
	for(PermissionEumn pe :PermissionEumn.values()){
			if(pe.value.equals(key)) return pe.desc();
	}
	return null;
	}
	
	public static List<SysPermissionVO> getAllPermission(){
	if(list.size()>0) return list;
	for(PermissionEumn pe :PermissionEumn.values()){
			SysPermissionVO vo = new SysPermissionVO();
			vo.setResAddDele(0);
			vo.setGroupName(pe.component);
			vo.setResId(pe.value);
			vo.setResName(pe.name());
			vo.setResDesc(pe.desc);
			list.add(vo);
	}
	return list;
	}
	
	public static boolean hasPermission(PermissionEumn permission) throws Exception{
	List<SysPermissionVO> list = null;
	Object prs = AppContextHelper.getRequest().getSession().getAttribute("userPermissions");
	if(prs == null){
			ISysPermissionService sysPermissionService = AppContextHelper.getBean(ISysPermissionService.class);
			list = sysPermissionService.listMyPermission(RxUser.getUserId());
			AppContextHelper.getRequest().getSession().setAttribute("userPermissions", list);
	}else{
			list = (List<SysPermissionVO>)prs;
	}
	if(list == null) return false;
	for (SysPermissionVO sv : list) {
			if(sv.getResId().equals(permission.value)) return true;
	}
	return false;
	}
	*/
}
