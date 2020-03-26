package com.rx.ext;

import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtConfig;
import com.rx.ext.annotation.ExtProperty;
import com.rx.extrx.widget.EnumStore;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.Map.Entry;

public class Base {
    private boolean isApplyed = false;

    protected boolean isDefine = false;

    private Class<?> targetClazz;//目标类型

    private Object targetObj;


    @ExtProperty()
    private Boolean singleton;

    @ExtProperty()
    private String extend;

    @ExtProperty()
    private String alias;

    @ExtProperty()
    private Map<String, Object> statics;

    @ExtProperty()
    private String[] alternateClassName;


    //@ExtProperty(key="defaultConfig")
    private Map<String, Object> config;
    //全部属性
    private Map<String, Object> properties;

    //全部定义
    private List<Define> defines;
    //全部依赖
    private List<String> requires;


    private List<DefinePlugin<?>> plugins;

    public Base() {
        this.targetClazz = this.getClass();
        try {

            for (Entry<Class<? extends DefinePlugin<?>>, Class<?>> pg : definePlugins.entrySet()) {
                if (pg.getValue().isAssignableFrom(this.getClass())) {
                    if (plugins == null) {
                        plugins = new ArrayList<DefinePlugin<?>>();
                    }
                    DefinePlugin<?> dp = (DefinePlugin<?>) pg.getKey().newInstance();
                    dp.applyBase(this);
                    plugins.add(dp);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Map<Class<? extends DefinePlugin<?>>, Class<?>> definePlugins = new HashMap<Class<? extends DefinePlugin<?>>, Class<?>>();

    public static void setupPlugin(Class<? extends DefinePlugin<?>> pluginClass) {
        Class<?> clazz = getFX(pluginClass);
        definePlugins.put(pluginClass, clazz);
    }


    private static Class<?> getFX(Class<? extends DefinePlugin<?>> pluginClass) {
        ParameterizedType pType = null;
        for (Type type : pluginClass.getGenericInterfaces()) {
            if (type instanceof ParameterizedType) {
                ParameterizedType p2 = (ParameterizedType) type;
                if (p2.getRawType() == DefinePlugin.class) {
                    pType = p2;
                    break;
                }
            }
        }
        if (pType == null) {
            throw new IllegalArgumentException("定义插件必须加入泛型  DefinePlugin<T>" + pluginClass.getName());
        }
        return (Class<?>) pType.getActualTypeArguments()[0];
    }


    public static Base forClass(Class<?> clazz) throws Exception {
        return Base.forClass(clazz, false);
    }

    @SuppressWarnings("unchecked")
    public static Base forClass(Class<?> clazz, boolean isDefine) throws Exception {
        Base base;
        if (Enum.class.isAssignableFrom(clazz)) {
            base = new EnumStore((Class<? extends Enum<?>>) clazz);
            base.setExtend(Define.getJSClazzName(EnumStore.class));
            ExtClass extClass = clazz.getAnnotation(ExtClass.class);
            if (extClass != null) {
                String[] ans = extClass.alternateClassName();
                if (ans.length > 0) {
                    base.setAlternateClassName(ans);
                }
                String[] reqs = extClass.requires();
                if (reqs.length > 0) {
                    for (String req : reqs) {
                        base.addRequire(req);
                    }
                }
            }

        } else {
            Class<? extends Base> tarClazz = null;
            ExtClass classField = clazz.getAnnotation(ExtClass.class);
            if (classField != null) {
                tarClazz = classField.extend();
            }
            if (tarClazz == Base.class && Base.class.isAssignableFrom(clazz)) {
                tarClazz = (Class<? extends Base>) clazz;
            }
            if (tarClazz == null) {
                throw new Exception("没有继承自Base类，必须有ExtClass注解,否者不能输出JS:" + clazz.getName());
            }
            base = tarClazz.newInstance();//1，加载base类自身的注解
            base.isDefine = isDefine;
            base.setTargetClazz(clazz);
            if (tarClazz == clazz) {
                base.setExtend(Define.getJSClazzName(tarClazz.getSuperclass()));
            } else {
                base.setExtend(Define.getJSClazzName(tarClazz));
            }
            base.applyTarget();
        }
        return base;
    }

    public void applyAnnotation(Annotation annotation, Field field, Object value) throws Exception {
        if (annotation instanceof ExtClass) {
            //如果field上面标记了ExtClass
        } else if (annotation instanceof ExtConfig) {
            ExtConfig cfgField = (ExtConfig) annotation;
            String key = cfgField.key();
            if (ExtConfig.NULL.equals(key)) {
                key = field.getName();
            }
            Object cvalue = cfgField.value();
            if (ExtConfig.NULL.equals(cvalue)) {
                cvalue = value;
            }
            if (cvalue != null) {
                this.addConfig(key, cvalue);
            }
        } else if (annotation instanceof ExtProperty) {
            ExtProperty propField = (ExtProperty) annotation;
            String key = propField.key();
            if (ExtProperty.NULL.equals(key)) {
                key = field.getName();
            }
            Object cvalue = propField.value();
            if (ExtProperty.NULL.equals(cvalue)) {
                cvalue = value;
            }
            if (cvalue != null) {
                this.addProperty(key, cvalue);
            }
        }
    }

    public void applyAnnotation(Annotation annotation, Class<?> clazz, Object value) throws Exception {
        if (annotation instanceof ExtClass) {
            ExtClass extClass = (ExtClass) annotation;
            if (clazz == this.targetClazz) {//如果扫描的是目标类，加载目标类的注解
                if (extClass.singleton()) {
                    this.setSingleton(Boolean.TRUE);
                }
                if (!ExtClass.NULL.equals(extClass.alias())) {
                    String as = extClass.alias();
                    this.setAlias(as);
                }
                String[] ans = extClass.alternateClassName();
                if (ans.length > 0) {
                    this.setAlternateClassName(ans);
                }

                String[] reqs = extClass.requires();
                if (reqs.length > 0) {
                    for (String req : reqs) {
                        this.addRequire(req);
                    }
                }
            }
        }
    }

    public void applyAnnotation(Annotation annotation, Method method, Object object) throws Exception {
        if (annotation instanceof ExtClass) {
            ExtClass extClass = (ExtClass) annotation;
            extClass.alter();//方法上面有ExtClass注解
        } else if (annotation instanceof ExtConfig) {
            ExtConfig cfgField = (ExtConfig) annotation;
            String key = cfgField.key();
            if (ExtConfig.NULL.equals(key)) {
                key = method.getName();
            }
            Object cvalue = cfgField.value();
            if (ExtConfig.NULL.equals(cvalue)) {
                cvalue = method.invoke(object);
            }
            if (cvalue != null) {
                this.addConfig(key, cvalue);
            }
        } else if (annotation instanceof ExtProperty) {
            ExtProperty propField = (ExtProperty) annotation;
            String key = propField.key();
            if (ExtProperty.NULL.equals(key)) {
                key = method.getName();
            }
            Object cvalue = propField.value();
            if (ExtProperty.NULL.equals(cvalue)) {
                cvalue = method.invoke(object);
            }
            if (cvalue != null) {
                this.addProperty(key, cvalue);
            }
        }
    }

    private boolean isMineAnnotation(Annotation an) {
        return an instanceof ExtClass || an instanceof ExtConfig || an instanceof ExtProperty;
    }

    protected void applyField(Field field, Object value, boolean baseSelf) throws Exception {
        Annotation[] annotations = field.getAnnotations();
        for (Annotation an : annotations) {
            if ((baseSelf && isMineAnnotation(an)) || (!baseSelf && !isMineAnnotation(an))) {
                this.applyAnnotation(an, field, value);
            }
        }
        if (plugins != null) {
            for (DefinePlugin<?> plugin : plugins) {
                plugin.applyField(field, value);
            }
        }
    }

    private void applyMethod(Method method, Object target, boolean baseSelf) throws Exception {
        Annotation[] annotations = method.getAnnotations();
        for (Annotation an : annotations) {
            if ((baseSelf && isMineAnnotation(an)) || (!baseSelf && !isMineAnnotation(an))) {
                this.applyAnnotation(an, method, target);
            }
        }

        if (plugins != null) {
            for (DefinePlugin<?> plugin : plugins) {
                plugin.applyMethod(method, target);
            }
        }

    }

    protected void applyClass(Class<?> clazz, Object target, boolean baseSelf) throws Exception {
	/*
	if(clazz == null){
			clazz = target.getClass();
	}
	if(target == null){
			target = clazz.newInstance();
	}
	*/
        List<Class<?>> list = Base.getSuperclasses(clazz);
        int len = list.size();
        Class<?> tarClazz = null;
        for (int i = len - 1; i >= 0; i--) {
            tarClazz = list.get(i);
            Method[] methods = tarClazz.getDeclaredMethods();
            for (Method method : methods) {
                method.setAccessible(true);
                this.applyMethod(method, target, baseSelf);//3,加载目标类以及目标类所有父类的方法上的注解
            }
            Field[] fields = tarClazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (target == null) {
                    this.applyField(field, null, baseSelf);//3,加载目标类以及目标类所有父类的属性上的注解
                } else {
                    this.applyField(field, field.get(target), baseSelf);
                }
            }
            //tarClazz = tarClazz.getSuperclass();
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


    private boolean isTargetApplyed = false;

    public void applyTarget() throws Exception {
        if (isTargetApplyed) return;
        isTargetApplyed = true;
        Annotation[] annotations = this.getClass().getAnnotations();
        for (Annotation an : annotations) {
            this.applyAnnotation(an, this.getClass(), this);//初始化加载完自己的类注解
        }
        this.applyClass(this.getClass(), this, false);//扫描一遍自己的其它属性方法

        if (this.targetClazz != null && this.targetClazz != this.getClass()) {
            Annotation[] ans = this.targetClazz.getAnnotations();
            for (Annotation an : ans) {
                this.applyAnnotation(an, this.targetClazz, this.getTarget());//初始化加载完目标类注解
            }
            this.applyClass(this.targetClazz, this.getTarget(), false);//扫描一遍目标的其它属性方法
            if (plugins != null) {
                for (DefinePlugin<?> plugin : plugins) {
                    plugin.applyClass(this.targetClazz, this.getTarget());
                }
            }
        }
    }


    public void applySelf() throws Exception {
        if (isApplyed) return;
        isApplyed = true;

        applyTarget();

        this.applyClass(this.getClass(), this, true);
        if (this.targetClazz != null && this.targetClazz != this.getClass()) {
            this.applyClass(this.targetClazz, this.getTarget(), true);
        }

        if (plugins != null) {
            for (DefinePlugin<?> plugin : plugins) {
                plugin.applyFinish(this);
            }
        }
    }

    public String toJS() throws Exception {
        this.applySelf();
        StringBuffer sb = new StringBuffer("{");
        if (this.isDefine) {
            if (properties != null) {
                for (Entry<String, Object> enty : properties.entrySet()) {
                    sb.append(enty.getKey()).append(":").append(toJS(enty.getValue())).append(",");
                }
            }
            if (config != null) {
                for (Entry<String, Object> enty : config.entrySet()) {
                    sb.append(enty.getKey()).append(":").append(toJS(enty.getValue())).append(",");
                }
            }
            if (requires != null) {
                sb.append("requires:").append(toJS(requires)).append(",");
            }
        } else if (config != null) {
            for (Entry<String, Object> enty : config.entrySet()) {
                sb.append(enty.getKey()).append(":").append(toJS(enty.getValue())).append(",");
            }
        }
        return sb.append("}").toString();
    }
    public String toJson() throws Exception {
        this.applySelf();
        StringBuffer sb = new StringBuffer("{");
        if (this.isDefine) {
            if (properties != null) {
            	if(!config.isEmpty()) {
            		for (Entry<String, Object> enty : properties.entrySet()) {
            			sb.append("\"").append(enty.getKey()).append("\"").append(":").append(toJson(enty.getValue())).append(",");
                	}
            		sb.deleteCharAt(sb.length() - 1);
            	}
            }
            if (config != null) {
            	if(!config.isEmpty()) {
	                for (Entry<String, Object> enty : config.entrySet()) {
	                    sb.append("\"").append(enty.getKey()).append("\"").append(":").append(toJson(enty.getValue())).append(",");
	                }
	                sb.deleteCharAt(sb.length() - 1);
            	}
            }
            //if (requires != null) {
            //    sb.append("requires:").append(toJson(requires)).append(",");
            //}
        } else if (config != null) {
        	if(!config.isEmpty()) {
        		for (Entry<String, Object> enty : config.entrySet()) {
        			sb.append("\"").append(enty.getKey()).append("\"").append(":").append(toJson(enty.getValue())).append(",");
            	}
        		sb.deleteCharAt(sb.length() - 1);
        	}
        }
        return sb.append("}").toString();
    }

    
    private static String ROUND = "\"";
    
    private String toJson(Object value) throws Exception {
        if (value == null) {
            return "undefined";
        }
        StringBuffer sb = new StringBuffer();

        if (value instanceof Class) {
            this.addRequire(Define.getJSClazzName(((Class<?>) value)));
            return ROUND + Define.getJSClazzName((Class<?>) value) + ROUND;
        }
        Class<?> fieldType = value.getClass();
        if (fieldType == int.class || fieldType == long.class || fieldType == short.class || fieldType == byte.class) {
            if (!Objects.equals(value, 0)) {
                sb.append(value);
            }
        } else if (fieldType == float.class || fieldType == double.class) {
            if (!(Objects.equals(value, 0f) || Objects.equals(value, 0.0))) {
                sb.append(value);
            }
        } else if (fieldType == boolean.class) {
            if ((boolean) value) {
                sb.append(value);
            }
        } else if (fieldType == char.class) {
            sb.append(ROUND).append(value).append(ROUND);
        } else if (fieldType == Boolean.class || fieldType == Integer.class || fieldType == Float.class || fieldType == Double.class || fieldType == Long.class || fieldType == Short.class || fieldType == Byte.class) {
            sb.append(value);
        } else if (fieldType == String.class) {
            sb.append(ROUND).append(value).append(ROUND);
        } else if (fieldType == Class.class) {
            if (Base.class.isAssignableFrom(fieldType)) {
                sb.append(ROUND).append(Define.getJSClazzName(fieldType)).append(ROUND);
                this.addRequire(Define.getJSClazzName(fieldType));
            } else {
                sb.append(ROUND).append(Define.getJSClazzName(fieldType)).append(ROUND);
                this.addRequire(Define.getJSClazzName(fieldType));
            }
        } else if (fieldType.isEnum()) {
            sb.append(ROUND).append(((Enum<?>) value).name()).append(ROUND);
        }
        //
        else if (value instanceof Base) {
            Base b = (Base) value;
            if (b.getClass().getName().contains("$")) {//处理匿名内部类
                Base t = Base.forClass(b.getClass().getSuperclass());
                t.setTargetClazz(b.getClass());
                t.targetObj = b;
                sb.append(t.toJson());
                if (t instanceof Config) {
                } else {
                    this.addRequire(Define.getJSClazzName(t.getClass()));
                }
                this.addRequires(t.getRequires());
            } else {
                sb.append(b.toJson());
                if (b instanceof Config) {
                } else {
                    this.addRequire(Define.getJSClazzName(b.getClass()));
                    this.addRequires(b.getRequires());
                }
            }
        } else if (Base[].class.isAssignableFrom(fieldType)) {
            Base[] bb = ((Base[]) value);
            sb.append("[");
            if (bb.length > 0) {
                for (Base b : bb) {
                    sb.append(toJson(b)).append(",");
                }
                sb.deleteCharAt(sb.length() - 1);
            }
            sb.append("]");
        } else if (Object[].class.isAssignableFrom(fieldType)) {
            Object[] bb = ((Object[]) value);
            sb.append("[");
            if (bb.length > 0) {
                for (Object b : bb) {
                    sb.append(toJson(b)).append(",");
                }
                sb.deleteCharAt(sb.length() - 1);
            }
            sb.append("]");
        } else if (Collection.class.isAssignableFrom(fieldType)) {
            Collection<?> list = (Collection<?>) value;
            sb.append("[");
            if (list.size() > 0) {
                for (Object b : list) {
                    sb.append(toJson(b)).append(",");
                }
                sb.deleteCharAt(sb.length() - 1);
            }
            sb.append("]");
        } else if (Map.class.isAssignableFrom(fieldType)) {
            Map<?, ?> map = (Map<?, ?>) value;
            if (!map.isEmpty()) {
                sb.append("{");
                for (Entry<?, ?> enty : map.entrySet()) {
                    sb.append("\"").append(enty.getKey()).append("\"").append(":").append(toJson(enty.getValue())).append(",");
                }
                sb.deleteCharAt(sb.length() - 1);
                sb.append("}");
            }
        } else if (Define.class.isAssignableFrom(fieldType)) {
            sb.append(((Define) value).toJS());
        } else {
            Method mt = fieldType.getDeclaredMethod("toJS");
            if (mt != null && mt.getReturnType() == String.class) {
                return (String) mt.invoke(value);
            }
            System.out.println("------------未发现的类型--------------fieldType:" + fieldType.getName());
        }
        return sb.toString();
    }
    
    private String toJS(Object value) throws Exception {
        if (value == null) {
            return "undefined";
        }
        StringBuffer sb = new StringBuffer();

        if (value instanceof Class) {
            this.addRequire(Define.getJSClazzName(((Class<?>) value)));
            return "'" + Define.getJSClazzName((Class<?>) value) + "'";
        }
        Class<?> fieldType = value.getClass();
        if (fieldType == int.class || fieldType == long.class || fieldType == short.class || fieldType == byte.class) {
            if (!Objects.equals(value, 0)) {
                sb.append(value);
            }
        } else if (fieldType == float.class || fieldType == double.class) {
            if (!(Objects.equals(value, 0f) || Objects.equals(value, 0.0))) {
                sb.append(value);
            }
        } else if (fieldType == boolean.class) {
            if ((boolean) value) {
                sb.append(value);
            }
        } else if (fieldType == char.class) {
            sb.append("'").append(value).append("'");
        } else if (fieldType == Boolean.class || fieldType == Integer.class || fieldType == Float.class || fieldType == Double.class || fieldType == Long.class || fieldType == Short.class || fieldType == Byte.class) {
            sb.append(value);
        } else if (fieldType == String.class) {
            sb.append("'").append(value).append("'");
        } else if (fieldType == Class.class) {
            if (Base.class.isAssignableFrom(fieldType)) {
                sb.append("'").append(Define.getJSClazzName(fieldType)).append("'");
                this.addRequire(Define.getJSClazzName(fieldType));
            } else {
                sb.append("'").append(Define.getJSClazzName(fieldType)).append("'");
                this.addRequire(Define.getJSClazzName(fieldType));
            }
        } else if (fieldType.isEnum()) {
            sb.append("'").append(((Enum<?>) value).name()).append("'");
        }
        //
        else if (value instanceof Base) {
            Base b = (Base) value;
            if (b.getClass().getName().contains("$")) {//处理匿名内部类
                Base t = Base.forClass(b.getClass().getSuperclass());
                t.setTargetClazz(b.getClass());
                t.targetObj = b;
                sb.append(t.toJS());
                if (t instanceof Config) {
                } else {
                    this.addRequire(Define.getJSClazzName(t.getClass()));
                }
                this.addRequires(t.getRequires());
            } else {
                sb.append(b.toJS());
                if (b instanceof Config) {
                } else {
                    this.addRequire(Define.getJSClazzName(b.getClass()));
                    this.addRequires(b.getRequires());
                }
            }
        } else if (Base[].class.isAssignableFrom(fieldType)) {
            Base[] bb = ((Base[]) value);
            sb.append("[");
            if (bb.length > 0) {
                for (Base b : bb) {
                    sb.append(toJS(b)).append(",");
                    //this.addRequires(b.getRequires());
                }
            }
            sb.append("]");
        } else if (Object[].class.isAssignableFrom(fieldType)) {
            Object[] bb = ((Object[]) value);
            sb.append("[");
            if (bb.length > 0) {
                for (Object b : bb) {
                    sb.append(toJS(b)).append(",");
                }
            }
            sb.append("]");
        } else if (Collection.class.isAssignableFrom(fieldType)) {
            Collection<?> list = (Collection<?>) value;
            sb.append("[");
            if (list.size() > 0) {
                for (Object b : list) {
                    sb.append(toJS(b)).append(",");
                }
            }
            sb.append("]");
        } else if (Map.class.isAssignableFrom(fieldType)) {
            Map<?, ?> map = (Map<?, ?>) value;
            if (!map.isEmpty()) {
                sb.append("{");
                for (Entry<?, ?> enty : map.entrySet()) {
                    sb.append(enty.getKey()).append(":").append(toJS(enty.getValue())).append(",");
                }
                sb.append("}");
            }
        } else if (Define.class.isAssignableFrom(fieldType)) {
            sb.append(((Define) value).toJS());
        } else if (Script.class.isAssignableFrom(fieldType)) {
            sb.append(((Script) value).getScript());
        } else {
            Method mt = fieldType.getDeclaredMethod("toJS");
            if (mt != null && mt.getReturnType() == String.class) {
                return (String) mt.invoke(value);
            }
            System.out.println("------------未发现的类型--------------fieldType:" + fieldType.getName());
        }
        return sb.toString();
    }


    public List<String> getRequires() {
        if (requires == null) {
            requires = new ArrayList<String>();
        }
        return requires;
    }

    public void setRequires(List<String> requires) {
        this.requires = requires;
    }

    public void addRequires(List<String> requires) {
        if (requires != null) {
            for (String str : requires) {
                this.addRequire(str);
            }
        }
    }

    public void addRequire(Class<?> clazz) {
        this.addRequire(Define.getJSClazzName(clazz));
    }

    public void addRequire(String req) {
        if (this.getRequires().indexOf(req) == -1) {
            this.getRequires().add(req);
        }
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    public Boolean getSingleton() {
        return singleton;
    }

    public void setSingleton(Boolean singleton) {
        this.singleton = singleton;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Map<String, Object> getConfig() {
        if (config == null) {
            config = new HashMap<String, Object>();
        }
        return config;
    }

    public void setConfig(Map<String, Object> config) {
        this.config = config;
    }

    public Base addConfig(String key, Object value) {
        this.getConfig().put(key, value);
        return this;
    }

    public Base addProperty(String key, Object value) {
        this.getProperties().put(key, value);
        return this;
    }

    public Map<String, Object> getProperties() {
        if (properties == null) {
            properties = new HashMap<>();
        }
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    public Map<String, Object> getStatics() {
        if (statics == null) {
            statics = new HashMap<String, Object>();
        }
        return statics;
    }

    public Object getStatic(String key) {
        if (statics == null) {
            return null;
        }
        return statics.get(key);
    }

    public void addStatic(String key, Object value) {
        this.getStatics().put(key, value);
    }

    public void setStatics(Map<String, Object> statics) {
        this.statics = statics;
    }

    public List<Define> getDefines() {
        return defines;
    }

    public void setDefines(List<Define> defines) {
        this.defines = defines;
    }

    public void addDefine(Define define) {
        if (defines == null) {
            defines = new ArrayList<Define>();
        }
        defines.add(define);
    }

    public String[] getAlternateClassName() {
        return alternateClassName;
    }

    public void setAlternateClassName(String[] alternateClassName) {
        this.alternateClassName = alternateClassName;
    }

    public Class<?> getTargetClazz() {
        return targetClazz;
    }


    public Object getTarget() throws Exception {
        if (this.targetObj == null) {
            if (this.getClass() == this.targetClazz) {
                this.targetObj = this;
            } else if (this.targetClazz != null) {
                this.targetObj = this.targetClazz.newInstance();
            }
        }
        return this.targetObj;
    }

    public void setTargetClazz(Class<?> targetClazz) {
        this.targetClazz = targetClazz;
    }

    public Object getTargetObj() {
        return targetObj;
    }

    public void setTargetObj(Object targetObj) {
        this.targetObj = targetObj;
    }
}
