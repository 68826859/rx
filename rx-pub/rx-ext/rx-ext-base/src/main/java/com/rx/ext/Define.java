package com.rx.ext;

import com.rx.ext.annotation.ExtClass;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Define {
    private static Map<String, String> classPathMappings = new HashMap<String, String>() {
        private static final long serialVersionUID = 1L;

        {
            put("com.rx.ext.", "Ext.");
            put("com.rx.extrx.", "Rx.");
        }
    };

    public static void registerClassPathMapping(String javaPath, String jsPath) {
        classPathMappings.put(javaPath, jsPath);
    }

    public static String getJSClazzName(Class<?> clazz) {

        ExtClass extClass = clazz.getAnnotation(ExtClass.class);
        if (extClass != null && !ExtClass.NULL.equals(extClass.alter())) {
            return extClass.alter();
        }
        String cN = clazz.getName();
        String res = cN;
        for (Entry<String, String> entry : classPathMappings.entrySet()) {
            if (cN.startsWith(entry.getKey())) {
                res = cN.replace(entry.getKey(), entry.getValue());
            }
        }
        return res;
    }

    private static Map<String, String> clazzJs = new HashMap<String, String>();

    /**
     * 获得一个Class的JS代码
     *
     * @param clazz
     * @return
     * @throws Exception
     */
    public static String define(Class<?> clazz) throws Exception {
        String js = new Define(clazz.getName(), Base.forClass(clazz, true)).toJS();
        return js;
    }

    private Base target;
    private String name;

    public Define(String name, Base target) {
        this.name = name;
        this.target = target;
        target.isDefine = true;
    }


    public String toJS() throws Exception {
        //if(clazzJs.containsKey(name)){
        //	return clazzJs.get(name);
        //}
        StringBuffer sb = new StringBuffer();
        sb.append("Ext.define('").append(name).append("',").append(target.toJS()).append(");");
        List<Define> list = target.getDefines();
        if (list != null && list.size() > 0) {
            for (Define def : list) {
                sb.insert(0, def.toJS());
            }
        }
        String js = sb.toString();
        //clazzJs.put(name, js);
        return js;
    }
}
