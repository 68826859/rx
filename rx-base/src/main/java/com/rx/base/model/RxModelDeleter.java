package com.rx.base.model;

import java.util.List;

public interface RxModelDeleter extends RxModelApplyer {
	
	
	boolean beforeDelete(Object obj,Class<?> entityClass);
	
	
	boolean afterDelete(Object obj,Class<?> entityClass,Object result);
	
	
	public static void beforeDeleteModel(Object obj,Class<?> entityClass) {
		
		if (obj instanceof List) {
            List<?> list = (List<?>) obj;
            if (!list.isEmpty()) {
                for (Object o : list) {
                	beforeDeleteModelOne(o,entityClass);
                }
            }
        }else {
        	beforeDeleteModelOne(obj,entityClass);
        }
	}
	public static void afterDeleteModel(Object obj,Class<?> entityClass,Object result) {
		
		if (obj instanceof List) {
            List<?> list = (List<?>) obj;
            if (!list.isEmpty()) {
                for (Object o : list) {
                	afterDeleteModelOne(o,entityClass,result);
                }
            }
        }else {
        	afterDeleteModelOne(obj,entityClass,result);
        }
	}
	
	static void beforeDeleteModelOne(Object obj,Class<?> modelClass) {
		
		List<RxModelDeleter> applyers = RxModelApplyer.getModelApplyers(modelClass,RxModelDeleter.class);
		if(applyers != null) {
			if(obj instanceof RxModelDeleter) {
				if(((RxModelDeleter)obj).beforeDelete(obj,modelClass)) {
					for(RxModelDeleter ap : applyers) {
						if(!ap.beforeDelete(obj,modelClass)) {
							return;
						}
					}
				}
			}else {
				for(RxModelDeleter ap : applyers) {
					if(!ap.beforeDelete(obj,modelClass)) {
						return;
					}
				}
			}
			
		}
		RxModelApplyer.applyModelFields(ModelApplyEnum.DELETE,obj);
	}
	static void afterDeleteModelOne(Object obj,Class<?> modelClass,Object result) {
		
		List<RxModelDeleter> applyers = RxModelApplyer.getModelApplyers(modelClass,RxModelDeleter.class);
		if(applyers != null) {
			
			for(int i = applyers.size()-1; i >= 0;i--) {
				if(!applyers.get(i).afterDelete(obj,modelClass,result)) {
					return;
				}
			}
			if(obj instanceof RxModelDeleter) {
				((RxModelDeleter)obj).afterDelete(obj,modelClass,result);
			}
			
		}
	}
	
}
