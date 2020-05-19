package com.rx.base.model;

import java.util.List;

public interface RxModelSelecter extends RxModelApplyer {
	
	
	boolean beforeSelect(Object obj,Class<?> entityClass);
	
	
	boolean afterSelect(Object obj,Class<?> entityClass,Object result);
	
	
	public static void beforeSelectModel(Object obj,Class<?> entityClass) {
		
		if (obj instanceof List) {
            List<?> list = (List<?>) obj;
            if (!list.isEmpty()) {
                for (Object o : list) {
                	beforeSelectModelOne(o,entityClass);
                }
            }
        }else {
        	beforeSelectModelOne(obj,entityClass);
        }
	}
	public static void afterSelectModel(Object obj,Class<?> entityClass,Object result) {
		
		if (result instanceof List) {
            List<?> list = (List<?>) result;
            if (!list.isEmpty()) {
                for (Object o : list) {
                	if(o != null)
                	afterSelectModelOne(obj,o.getClass(),o);
                }
            }
        }else {
        	afterSelectModelOne(obj,result.getClass(),result);
        }
	}
	
	static void beforeSelectModelOne(Object obj,Class<?> modelClass) {
		
		List<RxModelSelecter> applyers = RxModelApplyer.getModelApplyers(modelClass,RxModelSelecter.class);
		if(applyers != null) {
			if(obj instanceof RxModelSelecter) {
				if(((RxModelSelecter)obj).beforeSelect(obj,modelClass)) {
					for(RxModelSelecter ap : applyers) {
						if(!ap.beforeSelect(obj,modelClass)) {
							return;
						}
					}
				}
			}else {
				for(RxModelSelecter ap : applyers) {
					if(!ap.beforeSelect(obj,modelClass)) {
						return;
					}
				}
			}
			
		}
	}
	static void afterSelectModelOne(Object obj,Class<?> modelClass,Object result) {
		
		RxModelApplyer.applyModelFields(ModelApplyEnum.SELECT,result);
		
		List<RxModelSelecter> applyers = RxModelApplyer.getModelApplyers(modelClass,RxModelSelecter.class);
		if(applyers != null) {
			
			for(int i = applyers.size()-1; i >= 0;i--) {
				if(!applyers.get(i).afterSelect(obj,modelClass,result)) {
					return;
				}
			}
			if(result instanceof RxModelSelecter) {
				((RxModelSelecter)result).afterSelect(obj,modelClass,result);
			}
			
		}
	}
	
}
