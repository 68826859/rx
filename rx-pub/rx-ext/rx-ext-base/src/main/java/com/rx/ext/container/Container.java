package com.rx.ext.container;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import com.rx.ext.Base;
import com.rx.ext.Component;
import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtComp;
import com.rx.ext.annotation.ExtConfig;
import com.rx.ext.layout.Layout;
import com.rx.ext.layout.LayoutEnum;

@ExtClass(alias="widget.container")
public class Container extends Component{

	//@ExtComp(dock=DockEnum.top)
	
	public Container(){
	
	}
	
	public Container(Layout layout){
		this.layout = layout;
	}
	
	
	@ExtConfig(key="layout")
	protected String layoutEnum;
	
	@ExtConfig(key="layout")
	protected Layout layout;
	
	@ExtConfig(key="defaults")
	protected Map<String,Object> defaults;
	
	
	@ExtConfig(tag="com.rx.ext.container.Container.items")
	protected List<Component> items;
	
	
	@Override
	protected void applyField(Field field,Object value,boolean baseSelf) throws Exception{
		
	if(!baseSelf && value != null){
			if(value instanceof Layout){
				this.setLayout((Layout)value);
			}else if(value instanceof LayoutEnum){
				this.layoutEnum = ((LayoutEnum)value).name();
			}
	}
	super.applyField(field, value, baseSelf);
	}
	
	
	@Override
	public void applyAnnotation(Annotation annotation,Field field,Object value) throws Exception{
		if(annotation instanceof ExtComp){
				ExtComp extComp = (ExtComp)annotation;
				if(value != null && !BeanUtils.isSimpleProperty(value.getClass())){
					Component comp = null;
					boolean noDock = ExtComp.DockEnum.NULL.equals(extComp.dock());
					if(value instanceof Class){
						if(noDock){
							comp = new Component();
							comp.setXclass((Class<?>)value);
						}
					}else if(value instanceof Component){
						comp = (Component)value;
					}else if(value instanceof Object){
						if(noDock){
							Class<?> tar = value.getClass();
							if(tar.getName().indexOf("$") != -1){//处理匿名内部类
								tar = tar.getSuperclass();
							}
							comp = (Component)Base.forClass(tar);
							comp.setTargetClazz(tar);
							comp.setTargetObj(value);
						}
					}
					if(comp != null && comp.getItemId() == null){
						if(ExtComp.NULL.equals(extComp.itemId())){
							comp.setItemId(field.getName());
						}else{
							comp.setItemId(extComp.itemId());
						}
					}
					
					if(noDock && (comp.getDock() == null || ExtComp.DockEnum.NULL.name().equals(comp.getDock()))){
						comp.setOrdinal(extComp.ordinal());
						if(ExtComp.NULL.equals(extComp.ct())) {
							this.addItem(comp);
						}else {
							Component ct = this.lookupItemByItemId(extComp.ct());
							if(ct == null) {
								throw new RuntimeException("表单"+this.getTargetClazz().getName()+"元素"+comp.getItemId()+" 指向的ct容器"+extComp.ct()+"找不到对应的组件");
							}else if(ct instanceof Container) {
								((Container)ct).addItem(comp);
							}else {
								throw new RuntimeException("表单"+this.getTargetClazz().getName()+"元素"+comp.getItemId()+" 指向的ct容器"+extComp.ct()+"不是容器组件");
							}
						}
					}
				}
		}else if(annotation instanceof ExtConfig){
				if(annotation instanceof ExtConfig){
					ExtConfig cfg = (ExtConfig)annotation;
					if(cfg.tag().equals("com.rx.ext.container.Container.items")){
						if(this.items != null && this.items.size() > 0){
							Collections.sort(this.items,new Comparator<Component>(){
								@Override
								public int compare(Component o1, Component o2) {
									return o1.getOrdinal() > o2.getOrdinal()?-1:1;
								}
							});
						}
					}
				}
		}
		super.applyAnnotation(annotation,field,value);
	}
	
	
	
	public List<Component> getItems() {
		if(this.items == null){
			this.items = new ArrayList<Component>();
		}
		return items;
	}
	
	
	public Map<String, Object> getDefaults() {
		if(this.defaults == null){
			this.defaults = new HashMap<>();
		}
		return this.defaults;
	}
	
	public void addDefaults(String key,Object value) {
		this.getDefaults().put(key,value);
	}
	
	
	
	public Component lookupItemByItemId(String itemId) {
		for (Component component : getItems()) {
			if(itemId.equals(component.getItemId())) {
				return component;
			}else if(component instanceof Container) {
				Component comp  = ((Container)component).lookupItemByItemId(itemId);
				if(comp != null) {
					return comp;
				}
			}
		}
		return null;
	}
	
	public Component getItemByItemId(String itemId) {
		for (Component component : items) {
			if(itemId.equals(component.getItemId())) {
				return component;
			}
		}
		return null;
	}
	public void setItems(List<Component> items) {
		this.items = items;
	}
	
	public void addItem(Component item) {
		this.getItems().add(item);
	}

	public Layout getLayout() {
		return layout;
	}

	public Container setLayout(Layout layout) {
		this.layout = layout;
		return this;
	}
	public Container setLayout(String layout) {
		this.layoutEnum = layout;
		return this;
	}
}
