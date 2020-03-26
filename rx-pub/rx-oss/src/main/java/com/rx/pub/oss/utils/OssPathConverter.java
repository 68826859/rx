package com.rx.pub.oss.utils;

import java.lang.reflect.Field;

import com.rx.base.model.ModelApplyEnum;
import com.rx.base.model.RxModelFieldApplyer;
import com.rx.base.model.annotation.RxConfig;

public class OssPathConverter implements RxModelFieldApplyer {

	@Override
	public void apply(ModelApplyEnum type, Object model, Field field, Field tar, int tag, RxConfig[] cfg)
			throws Exception {
		if(type == ModelApplyEnum.SELECT) {
			String path = (String)tar.get(model);
			if(path != null){
				tar.set(model,OssHelper.getUrl(path, null));
			}
		}
		
	}
}
