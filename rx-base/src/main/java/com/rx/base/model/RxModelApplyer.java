package com.rx.base.model;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rx.base.enm.EnumApplyer;
import com.rx.base.enm.RxNullEnum;
import com.rx.base.model.annotation.RxConfig;
import com.rx.base.model.annotation.RxModel;
import com.rx.base.model.annotation.RxModelField;
public interface RxModelApplyer {
	
	
	//boolean beforeApply(ModelApplyEnum type,Object obj,Class<?> entityClass);
	

	
	static HashMap<Class<?>,Map<Class<? extends RxModelApplyer>,List<RxModelApplyer>>> modelApplyClasses = new HashMap<Class<?>,Map<Class<? extends RxModelApplyer>,List<RxModelApplyer>>>();
	
	static<T extends RxModelApplyer> List<T> getModelApplyers(Class<?> clazz,Class<T> t){
		Map<Class<? extends RxModelApplyer>, List<RxModelApplyer>> map = null;
		if(modelApplyClasses.containsKey(clazz)){
			map = modelApplyClasses.get(clazz);
			
			if(map != null && map.containsKey(t)) {
				return (List<T>) map.get(t);
			}else {
				return null;
			}
		}
		boolean isNewMap = false;
		if(map == null) {
			isNewMap = true;
			map = new HashMap<Class<? extends RxModelApplyer>, List<RxModelApplyer>>();
		}
		List<RxModelApplyer> list = null;
		Class<?> clazz1 = clazz;
        while (clazz1 != Object.class) {
    		RxModel applyAn = clazz1.getAnnotation(RxModel.class);
    		if(applyAn != null) {
    			for(Class<? extends RxModelApplyer> cls : applyAn.applyer()) {
    				
    				if(RxModelInserter.class.isAssignableFrom(cls)){
    					list = map.get(RxModelInserter.class);
    					if(list == null) {
    						list = new ArrayList<RxModelApplyer>();
    						try {
								list.add(cls.newInstance());
							} catch (Exception e) {
								e.printStackTrace();
							}
    						map.put(RxModelInserter.class, list);
    					}
    				}
    				if(RxModelDeleter.class.isAssignableFrom(cls)){
    					list = map.get(RxModelDeleter.class);
    					if(list == null) {
    						list = new ArrayList<RxModelApplyer>();
    						try {
								list.add(cls.newInstance());
							} catch (Exception e) {
								e.printStackTrace();
							}
    						map.put(RxModelDeleter.class, list);
    					}
    				}
    				if(RxModelUpdater.class.isAssignableFrom(cls)){
    					list = map.get(RxModelUpdater.class);
    					if(list == null) {
    						list = new ArrayList<RxModelApplyer>();
    						try {
								list.add(cls.newInstance());
							} catch (Exception e) {
								e.printStackTrace();
							}
    						map.put(RxModelUpdater.class, list);
    					}
    				}
    				if(RxModelSelecter.class.isAssignableFrom(cls)){
    					list = map.get(RxModelSelecter.class);
    					if(list == null) {
    						list = new ArrayList<RxModelApplyer>();
    						try {
								list.add(cls.newInstance());
							} catch (Exception e) {
								e.printStackTrace();
							}
    						map.put(RxModelSelecter.class, list);
    					}
    				}
    				
    				
    				/*
    				if(t.isAssignableFrom(cls)) {
    					list = map.get(t);
    					if(list == null) {
    						list = new ArrayList<RxModelApplyer>();
    						try {
								list.add(cls.newInstance());
							} catch (Exception e) {
								e.printStackTrace();
							}
    						map.put(t, list);
    					}
    				}
    				*/
    			}
    		}
        	clazz1 = clazz1.getSuperclass();
        }
        if(map.isEmpty()) {
        	modelApplyClasses.put(clazz, null);
        	return null;
        }
        
        if(isNewMap) {
        	modelApplyClasses.put(clazz,map);
        }
        
        return (List<T>)map.get(t);
	}
	
	static HashMap<Class<?>,FieldApplyer[]> applyClasses = new HashMap<Class<?>,FieldApplyer[]>();
	
	
	static class FieldApplyer{
		
		
		Field field;
		
		Field tarField;
		
		//RxModelField rxModelFieldApply;
		
		RxModelFieldApplyer[] rxModelFieldApplyers;
		
		RxConfig[] config;
		
