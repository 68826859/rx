package com.rx.web.user;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import com.rx.base.user.RxPermissionable;
import com.rx.base.user.RxUserable;
import com.rx.spring.utils.SpringContextHelper;

public class PermissionMgr {

	/*
	private static Map<Class<? extends Annotation>, List<RxPermissionable>> permissionItems = new HashMap<>();
	*/
	private static Map<Class<? extends Annotation>, Class<? extends Enum<? extends RxPermissionable>>> permissionEnums = new HashMap<>();
	
	private static Map<Class<? extends Annotation>, Class<? extends RxUserable>> annotationUserTypes = new HashMap<>();

	public static void registerPermissions(Class<? extends Annotation> an,Class<? extends Enum<? extends RxPermissionable>> es){
		registerPermissions(an,es,null);
	}
	
	/*
	 * 注册权限点，userType为空表示注册给所有人
	 */
	public static void registerPermissions(Class<? extends Annotation> an,Class<? extends Enum<? extends RxPermissionable>> es,Class<? extends RxUserable> userType){
		permissionEnums.put(an, es);
		annotationUserTypes.put(an, userType);
		RxPermissionable[] em = (RxPermissionable[]) es.getEnumConstants();
		ArrayList<RxPermissionable> list = new ArrayList<>();
		for (RxPermissionable ee : em) {
			if(hasCode(list,ee.getId())){
				throw new RuntimeException("有相同的权限:"+ee.getId());
			}
			list.add(ee);
		}
		SpringContextHelper.getBean(PermissionPersistencer.class).regPermission(list,userType);
		//permissionItems.put(an, list);
	}
	
	private static boolean hasCode(ArrayList<RxPermissionable> list,String code) {
		for (RxPermissionable it : list) {
			if(Objects.equals(it.getId(), code)) {
				return true;
			}
		}
		return false;
	}
	
	/*
	 * public static void registerPermissions(Class<? extends Annotation>
	 * an,List<PermissionItem> ps){ permissions.put(an, ps); }
	 */

	private static Map<Annotation, String> permissionStrs = new HashMap<Annotation, String>();

	public static String getAnnotationPermissionStr(Annotation permission) {
		if (permissionStrs.containsKey(permission)) {
			return permissionStrs.get(permission);
		} else {
			Class<? extends Enum<? extends RxPermissionable>> emClass = permissionEnums.get(permission.annotationType());
			Method[] method = permission.annotationType().getMethods();
			for (Method me : method) {
				if (me.getReturnType().isArray() && me.getReturnType().getComponentType().equals(emClass)) {
					try {
						RxPermissionable[] oo = (RxPermissionable[]) me.invoke(permission);
						StringBuffer sb = new StringBuffer();
						for (RxPermissionable o : oo) {
							sb.append(o.getId()).append(",");
						}
						String str = sb.toString();
						permissionStrs.put(permission, str);
						return str;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return null;
	}
	
	public static String getAnnotationPermissionNames(Annotation permission) {
		Class<? extends Enum<? extends RxPermissionable>> emClass = permissionEnums.get(permission.annotationType());
		Method[] method = permission.annotationType().getMethods();
		for (Method me : method) {
			if (me.getReturnType().isArray() && me.getReturnType().getComponentType().equals(emClass)) {
				try {
					RxPermissionable[] oo = (RxPermissionable[]) me.invoke(permission);
					StringBuffer sb = new StringBuffer();
					for (RxPermissionable o : oo) {
						sb.append("<").append(o.getName()).append(">");
					}
					return sb.toString();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	
	public static List<? extends RxPermissionable> getAllPermissionItems() {
		return getAllPermissionItems(RxUser.getUser().getClass());
	}
	
	public static List<? extends RxPermissionable> getAllPermissionItems(Class<? extends RxUserable> userType) {
		return SpringContextHelper.getBean(PermissionPersistencer.class).getAllPermissionItems(userType);
	}
	

	public static Class<? extends Enum<? extends RxPermissionable>> getPermissionEnum(Class<? extends Annotation> an) {
		return permissionEnums.get(an);
	}
	
	public static Class<? extends RxUserable> getAnnotationUserTypes(Annotation an) {
		Class<? extends RxUserable> cls = null;
		if(an.annotationType() == RxPermission.class) {
			cls = ((RxPermission)an).type();
			if( cls == RxUser.class) {
				cls = null;
				//cls = RxUserConfig.lastUserClass;
			}
		}else {
			cls = annotationUserTypes.get(an.annotationType());
			if(cls == null) {
				//cls = RxUserConfig.getLastUserClass();
			}
		}
		return cls;
	}
	

	public static boolean hasAnnotation(Annotation an) {
		return permissionEnums.containsKey(an.annotationType());
	}

}
