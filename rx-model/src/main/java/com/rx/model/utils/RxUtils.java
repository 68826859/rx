package com.rx.model.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class RxUtils {

	public static Map<String, Object> paramToMap(Object bean) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (bean == null)
			return map;
		Field[] fields = bean.getClass().getDeclaredFields();
		for (Field f : fields) {
			f.setAccessible(true);
			Object v = null;
			try {
				v = f.get(bean);
			} catch (Exception e) {
			}
			if (v != null) {
				if (v instanceof String) {
					v = ((String) v).trim();
					if (v.equals(""))
						continue;
				} else if (v instanceof String[]) {
					if (((String[]) v).length == 0)
						continue;
				}
				map.put(f.getName(), v);
			}

		}
		return map;
	}

	/**
	 * javaBean 转 Map 其中 key的格式为驼峰<br>
	 * 如:key:userName 转成map 为 user_name
	 * 
	 * @param bean
	 * @return
	 */
	public static Map<String, Object> javaBeanToMap(Object bean) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (bean == null)
			return map;
		Field[] fields = bean.getClass().getDeclaredFields();
		for (Field f : fields) {
			f.setAccessible(true);
			Object v = null;
			try {
				v = f.get(bean);
			} catch (Exception e) {
			}
			if (v != null)
				map.put(camelCaseToUnderscore(f.getName()), v);
		}
		return map;
	}

	/**
	 * 下划线转驼峰 如 user_name -> userName
	 * 
	 * @param str
	 * @return
	 */
	public static String underscoreToCamelCase(String str) {
		if (str == null || str.length() == 0)
			return str;
		StringBuilder sb = new StringBuilder(str.length());
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c == '_') {
				sb.append(Character.toUpperCase(str.charAt(i + 1)));
				i++;
				continue;
			}
			sb.append(c);
		}
		return sb.toString();
	}

	/**
	 * 驼峰转下划线 如 userName -> user_name
	 * 
	 * @param str
	 * @return
	 */
	public static String camelCaseToUnderscore(String str) {
		if (str == null || str.length() == 0)
			return str;
		StringBuilder sb = new StringBuilder(str.length());
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (Character.isUpperCase(c)) {
				sb.append("_");
				sb.append(Character.toLowerCase(c));
				continue;
			}
			sb.append(c);
		}
		return sb.toString();
	}
}
