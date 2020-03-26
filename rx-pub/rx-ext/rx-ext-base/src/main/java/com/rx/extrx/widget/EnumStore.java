package com.rx.extrx.widget;

import com.rx.ext.Define;
import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtConfig;
import com.rx.ext.annotation.ExtGridColumn;
import com.rx.ext.data.Model;
import com.rx.ext.data.Store;
import com.rx.ext.data.proxy.Memory;
import com.rx.ext.enums.NullEnum;
import com.rx.base.model.annotation.RxModelField;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ExtClass(alias = "store.enumstore")
public class EnumStore extends Store<Map<String, Object>> {
    @ExtConfig()
    private Class<? extends Enum<?>> em;

    @ExtConfig()
    private String model;

    private Model realModel;

    //	public String getModel() {
//		return this.model;
//	}
    public void setModel(String model) {
        this.model = model;
    }

    public EnumStore() {

    }

    public EnumStore(Class<? extends Enum<?>> em) {
        try {
            this.applyEnum(em);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void applyEnum(Class<? extends Enum<?>> em) throws Exception {

        Model mod = new Model();
        Field[] fs = em.getDeclaredFields();
        for (Field f : fs) {
            RxModelField an = f.getAnnotation(RxModelField.class);
            if (an != null) {
                mod.applyAnnotation(an, f, null);
            }
        }
        com.rx.ext.data.field.Field fd = new com.rx.ext.data.field.String();
        fd.setName("name");

        if (mod.getIdProperty() == null) {
            mod.setIdProperty("name");
        }
        if (mod.getDisplayProperty() == null) {
            mod.setDisplayProperty("name");
        }
        mod.addField(fd);
        mod.setExtend(Define.getJSClazzName(Model.class));

        String moName = em.getName() + "$Model";
        this.setModel(moName);
        this.setRealModel(mod);
        this.addDefine(new Define(moName, mod));
        this.setProxy(new Memory());
        this.setAutoLoad(Boolean.TRUE);

        Enum<? extends Enum<?>>[] enm = em.getEnumConstants();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map;
        for (Enum<? extends Enum<?>> e : enm) {
            map = new HashMap<String, Object>();
            String id = e.name();
            map.put("name", id);
            for (Field f : fs) {
                f.setAccessible(true);
                RxModelField an = f.getAnnotation(RxModelField.class);
                if (an != null) {
                    String name = RxModelField.NULL.equals(an.name()) ? f.getName() : an.name();
                    map.put(name, f.get(e));
                    if (an.isID()) {
                        id = name;
                    }
                }
            }
            this.addStatic(id, map);
            list.add(map);

        }

        this.setData(list);
    }


    @Override
    public void applyAnnotation(Annotation annotation, Field field, Object value) throws Exception {
        if (annotation instanceof ExtGridColumn) {
            ExtGridColumn columnField = (ExtGridColumn) annotation;
            Class<? extends Enum<?>> ecls = columnField.em();
            if (ecls != NullEnum.class) {
                this.em = ecls;
                this.applyEnum(em);
            }
        } else {
            super.applyAnnotation(annotation, field, value);
        }
    }

    public Class<?> getEm() {
        return em;
    }

    public void setEm(Class<? extends Enum<?>> em) {
        this.em = em;
    }

    public Model getRealModel() {
        return realModel;
    }

    public void setRealModel(Model realModel) {
        this.realModel = realModel;
    }

}
