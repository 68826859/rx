package com.rx.extrx.widget;

import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtConfig;
import com.rx.ext.data.Model;
import com.rx.ext.data.Store;
import com.rx.ext.form.field.Base;
import com.rx.ext.grid.column.Column;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

@ExtClass(alias = "widget.remotecolumn")
public class RemoteColumn extends Column {


    @ExtConfig
    private Store<?> store;

    @ExtConfig
    private String displayField;


    public RemoteColumn() {
    }

    @Override
    public void applyAnnotation(Annotation annotation, Field field, Object value) throws Exception {
        super.applyAnnotation(annotation, field, value);
    }

    public Store<?> getStore() {
        return store;
    }

    public void setStore(Store<?> store) {
        if (store != null) {
            store.setAutoLoad(Boolean.TRUE);
            if (store.getModel() != null) {
                Model md = null;
                try {
                    md = (Model) Base.forClass(store.getModel());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                this.setDisplayField(md.getDisplayProperty());
            }
        }
        this.store = store;
    }

    public String getDisplayField() {
        return displayField;
    }

    public void setDisplayField(String displayField) {
        this.displayField = displayField;
    }
}
