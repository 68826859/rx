package com.rx.web.serializer;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import com.alibaba.fastjson.serializer.AfterFilter;
import com.rx.base.Showable;
import com.rx.base.enm.RxNullEnum;
import com.rx.base.model.annotation.RxModel;
import com.rx.base.model.annotation.RxModelField;
import com.rx.base.page.Pager;
import com.rx.base.result.DataResult;

public class DataResultCommentAfterFilter extends AfterFilter{

	
	//private static String Enum_display_prefix = "enumdisplay_";
	
	@Override
	public void writeAfter(Object res) {
		
		if(res != null) {
			if(res instanceof DataResult) {
				DataResult ds = (DataResult)res;
				Object object = ds.getData();
				if(object != null) {
					writeComment("@comment_data",object.getClass());
				}
			}else if(res instanceof Pager) {
				Class<?> cls = ((Pager<?>)res).getResType();
				if(cls != null) {
					writeComment("@comment_list",cls);
				}
			}else {
				writeComment("@comment",res.getClass());
			}
		}
		
	}
	
	
	public void writeComment(String key,Class<?> cls) {
		if(cls.getAnnotation(RxModel.class) != null);
		{
			List<Class<?>> list = getSuperclasses(cls);
	        int len = list.size();
	        Class<?> tarClazz = null;
	        
	        Map<String,Object> comment = new HashMap<String,Object>();
	        for (int i = len - 1; i >= 0; i--) {
	            tarClazz = list.get(i);
	            Field[] fields = tarClazz.getDeclaredFields();
	            for (Field field : fields) {
	                
	                RxModelField mf = field.getAnnotation(RxModelField.class);
	                if(mf != null) {
	                	field.setAccessible(true);
	                	if(mf.em() != RxNullEnum.class && Showable.class.isAssignableFrom(mf.em())) {
	                		
	                		Map<String,Object> comment2 = new LinkedHashMap<String,Object>();
	                		Map<String,Object> comment3 = new LinkedHashMap<String,Object>();
	                		for (Showable<?> item: (Showable[])mf.em().getEnumConstants()) {
	                			comment3.put(item.display(), item.value());
				    		}
	                		comment2.put("comment", mf.text());
	                		comment2.put("type", "enum");
	                		comment2.put("class", mf.em().getName());
	                		comment2.put("items", comment3);
	                		comment.put(field.getName(),comment2);
	                	}else {
	                		comment.put(field.getName(), mf.text());
	                	}
	                }
	            }
	        }
	        writeKeyValue(key,comment);
		}
	}
	
	


    public static List<Class<?>> getSuperclasses(Class<?> clazz) {
        Class<?> tarClazz = clazz;
        List<Class<?>> list = new ArrayList<Class<?>>();
        while (tarClazz != Object.class) {
            list.add(tarClazz);
            tarClazz = tarClazz.getSuperclass();
        }
        return list;
    }

}