		int tag;
		
		
		FieldApplyer(Field field,Field tarField,RxModelFieldApplyer[] rxModelFieldApplyers,int tag,RxConfig[] config){
			
			field.setAccessible(true);
			this.field = field;
			tarField.setAccessible(true);
			this.tarField = tarField;
			this.rxModelFieldApplyers = rxModelFieldApplyers;
			this.config = config;
			this.tag = tag;
		}
		
		void applyField(ModelApplyEnum type,Object obj) {
			try {
				for(RxModelFieldApplyer rxModelFieldApplyer: rxModelFieldApplyers) {
					rxModelFieldApplyer.apply(type,obj,field,tarField,tag,config);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	static void applyModelFields(ModelApplyEnum type , Object obj) {
        FieldApplyer[] applyFielder = getModelFields(obj.getClass());
        if(applyFielder != null) {
	    	for(int i = applyFielder.length -1;i>=0;i--) {
	    		applyFielder[i].applyField(type, obj);
	        }
        }
	}
	
	
	static FieldApplyer[] getModelFields(Class<?> clazz) {
		
		if(applyClasses.containsKey(clazz)) {
			return applyClasses.get(clazz);
		}
		
		List<FieldApplyer> list = new ArrayList<FieldApplyer>();
		
		Class<?> clazz1 = clazz;
        while (clazz1 != Object.class) {
        	Field[] fields = clazz1.getDeclaredFields();
        	for(Field field : fields) {
        		RxModelField applyAn = field.getAnnotation(RxModelField.class);
        		if(applyAn != null) {
        			
        			if(applyAn.applyer().length == 0) {
	        			String tar = applyAn.tar();
	        			if(tar.equals("")) {
	        				continue;
	        			}else {
	        					Field tarF = getFieldByName(clazz,tar);
	        					
	        					if(tarF == null) {
	        						throw new RuntimeException("未找到对应的tar:" + clazz.getName() +"."+tar);
	        					}
	        					
	        					RxModelField mf = tarF.getAnnotation(RxModelField.class);
	        					
	        					if(mf == null || RxNullEnum.class == mf.em()) {
	        						throw new RuntimeException("找不到对应的tar类型:" + clazz.getName() +"."+ field.getName());
	        					}else {
	        						list.add(new FieldApplyer(field,tarF,new RxModelFieldApplyer[]{new EnumApplyer()} ,applyAn.tag(),applyAn.cfg()));
	        					}
	        			}
        			}else {
        				String tar = applyAn.tar();
	        			Field tarF = null;
	        			if(tar.equals("")) {
	        				tarF = field;
	        			}else {
	        				tarF = getFieldByName(clazz,tar);
							if(tarF == null) {
								throw new RuntimeException("未找到对应的tar:" + clazz.getName() +"."+ tarF);
							}
	        			}
	        			Class<? extends RxModelFieldApplyer>[] clss = applyAn.applyer();
	        			int len = clss.length;
	        			RxModelFieldApplyer[] arr = new RxModelFieldApplyer[len];
	        			try {
		        			for(int i = 0;i< len;i++) {
								arr[i] = clss[i].newInstance();
		        			}
	        			} catch (Exception e) {
							e.printStackTrace();
						}
        				list.add(new FieldApplyer(field,tarF,arr,applyAn.tag(),applyAn.cfg()));
        			}
        		}
        	}
        	clazz1 = clazz1.getSuperclass();
        }
        if(list.isEmpty()) {
        	applyClasses.put(clazz, null);
        	return null;
        }
        FieldApplyer[] res = list.toArray(new FieldApplyer[list.size()]);
        applyClasses.put(clazz,res);
        return res;
	}
	
	static Field getFieldByName(Class<?> clazz2,String name) {
		while (clazz2 != Object.class) {
        	for(Field field2 : clazz2.getDeclaredFields()) {
        		if(field2.getName().equals(name)) {
        			return field2;
        		}
        	}
        	clazz2 = clazz2.getSuperclass();
		}
		return null;
	}
	
	/*
	static List<Field> getAllFields(Class<?> clazz) {
		
		List<Field> res = new ArrayList<Field>();
        while (clazz != Object.class) {
        	Field[] fields = clazz.getDeclaredFields();
        	for(Field f:fields) {
        		res.add(f);
        	}
        }
        return res;
	}
	*/
}
