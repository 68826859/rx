package com.rx.ext.panel;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.rx.ext.Base;
import com.rx.ext.Component;
import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtComp;
import com.rx.ext.annotation.ExtConfig;
import com.rx.ext.container.Container;

@ExtClass(alias = "widget.panel")
public class Panel extends Container {
	@ExtConfig
	public String title;

	@ExtConfig(key = "title")
	public Title titleCfg;

	@ExtConfig(tag = "com.rx.ext.panel.Panel.dockedItems")
	protected List<Component> dockedItems;

	@Override
	public void applyAnnotation(Annotation annotation, Field field, Object value) throws Exception {
		if (annotation instanceof ExtComp) {
			ExtComp extClass = (ExtComp) annotation;
			if (value != null && !BeanUtils.isSimpleProperty(value.getClass())) {
				Component comp = null;
				boolean hasDock = !ExtComp.DockEnum.NULL.equals(extClass.dock());
				if (value instanceof Class) {
					if (hasDock) {
						comp = new Component();
						comp.setXclass((Class<?>) value);
					}
				} else if (value instanceof Component) {
					comp = (Component) value;
				} else if (value instanceof Object) {
					if (hasDock) {
						Class<?> tar = value.getClass();
						if (tar.getName().indexOf("$") != -1) {// 处理匿名内部类
							tar = tar.getSuperclass();
						}
						comp = (Component) Base.forClass(tar);
						comp.setTargetClazz(tar);
						comp.setTargetObj(value);
					}
				}
				if (comp != null && comp.getItemId() == null) {
					if (ExtComp.NULL.equals(extClass.itemId())) {
						comp.setItemId(field.getName());
					} else {
						comp.setItemId(extClass.itemId());
					}
				}
				if (hasDock) {
					comp.setOrdinal(extClass.ordinal());
					comp.setDock(extClass.dock());
					this.addDockedItem(comp);
				} else if (comp != null && comp.getDock() != null) {
					this.addDockedItem(comp);
				}
				// super.applyAnnotation(annotation,field,comp);
				// return;
			}
		}
		super.applyAnnotation(annotation, field, value);
	}

	public List<Component> getDockedItems() {
		if (this.dockedItems == null) {
			this.dockedItems = new ArrayList<Component>();
		}
		return this.dockedItems;
	}

	public void setDockedItems(List<Component> items) {
		this.dockedItems = items;
	}

	public void addDockedItem(Component item) {
		this.getDockedItems().add(item);
	}
}
