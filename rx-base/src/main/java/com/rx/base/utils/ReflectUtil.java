package com.rx.base.utils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ReflectUtil {


	public static Object getPropertyValue(String fieldName, String entityName) throws Exception {
		Object entity = Class.forName(entityName).newInstance();
		return getPropertyValue(fieldName, entity);
	}

	// 动态从类里调用get函数
	public static Object getPropertyValue(String fieldName, Object obj) throws Exception {
		Object result = null;
		if (fieldName == null || "".equals(fieldName) || obj == null) {
			return result;
		}
		if (obj instanceof Map) {
			return ((Map) obj).get(fieldName);
		}
		String prop = Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
		String mname = "get" + prop;
		Method method = obj.getClass().getMethod(mname, new Class[0]);
		if (method != null) {
			result = method.invoke(obj, new Object[0]);
		}
		return result;
	}

	/**
	 * 保存有set函数的属性数据
	 * 
	 * @param name  属性名
	 * @param obj   属性所属的对象
	 * @param param 参数值
	 */
	public static void setPropertyValue(String name, Object obj, Object param) throws Exception  {
		setPropertyValue(name, obj, new Object[] { param });
	}

	/**
	 * 保存有set函数的属性数据
	 * 
	 * @param name  属性名
	 * @param obj   属性所属的对象
	 * @param param 参数值
	 */
	public static void setPropertyValue(String name, Object obj, Object[] paramArr) throws Exception  {
		if (name == null || "".equals(name)) {
			return;
		}
		String prop = Character.toUpperCase(name.charAt(0)) + name.substring(1);
		Class[] parameterTypes = new Class[paramArr.length];
		for (int i = 0; i < parameterTypes.length; i++) {
			if (paramArr[i] != null) {
				parameterTypes[i] = paramArr[i].getClass();
			}
		}
		String mname = "set" + prop;
		Method method = obj.getClass().getMethod(mname, parameterTypes);
		method.invoke(obj, paramArr);
	}

	public static void setValue(String name, Object obj, Object value)  throws Exception  {
		if (name == null || "".equals(name)) {
			return;
		}
		PropertyDescriptor pd = new PropertyDescriptor(name, obj.getClass());
		pd.getWriteMethod().invoke(obj, new Object[] { value });
	}

	public static Object invokeMethod(String method, String clazzName)  throws Exception {
		return invokeMethod(method, clazzName, new Object[0]);
	}

	public static Object invokeMethod(String method, String clazzName, Object[] args)  throws Exception {
		Object value = null;
		Object object = Class.forName(clazzName).newInstance();
		value = invokeMethod(method, object, args);
		return value;
	}

	public static Object invokeMethod(String method, Object obj)  throws Exception  {
		return invokeMethod(method, obj, new Object[0]);
	}

	// 动态从类里调用函数
	public static Object invokeMethod(String method, Object obj, Object[] args) throws Exception  {
		Object result = null;
		if (method == null || "".equals(method)) {
			return result;
		}
		Class[] parameterTypes = new Class[args.length];
		for (int i = 0; i < parameterTypes.length; i++) {
			parameterTypes[i] = args[i].getClass();
		}
		Method mdMethod = obj.getClass().getMethod(method, parameterTypes);
		if (mdMethod != null) {
			result = mdMethod.invoke(obj, args);
		}
		return result;
	}

	public static Class getFieldType(String fieldName, Object obj)  throws Exception {
		Field field = obj.getClass().getDeclaredField(fieldName);
		return field.getType();
	}

	public static Class getFieldType(String fieldName, Class obj)  throws Exception {
		Class type  = getFieldType(fieldName, obj.newInstance());
		return type;
	}

	@SuppressWarnings("unchecked")
	public static Object newInstance(String entityName) throws Exception {
		Class<?> clazz = null;
		if (entityName == null || "".equals(entityName)) {
			return clazz;
		}
		clazz = Class.forName(entityName);
		return newInstance(clazz);
	}

	@SuppressWarnings("unchecked")
	public static Class<?> getClass(String entityName) throws Exception {
		return Class.forName(entityName);
	}

	/**
	 * 实例化一个不带参数的对象
	 * 
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object newInstance(Class clazz) throws Exception  {
		Object obj = null;
		if (clazz != null) {
			obj = clazz.newInstance();
		}
		return obj;
	}

	/**
	 * 实例化一个带参数的对象
	 * 
	 * @param clazz
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object newInstance(Class clazz, Object[] params) throws Exception {
		Object obj = null;
			if (clazz != null) {
				if (params != null && params.length > 0) {
					Class paramCls[] = new Class[params.length];
					for (int i = 0; i < params.length; i++) {
						paramCls[i] = params[i].getClass();
					}
					obj = clazz.getConstructor(paramCls).newInstance(params);
				} else {
					obj = clazz.newInstance();
				}
			}
		return obj;
	}

	public static boolean isInstanceOf(Object instance, Object object) {
		return instance.getClass().isInstance(object);
	}

	public static boolean isPojo(Object object) {
		if (object != null) {
			return isPojo(object.getClass());
		} else {
			return false;
		}
	}

	public static boolean isIterable(Object object) {
		if (object == null)
			return false;
		if (object instanceof Map)
			return true;
		if (object.getClass().isArray())
			return true;
		if (object instanceof Enumeration)
			return true;
		return object instanceof Iterator;
	}

	public static boolean isStatic(Field field) {
		if (field != null) {
			String str = field.toString();
			return PatternUtil.isMatch(str, ".* static .*");
		}
		return false;
	}

	public static boolean isFinal(Field field) {
		if (field != null) {
			String str = field.toString();
			return PatternUtil.isMatch(str, ".* final .*");
		}
		return false;
	}

	public static boolean isPublic(Field field) {
		if (field != null) {
			String str = field.toString();
			return PatternUtil.isMatch(str, ".* public .*");
		}
		return false;
	}

	/**
	 * 往String,Integer, Long, Double, Boolean、Character、Float、Short和Byte这些基础类型赋值
	 * 
	 * @return
	 */
	public static void setBaseData(Object obj, Field field, Object data) throws Exception  {
		if (obj == null || field == null || data == null) {
			return;
		}
		String name = field.getName();
		if (field.getType().isInstance(new String())) {
			ReflectUtil.setPropertyValue(field.getName(), obj, data);
		} else if (field.getType().isInstance(new Integer(0))) {
			ReflectUtil.setPropertyValue(field.getName(), obj, new Integer(data.toString()));
		} else if (field.getType().isInstance(new Long(0))) {
			ReflectUtil.setPropertyValue(field.getName(), obj, new Long(data.toString()));
		} else if (field.getType().isInstance(new Double(0))) {
			ReflectUtil.setPropertyValue(field.getName(), obj, new Double(data.toString()));
		} else if (field.getType().isInstance(new Boolean(true))) {
			ReflectUtil.setPropertyValue(field.getName(), obj, new Boolean(data.toString()));
		} else if (field.getType().isInstance(new Character('r'))) {
			ReflectUtil.setPropertyValue(field.getName(), obj, new Character(data.toString().charAt(0)));
		} else if (field.getType().isInstance(new Float(0))) {
			ReflectUtil.setPropertyValue(field.getName(), obj, new Float(data.toString()));
		} else if (field.getType().isInstance(new Short("0"))) {
			ReflectUtil.setPropertyValue(field.getName(), obj, new Short(data.toString()));
		} else if (field.getType().isInstance(new Byte("0"))) {
			ReflectUtil.setPropertyValue(field.getName(), obj, new Byte(data.toString()));
		} else if ("int".equals(name)) {
			ReflectUtil.setPropertyValue(field.getName(), obj, Integer.parseInt(data.toString()));
		} else if ("long".equals(name)) {
			ReflectUtil.setPropertyValue(field.getName(), obj, Long.parseLong(data.toString()));
		} else if ("float".equals(name)) {
			ReflectUtil.setPropertyValue(field.getName(), obj, Float.parseFloat(data.toString()));
		} else if ("double".equals(name)) {
			ReflectUtil.setPropertyValue(field.getName(), obj, Double.parseDouble(data.toString()));
		} else if ("boolean".equals(name)) {
			ReflectUtil.setPropertyValue(field.getName(), obj, Boolean.parseBoolean(data.toString()));
		} else if ("short".equals(name)) {
			ReflectUtil.setPropertyValue(field.getName(), obj, Short.parseShort(data.toString()));
		} else if ("byte".equals(name)) {
			ReflectUtil.setPropertyValue(field.getName(), obj, Byte.parseByte(data.toString()));
		} else if ("char".equals(name)) {
			ReflectUtil.setPropertyValue(field.getName(), obj, data.toString().charAt(0));
		}
	}

	/**
	 * new一个String,Integer, Long, Double, Boolean、Character、Float、Short和Byte这些基础类型的值
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object newBaseData(Object value, Class clazz) {
		Object result = null;
		if (value == null || clazz == null) {
			return result;
		}
		if (clazz.isInstance(new String())) {
			result = value.toString();
		} else if (clazz.isInstance(new Integer(0))) {
			result = new Integer(value.toString());
		} else if (clazz.isInstance(new Long(0))) {
			result = new Long(value.toString());
		} else if (clazz.isInstance(new Double(0))) {
			result = new Double(value.toString());
		} else if (clazz.isInstance(new Boolean(true))) {
			result = new Boolean(value.toString());
		} else if (clazz.isInstance(new Character('r'))) {
			result = Character.valueOf(value.toString().charAt(0));
		} else if (clazz.isInstance(new Float(0))) {
			result = new Float(value.toString());
		} else if (clazz.isInstance(new Short("0"))) {
			result = new Short(value.toString());
		} else if (clazz.isInstance(new Byte("0"))) {
			result = new Byte(value.toString());
		}
		return result;
	}

	/**
	 * 判断是否是String,Integer, Long, Double, Boolean、Character、Float、Short和Byte这些基础类型
	 * 
	 * @return
	 */
	public static boolean isBaseData(Field field) {
		if (field == null) {
			return false;
		}
		String name = field.getType().getName();
		return field.getType().isInstance(new String()) || field.getType().isInstance(new Integer(0))
				|| field.getType().isInstance(new Long(0)) || field.getType().isInstance(new Double(0))
				|| field.getType().isInstance(new Boolean(true)) || field.getType().isInstance(new Character('r'))
				|| field.getType().isInstance(new Float(0)) || field.getType().isInstance(new Short("0"))
				|| field.getType().isInstance(new Byte("0")) || "int".equals(name) || "long".equals(name)
				|| "float".equals(name) || "double".equals(name) || "short".equals(name) || "boolean".equals(name)
				|| "byte".equals(name) || "char".equals(name);
	}

	/**
	 * 判断是否是String,Integer, Long, Double, Boolean、Character、Float、Short和Byte这些基础类型
	 * 
	 * @return
	 */
	public static boolean isBaseData(Class clazz) {
		if (clazz == null) {
			return false;
		}
		String name = clazz.getName();
		return clazz.isInstance(new String()) || clazz.isInstance(new Integer(0)) || clazz.isInstance(new Long(0))
				|| clazz.isInstance(new Double(0)) || clazz.isInstance(new Boolean(true))
				|| clazz.isInstance(new Character('r')) || clazz.isInstance(new Float(0))
				|| clazz.isInstance(new Short("0")) || clazz.isInstance(new Byte("0")) || "int".equals(name)
				|| "long".equals(name) || "float".equals(name) || "double".equals(name) || "short".equals(name)
				|| "boolean".equals(name) || "byte".equals(name) || "char".equals(name);
	}

	/**
	 * 判断是否Byte类型数据
	 * 
	 * @param field
	 * @return
	 */
	public static boolean isByteData(Field field) {
		if (field == null) {
			return false;
		}
		return field.getType().isInstance(new Byte("0"));
	}

	/**
	 * 判断是否Byte类型数据
	 * 
	 * @param clazz
	 * @return
	 */
	public static boolean isByteData(Class clazz) {
		if (clazz == null) {
			return false;
		}
		return clazz.getName().equals("byte") || clazz.getName().equals("java.lang.Byte");
	}

	/**
	 * 判断是否byte类型数据
	 * 
	 * @param clazz
	 * @return
	 */
	public static boolean isByteArray(Class clazz) {
		if (clazz == null || clazz.getComponentType() == null) {
			return false;
		}
		return PatternUtil.isMatch(clazz.getComponentType().getName(), ".*byte.*");
	}

	/**
	 * 往String,Integer, Long, Double, Boolean、Character、Float、Short和Byte这些基础类型数组赋值。
	 * 如果不是数组对象或者下标越界不做任何操作
	 * 
	 * @param arrayObj 数组
	 * @param index    数组下标
	 * @param data     值
	 * @author 余博 2012-4-6
	 */
	public static void setBaseArrayData(Object arrayObj, int index, String data) {
		if (arrayObj == null || !arrayObj.getClass().isArray() || data == null || index < 0
				|| index >= Array.getLength(arrayObj)) {
			return;
		}
		Class clazz = arrayObj.getClass().getComponentType();
		if (clazz.isInstance(new String())) {
			Array.set(arrayObj, index, data);
		} else if (clazz.isInstance(new Integer(0))) {
			Array.set(arrayObj, index, new Integer(data));
		} else if (clazz.isInstance(new Long(0))) {
			Array.set(arrayObj, index, new Long(data));
		} else if (clazz.isInstance(new Double(0))) {
			Array.set(arrayObj, index, new Double(data));
		} else if (clazz.isInstance(new Boolean(true))) {
			Array.set(arrayObj, index, new Boolean(data));
		} else if (clazz.isInstance(new Character('r'))) {
			Array.set(arrayObj, index, new Character(data.charAt(0)));
		} else if (clazz.isInstance(new Float(0))) {
			Array.set(arrayObj, index, new Float(data));
		} else if (clazz.isInstance(new Short("0"))) {
			Array.set(arrayObj, index, new Short(data));
		} else if (clazz.isInstance(new Byte("0"))) {
			Array.set(arrayObj, index, new Byte(data));
		}
	}

	/**
	 * 保存时间类型数据
	 * 
	 * @param name       对象的属性名，有set函数
	 * @param obj        对象
	 * @param value      参数值
	 * @param dataFormat 时间格式
	 * @throws ReflectException
	 */
	public static void setDateData(String name, Object obj, String value, String dataFormat) throws Exception {

		SimpleDateFormat sdf = new SimpleDateFormat(dataFormat);
			ReflectUtil.setPropertyValue(name, obj, sdf.parse(value));
	}

	/**
	 * 保存时间类型数据
	 * 
	 * @author 余博 2012-4-6
	 * @param name       对象的属性名，有set函数
	 * @param obj        对象
	 * @param value      参数值
	 * @param dataFormat 时间格式
	 * @throws ReflectException
	 */
	public static void setSqlDateData(String name, Object obj, String value, String dataFormat)
			throws Exception {

		SimpleDateFormat sdf = new SimpleDateFormat(dataFormat);
			java.sql.Date sqlDate = new java.sql.Date(sdf.parse(value).getTime());
			ReflectUtil.setPropertyValue(name, obj, sqlDate);
	}

	/**
	 * 保存时间类型数据
	 * 
	 * @author 余博 2012-4-6
	 * @param name       对象的属性名，有set函数
	 * @param obj        对象
	 * @param value      参数值
	 * @param dataFormat 时间格式
	 * @throws ReflectException
	 */
	public static String getDateData(String name, Object obj, String dataFormat) throws Exception {

		SimpleDateFormat sdf = new SimpleDateFormat(dataFormat);
			Date date = (Date) ReflectUtil.getPropertyValue(name, obj);
			return sdf.format(date);
	}

	/**
	 * 实例化以type为类型的数组
	 * 
	 * @param type   数组类型
	 * @param length 长度
	 * @author 余博 2012-4-6
	 * @return type类型的实例
	 */
	public static Object newArrayInstance(Class type, int length) {
		if (type == null) {
			return type;
		}
		return Array.newInstance(type, length);
	}

	/**
	 * 为数组赋值，如果不是数组对象或者下标越界不做任何操作
	 * 
	 * @param arrayObj 数组
	 * @param index    数组下标
	 * @param data     值
	 * @author 余博 2012-4-6
	 */
	public static void setArrayData(Object arrayObj, int index, Object data) {
		if (arrayObj == null || !arrayObj.getClass().isArray() || data == null || index < 0
				|| index >= Array.getLength(arrayObj)) {
			return;
		}
		Array.set(arrayObj, index, data);
	}

	public static Object getArrayData(Object arrayObj, int index) {
		if (arrayObj == null || !arrayObj.getClass().isArray() || index < 0 || index >= Array.getLength(arrayObj)) {
			return null;
		}
		return Array.get(arrayObj, index);
	}

	/**
	 * 获取数组的类型
	 * 
	 * @param clazz 数组
	 * @return 数组的类型,如果传的的不是数组，返回为空
	 */
	public static Class getArrayType(Class clazz) {
		if (clazz == null) {
			return null;
		}
		return clazz.getComponentType();
	}

	/**
	 * 获取数组的类型 数组
	 * 
	 * @return 数组的类型,如果传的的不是数组，返回为空
	 */
	public static Class getArrayType(Object object) {
		if (object == null) {
			return null;
		}
		return object.getClass().getComponentType();
	}

	/**
	 * 获取数组的长度，如果空或者不是数组，返回长度为0
	 * 
	 * @param array 数组
	 * @return 数组长度
	 */
	public static int getArrayLength(Object array) {
		if (array == null || !array.getClass().isArray() && !(array instanceof List)) {
			return 0;
		}
		return Array.getLength(array);
	}

	public static boolean isImplMap(Object object) {
		if (object instanceof Map)
			return true;
		return false;
	}

	public static boolean isImplMap(Class clazz) {
		if (clazz == null)
			return false;
		else if (HashMap.class.getName().equals(clazz.getName()))
			return true;
		return false;
	}

	public static boolean isImplList(Object list) {
		return list instanceof List;
	}

	public static boolean isImplList(Class list) {
		if (list == null)
			return false;
		else if (ArrayList.class.getName().equals(list.getName()))
			return true;
		return false;
	}

	public static boolean isList(Class list) {
		return List.class.getName().equals(list.getName());
	}

	public static boolean isMap(Class map) {
		return Map.class.getName().equals(map.getName());
	}

	public static boolean isArray(Class<? extends Object> class1) {
		boolean result = false;
		if (class1 != null) {
			result = class1.isArray();
		}
		return result;
	}

	public static Object createArray(Class<? extends Object> clazz, int length) {
		return Array.newInstance(clazz, length);
	}
}
