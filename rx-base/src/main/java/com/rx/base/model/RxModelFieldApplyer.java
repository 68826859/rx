package com.rx.base.model;

import java.lang.reflect.Field;

import com.rx.base.model.annotation.RxConfig;
public interface RxModelFieldApplyer {
	
	/**
	 * 
	 * @param type
	 * @param model
	 * @param field
	 * @param tar
	 * @param tag
	 * @param cfg
	 * @throws Exception 将阻断其它FieldApplyer
	 */
	void apply(ModelApplyEnum type ,Object model, Field field, Field tar, int tag , RxConfig[] cfg) throws Exception;
	
	public static Object getCfgValue(RxConfig[] cfg,String key) {
		
		for(RxConfig cg: cfg) {
			if(cg.key().equals(key)) {
				return cg.value();
			}
		}
		
		return null;
	}
}
