package com.rx.ext.form.field;

import com.rx.base.model.annotation.RxModelField;
import com.rx.ext.Component;
import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtConfig;
import com.rx.ext.annotation.ExtFormField;
import com.rx.ext.enums.FormInputType;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigDecimal;

@ExtClass(alias = "widget.field")
public class Base extends Component {

    @ExtConfig()
    private FormInputType inputType = FormInputType.文本;
    @ExtConfig()
    private String name;

    @ExtConfig()
    private Object value;
    @ExtConfig()
    private String fieldLabel;
    @ExtConfig()
    private String labelSeparator;

    @ExtConfig()
    private Boolean hideLabel;
    
    
    @ExtConfig()
    private Boolean readOnly;

    @ExtConfig()
    public Integer labelWidth;

    
    



    public Base() {
    }

    @Override
    public void applyAnnotation(Annotation annotation, Field field, Object value) throws Exception {
        if (annotation instanceof ExtConfig && field != null && field.getDeclaringClass() == Base.class && field.getType() == FormInputType.class) {
            FormInputType t = (FormInputType) field.get(this);
            if (t != null && t != FormInputType.文本) {
                this.addConfig(field.getName(), t.getType());
            }
            return;
        } else if (annotation instanceof ExtFormField) {
            ExtFormField formField = (ExtFormField) annotation;


            RxModelField modelField = null;
            if (field != null) {
                modelField = field.getAnnotation(RxModelField.class);
            }


            if (!ExtFormField.NULL.equals(formField.label())) {
                this.setFieldLabel(formField.label());
            } else if (modelField != null && !RxModelField.NULL.equals(modelField.text())) {
                this.setFieldLabel(modelField.text());
            }


            if (!ExtFormField.default_labelSeparator.equals(formField.labelSeparator())) {
                this.setLabelSeparator(formField.labelSeparator());
            }
            if (formField.readOnly()) {
                this.setReadOnly(Boolean.TRUE);
            }
            this.setInputType(formField.inputType());

            String name = formField.name();

            if (ExtFormField.NULL.equals(name)) {
                if (modelField != null && !RxModelField.NULL.equals(modelField.name())) {
                    name = modelField.name();
                } else if (field != null) {
                    name = field.getName();
                }
            }
            this.setName(name);

            if (ExtFormField.NULL.equals(formField.itemId())) {
                this.setItemId(name);
            } else {
                this.setItemId(formField.itemId());
            }

            if (value != null) {
                this.setValue(value);
            }
            this.setOrdinal(formField.ordinal());

            
            if (formField.hideLabel()) {
                this.hideLabel = Boolean.TRUE;
            }
            
            if (formField.labelWidth() != 0) {
                labelWidth = formField.labelWidth();
            }
            if (formField.width() != 0) {
                this.width = formField.width();
            }
            if (formField.height() != 0) {
                this.height = formField.height();
            }
            if (!ExtFormField.NULL.equals(formField.cellCls())) {
                this.cellCls = formField.cellCls();
            }
            if(formField.config().length > 0) {
                for(ExtConfig ec : formField.config()) {
                    if(Boolean.class.isAssignableFrom(ec.valueType())){
                        this.addConfig(ec.key(), Boolean.parseBoolean(ec.value()));
                    }
                    else if(java.lang.Number.class.isAssignableFrom(ec.valueType())){
                        if(ec.valueType()==Integer.class || ec.valueType()==Long.class || ec.valueType()==short.class) {
                            this.addConfig(ec.key(), Long.parseLong(ec.value()));
                        }else if(ec.valueType()==Double.class || ec.valueType()==Float.class){
                            this.addConfig(ec.key(), Double.parseDouble(ec.value()));
                        }else{
                            this.addConfig(ec.key(), new BigDecimal(ec.value()));
                        }
                    }else {
                        this.addConfig(ec.key(), ec.value());
                    }
                }
            }

        }
        super.applyAnnotation(annotation, field, value);

    }


    public FormInputType getInputType() {
        return inputType;
    }

    public Base setInputType(FormInputType inputType) {
        this.inputType = inputType;
        return this;
    }

    public String getName() {
        return name;
    }

    public Base setName(String name) {
        this.name = name;
        return this;
    }


    public String getFieldLabel() {
        return fieldLabel;
    }

    public void setFieldLabel(String fieldLabel) {
        this.fieldLabel = fieldLabel;
    }

    public Boolean getReadOnly() {
        return readOnly;
    }

    public Base setReadOnly(Boolean readOnly) {
        this.readOnly = readOnly;
        return this;
    }

    public String getLabelSeparator() {
        return labelSeparator;
    }

    public void setLabelSeparator(String labelSeparator) {
        this.labelSeparator = labelSeparator;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
