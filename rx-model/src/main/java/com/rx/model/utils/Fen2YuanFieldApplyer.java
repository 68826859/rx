package com.rx.model.utils;

import java.lang.reflect.Field;
import java.math.BigDecimal;

import com.rx.base.model.ModelApplyEnum;
import com.rx.base.model.RxModelFieldApplyer;
import com.rx.base.model.annotation.RxConfig;

public class Fen2YuanFieldApplyer implements RxModelFieldApplyer {
	
	@Override
	public void apply(ModelApplyEnum type, Object model, Field field, Field tar, int tag, RxConfig[] cfg)
			throws Exception {
		if(type == ModelApplyEnum.SELECT) {
			Object it = tar.get(model);
			if(it != null && Integer.class.isAssignableFrom(it.getClass())) {
				field.set(model,Double.valueOf(BigDecimal.valueOf((Integer)it).divide(BigDecimal.valueOf(100)).doubleValue()));
			}
		}
		
		/*
		else {
			String ob = (String)field.get(model);
			if (ob != null) {
				field.set(model,EmojiParser.parseToAliases(ob));
			}
			
		}
		*/
	}

}
