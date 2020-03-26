package com.rx.base.enm;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.rx.base.Showable;

public class EnumUtil {

    public static List extractEnum(Class clz){
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> p;
        for (Showable obj: (Showable[])clz.getEnumConstants()) {
            p = new HashMap<>();
            p.put("valueField", obj.value());
            p.put("displayField", obj.display());
            list.add(p);
        }
        return list;
    }


    public static List<String> extractEnumValues(Class clz){
        List<String> list = new ArrayList<>();
        for (Showable obj: (Showable[])clz.getEnumConstants()) {
            list.add((String) obj.value());
        }
        return list;
    }

    public static <T> T[] getEnumValue(Class clz, Object key, Class<T> tClass){
        for (Showable obj : (Showable[]) clz.getEnumConstants()) {
            if (key.equals(obj.display())){
                try {
                    return (T[])obj.value();
                }catch (Exception ignore){ }
            }
        }
        return null;
    }


    public static String getEnumDisplay(Class clz, Object key){
        for (Showable obj : (Showable[]) clz.getEnumConstants()) {
            if (obj.value() instanceof Integer){
                if (key instanceof String){
                    try {
                        if (obj.value().equals(Integer.valueOf((String) key))) {
                            return obj.display();
                        }
                    }catch (Exception ignore){ }
                }
                if (key instanceof Integer){
                    if (obj.value().equals(key)) {
                        return obj.display();
                    }
                }
            }else {
                if (obj.value().equals(key)) {
                    return obj.display();
                }
            }
        }
        return "";
    }

    public static Showable getEnumClass(Class clz, Object key){
        for (Showable obj : (Showable[]) clz.getEnumConstants()) {
            if (obj.value() instanceof Integer){
                if (key instanceof String){
                    try {
                        if (obj.value().equals(Integer.valueOf((String) key))) {
                            return obj;
                        }
                    }catch (Exception ignore){

                    }
                }
                if (key instanceof Integer){
                    if (obj.value().equals(key)) {
                        return obj;
                    }
                }
            }else {
                if (obj.value().equals(key)) {
                    return obj;
                }
            }
        }
        return null;
    }
    
    
    public static <T extends Enum<T> & Showable<K>,K> T valueOf(Class<T> enumType,K value,T defalutE) {
		T[] ts = enumType.getEnumConstants();
		for(T t :ts) {
			if (Objects.equals(t.value(),value)) {
				return t;
			}
		}
		return defalutE;
	}

}
