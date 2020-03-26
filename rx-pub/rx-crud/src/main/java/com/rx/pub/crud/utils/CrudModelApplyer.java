package com.rx.pub.crud.utils;

import java.lang.reflect.Field;
import java.util.List;
import javax.persistence.Id;
import com.alibaba.fastjson.JSON;
import com.rx.base.model.RxModelDeleter;
import com.rx.base.model.RxModelInserter;
import com.rx.base.model.RxModelUpdater;
import com.rx.base.model.annotation.RxModel;
import com.rx.base.result.AlertTypeEnum;
import com.rx.base.result.type.ValidateException;
import com.rx.base.utils.StringUtil;
import com.rx.pub.crud.enm.CrudTypeEnum;
import com.rx.pub.crud.po.PubCrud;
import com.rx.pub.crud.service.IPubCrudService;
import com.rx.pub.mybatis.impls.MyBatisMapper;
import com.rx.pub.mybatis.utils.ModelDefine;
import com.rx.pub.mybatis.utils.MybatisModelHelper;
import com.rx.spring.utils.SpringContextHelper;
import com.rx.web.utils.HttpServletHelper;


public class CrudModelApplyer implements RxModelInserter,RxModelDeleter,RxModelUpdater {

	private static Object getIdValue(Object model) {
		Class<?> clazz = model.getClass();
		Class<?> clazz1 = clazz;
        while (clazz1 != Object.class) {
        	Field[] fields = clazz1.getDeclaredFields();
        	for(Field field : fields) {
        		Id idAn = field.getAnnotation(Id.class);
        		if(idAn != null) {
        			field.setAccessible(true);
        			try {
						return field.get(model);
					} catch (Exception e) {
						e.printStackTrace();
					}
        		}
        	}
        	clazz1 = clazz1.getSuperclass();
        }
        return null;
	}

	@Override
	public boolean beforeUpdate(Object obj, Class<?> entityClass) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean afterUpdate(Object obj, Class<?> entityClass, Object result) {
		IPubCrudService service = SpringContextHelper.getBean(IPubCrudService.class);
		PubCrud record = new PubCrud();
		record.setId(StringUtil.getUUID());
		record.setCrudType(CrudTypeEnum.修改.getCode());
		record.setDataType(entityClass.getName());
		Object id = getIdValue(obj);
		if(id instanceof String) {
			record.setDataId((String)id);
		}else {
			record.setDataId(JSON.toJSONString(id));
		}
		record.setData(JSON.toJSONString(obj));
		service.insert(record);
		return true;
	}
	
	@Override
	public boolean beforeDelete(Object model, Class<?> entityClass) {
		
		List<ModelDefine> models = MybatisModelHelper.getModelRefs(entityClass);
		
		for(ModelDefine md:models) {
			MyBatisMapper map = (MyBatisMapper)SpringContextHelper.springContext.getBean(md.getMapperBeanName());
			int count = 0;
			try {
				Object search = md.getFieldModelClass().newInstance();
				
				md.getField().set(search, model);
				count = map.selectCount(search);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(count > 0) {
				
				String tableName = md.getFieldModelClass().getSimpleName();
				RxModel rm = md.getFieldModelClass().getAnnotation(RxModel.class);
				if(rm != null && !RxModel.NULL.equals(rm.text())) {
					tableName = rm.text();
				}
				throw new ValidateException("库表<" + tableName + ">对该条数据有引用，不能删除").setAlertTypeEnum(AlertTypeEnum.需要关闭的提示2);
			}
		}
		
		IPubCrudService service = SpringContextHelper.getBean(IPubCrudService.class);
		PubCrud record = new PubCrud();
		record.setId(StringUtil.getUUID());
		record.setCrudType(CrudTypeEnum.删除.getCode());
		record.setDataType(entityClass.getName());
		if(model.getClass() == entityClass) {
			Object id = getIdValue(model);
			if(id instanceof String) {
				record.setDataId((String)id);
			}else {
				record.setDataId(JSON.toJSONString(id));
			}
			record.setData(JSON.toJSONString(model));
		}else if(model.getClass() == String.class){
			record.setDataId((String)model);
			record.setData(JSON.toJSONString(SpringContextHelper.getBeanService(entityClass).selectByPrimaryKey(model)));
		}else {
			record.setDataId(JSON.toJSONString(model));
			record.setData(JSON.toJSONString(SpringContextHelper.getBeanService(entityClass).selectByPrimaryKey(model)));
		}
		service.insert(record);
		return true;
	}

	@Override
	public boolean afterDelete(Object obj, Class<?> entityClass, Object result) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean beforeInsert(Object obj, Class<?> entityClass) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean afterInsert(Object model, Class<?> entityClass, Object result) {
		IPubCrudService service = SpringContextHelper.getBean(IPubCrudService.class);
		PubCrud record = new PubCrud();
		record.setId(StringUtil.getUUID());
		record.setCrudType(CrudTypeEnum.新增.getCode());
		record.setDataType(model.getClass().getName());
		Object id = getIdValue(model);
		if(id instanceof String) {
			record.setDataId((String)id);
		}else {
			record.setDataId(JSON.toJSONString(id));
		}
		record.setData(JSON.toJSONString(model));
		service.insert(record);
		return true;
	}

}
