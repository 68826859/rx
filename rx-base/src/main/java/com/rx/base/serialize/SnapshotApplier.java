package com.rx.base.serialize;

import java.lang.reflect.Field;

import com.rx.base.model.ModelApplyEnum;
import com.rx.base.model.RxModelFieldApplyer;
import com.rx.base.model.annotation.RxConfig;

public class SnapshotApplier implements RxModelFieldApplyer {
	
	@Override
	public void apply(ModelApplyEnum type, Object model, Field field, Field tar, int tag, RxConfig[] cfg)
			throws Exception {
		if(type == ModelApplyEnum.SELECT) {
			Object fm = tar.get(model);
			if(fm != null) {
				Object res = field.getType().newInstance();
				if(res instanceof SnapshotList){
					((SnapshotList<?>)res).applyShot(fm,field);
				}else if(res instanceof Snapshot) {
					((Snapshot)res).applyShot(fm);
				}else {
					throw new Exception("错误的Snapshot配置:"+ model.getClass().getName());
				}
				field.set(model, res);
			}
		}else {
			Object ob = tar.get(model);
			if(ob == null) {
				Object va = field.get(model);
				
				if(va instanceof SnapshotList){
					tar.set(model,((SnapshotList<?>)va).shot());
				}else if(va instanceof Snapshot) {
					tar.set(model,((Snapshot<?>)va).shot());
				}else {
					throw new Exception("错误的Snapshot配置:"+ model.getClass().getName());
				}
				
			}
		}
		
	}

}
