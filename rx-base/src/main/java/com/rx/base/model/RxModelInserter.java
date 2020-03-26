package com.rx.base.model;

import java.util.List;

public interface RxModelInserter extends RxModelApplyer {
	
	
	boolean beforeInsert(Object obj,Class<?> entityClass);
	
	
	boolean afterInsert(Object obj,Class<?> entityClass,Object result);
	
	
	public static void beforeInsertModel(Object obj,Class<?> entityClass) {
		
		if (obj instanceof List) {
            List<?> list = (List<?>) obj;
            if (!list.isEmpty()) {
                for (Object o : list) {
                	beforeInsertModelOne(o,entityClass);
                }
            }
        }else {
        	beforeInsertModelOne(obj,entityClass);
        }
	}
	public static void afterInsertModel(Object obj,Class<?> entityClass,Object result) {
		
		if (obj instanceof List) {
            List<?> list = (List<?>) obj;
            if (!list.isEmpty()) {
                for (Object o : list) {
                	afterInsertModelOne(o,entityClass,result);
                }
            }
        }else {
        	afterInsertModelOne(obj,entityClass,result);
        }
	}
	
	static void beforeInsertModelOne(Object obj,Class<?> modelClass) {
		
		List<RxModelInserter> applyers = RxModelApplyer.getModelApplyers(modelClass,RxModelInserter.class);
		if(applyers != null) {
			if(obj instanceof RxModelInserter) {
				if(((RxModelInserter)obj).beforeInsert(obj,modelClass)) {
					for(RxModelInserter ap : applyers) {
						if(!ap.beforeInsert(obj,modelClass)) {
							return;
						}
					}
				}
			}else {
				for(RxModelInserter ap : applyers) {
					if(!ap.beforeInsert(obj,modelClass)) {
						return;
					}
				}
			}	
		}
		
		RxModelApplyer.applyModelFields(ModelApplyEnum.INSERT,obj);
	}
	static void afterInsertModelOne(Object obj,Class<?> modelClass,Object result) {
		
		List<RxModelInserter> applyers = RxModelApplyer.getModelApplyers(modelClass,RxModelInserter.class);
		if(applyers != null) {
			
			for(int i = applyers.size()-1; i >= 0;i--) {
				if(!applyers.get(i).afterInsert(obj,modelClass,result)) {
					return;
				}
			}
			if(obj instanceof RxModelInserter) {
				((RxModelInserter)obj).afterInsert(obj,modelClass,result);
			}
			
		}
	}
	
}
