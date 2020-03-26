package com.rx.pub.file.utils;

import java.lang.reflect.Field;
import com.rx.base.model.ModelApplyEnum;
import com.rx.base.model.RxModelFieldApplyer;
import com.rx.base.model.annotation.RxConfig;
import com.rx.pub.file.base.RxFilePathConverter;
import com.rx.pub.file.base.RxFilePathParam;
import com.rx.spring.utils.PropertiesHelper;
import com.rx.spring.utils.SpringContextHelper;

public class FileConverter implements RxModelFieldApplyer  {

	public final static String FILE_PREFIX_PATH = "http://kanglefu-test.oss-cn-beijing.aliyuncs.com/";

	private static String FILE_SERVICE_URL = PropertiesHelper.getValue("FILE_SERVICE_URL") == null ? ""
			: PropertiesHelper.getValue("FILE_SERVICE_URL");

	public static String getFullPathForPath(String path, RxFilePathParam param) {
		// 调用路径转换器方法
		
		RxFilePathConverter pathConverter = SpringContextHelper.getBean(RxFilePathConverter.class);
		
		if (pathConverter != null) {
			return pathConverter.convert(path, param);
		}
		return FILE_SERVICE_URL + FILE_PREFIX_PATH + path;
	};

	@Override
	public void apply(ModelApplyEnum type, Object model, Field field, Field tar, int tag, RxConfig[] cfg)
			throws Exception {
		if(type == ModelApplyEnum.SELECT) {
			String keyValue = (String) tar.get(model);
			if (keyValue != null) {
				RxFilePathParam param = null;
				if(cfg.length > 0) {
					param = new RxFilePathParam();
					Object wh = RxModelFieldApplyer.getCfgValue(cfg,RxFilePathParam.WIDTH);
					if(wh != null) {
						param.setWidth((Integer) wh);
					}
					wh = RxModelFieldApplyer.getCfgValue(cfg,RxFilePathParam.HEIGHT);
					if(wh != null) {
						param.setHeight((Integer)wh);
					}
				}
				tar.set(model, FileConverter.getFullPathForPath(keyValue,param));
			}
		}else {
			
		}
		
	}

}
