package com.rx.web.user;

import java.lang.reflect.Field;

import com.rx.base.model.ModelApplyEnum;
import com.rx.base.model.RxModelFieldApplyer;
import com.rx.base.model.annotation.RxConfig;
import com.rx.web.user.RxUser;

public class UserApplyer implements RxModelFieldApplyer {
	@Override
	public void apply(ModelApplyEnum type, Object model, Field field, Field tar, int tag, RxConfig[] cfg)
			throws Exception {
		if(type == ModelApplyEnum.INSERT) {
			Object ob = field.get(model);
			if (ob == null) {
				RxUser<?> user = RxUser.getUser();
				if(user != null) {
					field.set(model,user.getId());
				}
			}
		}
		
	}



}
