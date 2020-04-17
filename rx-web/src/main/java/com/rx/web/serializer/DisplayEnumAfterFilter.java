package com.rx.web.serializer;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import com.alibaba.fastjson.serializer.AfterFilter;
import com.rx.base.Showable;
import com.rx.base.enm.RxNullEnum;
import com.rx.base.model.annotation.RxModel;
import com.rx.base.model.annotation.RxModelField;

public class DisplayEnumAfterFilter extends AfterFilter{

	
	private static String Enum_display_prefix = "rx_display_";
	
	@Override
	public void writeAfter(Object object) {
		
		if(object != null && object.getClass().getAnnotation(RxModel.class) != null);
		{
			
			List<Class<?>> list = getSuperclasses(object.getClass());
	        int len = list.size();
	        Class<?> tarClazz = null;
	        for (int i = len - 1; i >= 0; i--) {
	            tarClazz = list.get(i);
	            Field[] fields = tarClazz.getDeclaredFields();
	            for (Field field : fields) {
	                
	                RxModelField mf = field.getAnnotation(RxModelField.class);
	                if(mf != null && mf.em() != RxNullEnum.class && Showable.class.isAssignableFrom(mf.em())) {
	                	field.setAccessible(true);
	                    Object value;
						try {
							value = field.get(object);
							if(value != null) {
								for (Showable<?> item: (Showable[])mf.em().getEnumConstants()) {
					    			if(Objects.equals(item.value(), value)) {
					    				writeKeyValue(Enum_display_prefix+field.getName(),item.display());
					    			}
					    		}
		                    }
							
							
						} catch (IllegalArgumentException | IllegalAccessException e) {
							e.printStackTrace();
						}
	                }
	            }
	        }
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
