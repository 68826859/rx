package com.rx.base.enm;

import java.lang.reflect.Field;

import com.rx.base.Showable;
import com.rx.base.model.ModelApplyEnum;
import com.rx.base.model.RxModelFieldApplyer;
import com.rx.base.model.annotation.RxConfig;
import com.rx.base.model.annotation.RxModelField;

public class EnumApplyer implements RxModelFieldApplyer {

	@Override
	public void apply(ModelApplyEnum type, Object model, Field field, Field tar, int tag, RxConfig[] cfg) throws Exception {
		
		if(type == ModelApplyEnum.SELECT) {
			RxModelField mf = tar.getAnnotation(RxModelField.class);
			if(mf == null) {
				throw new RuntimeException("枚举映射找不到对应的RxModelField注解");
			}
			Class<? extends Enum<?>> emCls = mf.em();
			
			if(Showable.class.isAssignableFrom(emCls)) {
				Object tarV = tar.get(model);
				for (Showable<?> obj : (Showable[]) emCls.getEnumConstants()) {
	                if (obj.value().equals(tarV)) {
	                    if (field.getType().isEnum()) {
	                        field.set(model, obj);
	                    } else {
	                        field.set(model, obj.display());
	                    }
	                    break;
	                }
	            }
			}
		}
	}
}
