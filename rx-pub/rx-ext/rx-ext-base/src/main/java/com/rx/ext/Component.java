package com.rx.ext;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtComp;
import com.rx.ext.annotation.ExtConfig;
import com.rx.ext.annotation.ExtFormField;
import com.rx.ext.annotation.ExtComp.DockEnum;

@ExtClass(/* alias="widget.component" */)
public class Component extends Observable {

	@ExtConfig
	public String controller;

	@ExtConfig()
	public String xtype;

	@ExtConfig()
	public String margin;

	@ExtConfig()
	public String padding;

	@ExtConfig()
	public Boolean frame;
	@ExtConfig()
	public Boolean hidden;

	@ExtConfig()
	public Boolean disabled;

	@ExtConfig
	public Style style;

	@ExtConfig()
	public Class<?> xclass;

	@ExtConfig()
	public String itemId;

	@ExtConfig
	public String reference;

	@ExtConfig()
	public String dock;

	@ExtConfig()
	public Integer ordinal = 0;// 不设置默认值会使排序错误

	@ExtConfig
	public Integer width;

	@ExtConfig
	public Integer height;
	
	
	@ExtConfig
	public Integer flex;

	
	
    //layout
    @ExtConfig()
    public String anchor;
    
    @ExtConfig()
    public Integer rowspan;

    @ExtConfig()
    public Integer colspan;

    @ExtConfig()
    public String cellCls;
	
	
	public void applyAnnotation(Annotation annotation, Field field, Object value) throws Exception {
		
		if(annotation instanceof ExtComp) {
			ExtComp extComp = (ExtComp) annotation;
			if (extComp.flex() != 0) {
                this.flex = extComp.flex();
            }
			
			if (!ExtComp.NULL.equals(extComp.margin())) {
                this.margin = extComp.margin();
            }
			if (!ExtComp.NULL.equals(extComp.padding())) {
                this.padding = extComp.padding();
            }
			//layout
            
            if (!ExtFormField.NULL.equals(extComp.anchor())) {
                this.anchor = extComp.anchor();
            }
            if (extComp.rowspan() != 0) {
                this.rowspan = extComp.rowspan();
            }
            if (extComp.colspan() != 0) {
                this.colspan = extComp.colspan();
            }
			if(extComp.applyer().length > 0) {
				for(Class<? extends CompApplyer> cls: extComp.applyer()) {
					CompApplyer applyer  = cls.newInstance();
					applyer.applyAnnotation(this,annotation,field,value);
				}
			}
		}
		
		super.applyAnnotation(annotation, field, value);
	}
	
	@Override
	public void applyAnnotation(Annotation annotation, Class<?> cls, Object value) throws Exception {
		if (annotation instanceof ExtClass) {
			ExtClass extClass = (ExtClass) annotation;
			if (!ExtClass.NULL.equals(extClass.alias())) {
				if (!this.isDefine) {
					String as = extClass.alias();
					java.lang.String px = "widget.";
					if (as.startsWith(px)) {
						this.setXtype(as.substring(px.length()));
					}
				}
			}
		}
		super.applyAnnotation(annotation, cls, value);
	}

	public String getXtype() {
		return xtype;
	}

	public void setXtype(String xtype) {
		this.xtype = xtype;
	}

	public int getOrdinal() {
		return ordinal;
	}

	public void setOrdinal(int ordinal) {
		this.ordinal = ordinal;
	}

	public String getDock() {
		return dock;
	}

	public void setDock(String dock) {
		this.dock = dock;
	}

	public void setDock(DockEnum dockEnum) {
		if (dockEnum == DockEnum.NULL) {
			this.dock = null;
		} else {
			this.dock = dockEnum.name();
		}
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public Class<?> getXclass() {
		return xclass;
	}

	public void setXclass(Class<?> xclass) {
		this.xclass = xclass;
	}

	public String getPadding() {
		return padding;
	}

	public void setPadding(String padding) {
		this.padding = padding;
	}

	public String getMargin() {
		return margin;
	}

	public void setMargin(String margin) {
		this.margin = margin;
	}

	public Boolean getFrame() {
		return frame;
	}

	public void setFrame(Boolean frame) {
		this.frame = frame;
	}

}
