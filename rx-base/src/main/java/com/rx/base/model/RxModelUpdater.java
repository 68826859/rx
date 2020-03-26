package com.rx.base.model;

import java.util.List;

public interface RxModelUpdater extends RxModelApplyer {
	
	
	boolean beforeUpdate(Object obj,Class<?> entityClass);
	
	
	boolean afterUpdate(Object obj,Class<?> entityClass,Object result);
	
	
	public static void beforeUpdateModel(Object obj,Class<?> entityClass) {
		
		if (obj instanceof List) {
            List<?> list = (List<?>) obj;
            if (!list.isEmpty()) {
                for (Object o : list) {
                	beforeUpdateModelOne(o,entityClass);
                }
            }
        }else {
        	beforeUpdateModelOne(obj,entityClass);
        }
	}
	public static void afterUpdateModel(Object obj,Class<?> entityClass,Object result) {
		
		if (obj instanceof List) {
            List<?> list = (List<?>) obj;
            if (!list.isEmpty()) {
                for (Object o : list) {
                	afterUpdateModelOne(o,entityClass,result);
                }
            }
        }else {
        	afterUpdateModelOne(obj,entityClass,result);
        }
	}
	
	static void beforeUpdateModelOne(Object obj,Class<?> modelClass) {
		
		List<RxModelUpdater> applyers = RxModelApplyer.getModelApplyers(modelClass,RxModelUpdater.class);
		if(applyers != null) {
			if(obj instanceof RxModelUpdater) {
				if(((RxModelUpdater)obj).beforeUpdate(obj,modelClass)) {
					for(RxModelUpdater ap : applyers) {
						if(!ap.beforeUpdate(obj,modelClass)) {
							return;
						}
					}
				}
			}else {
				for(RxModelUpdater ap : applyers) {
					if(!ap.beforeUpdate(obj,modelClass)) {
						return;
					}
				}
			}	
		}
		RxModelApplyer.applyModelFields(ModelApplyEnum.UPDATE,obj);
	}
	static void afterUpdateModelOne(Object obj,Class<?> modelClass,Object result) {
		
		List<RxModelUpdater> applyers = RxModelApplyer.getModelApplyers(modelClass,RxModelUpdater.class);
		if(applyers != null) {
			
			for(int i = applyers.size()-1; i >= 0;i--) {
				if(!applyers.get(i).afterUpdate(obj,modelClass,result)) {
					return;
				}
			}
			if(obj instanceof RxModelUpdater) {
				((RxModelUpdater)obj).afterUpdate(obj,modelClass,result);
			}
			
		}
	}
	
}
