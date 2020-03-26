package com.rx.ext.form;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import com.rx.base.enm.RxNullEnum;
import com.rx.base.model.annotation.RxModelField;
import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtComp;
import com.rx.ext.annotation.ExtConfig;
import com.rx.ext.annotation.ExtFormAction;
import com.rx.ext.annotation.ExtFormField;
import com.rx.ext.enums.NullEnum;
import com.rx.ext.form.action.Action;
import com.rx.ext.form.field.Base;
import com.rx.ext.form.field.Text;
import com.rx.extrx.component.EnumComboBox;
@ExtClass(alias="widget.form")
public class Panel extends com.rx.ext.panel.Panel{
	public Panel(){
		
	}
	
	@ExtConfig
	private List<Action> formActions;
	public List<Action> getFormActions(){
		if(formActions == null){
				formActions = new ArrayList<Action>();
		}
		return formActions;
	}
	public void setActions(List<Action> actions) {
		this.formActions = actions;
	}
	public void addAction(Action action) {
		this.getFormActions().add(action);
	}
	
	
	
	@Override
	public void applyAnnotation(Annotation annotation,Field field,Object value) throws Exception{
		if(annotation instanceof ExtFormField){
			ExtFormField formField = (ExtFormField)annotation;
			Class<? extends Base> cls = formField.comp();
			if(cls == Base.class){
				cls = Text.class;
				if(field.getType() == java.util.Date.class){
					cls = com.rx.ext.form.field.Date.class;
				}
				Class<? extends Enum<?>> ecls = formField.em();
				if(ecls != NullEnum.class){
					cls = EnumComboBox.class;
				}else{
					RxModelField mf = field.getAnnotation(RxModelField.class);
					if(mf != null && !RxNullEnum.class.equals(mf.em())){
						cls = EnumComboBox.class;
					}
				}
			}
			Base comp = cls.newInstance();
			
			comp.applyAnnotation(formField,field,value);
			
			ExtComp extComp = field.getAnnotation(ExtComp.class);
			
			if(extComp != null) {
				comp.applyAnnotation(extComp,field,value);
				super.applyAnnotation(extComp,field,comp);
			}else {
				this.addItem(comp);
			}
			
			
		}else if(annotation instanceof ExtFormAction){
			ExtFormAction formAction = (ExtFormAction)annotation;
			if(Action.class.isAssignableFrom(field.getType())){
				if(value != null){
					Action act = (Action)value;
					act.setType(formAction.type().name());
					this.addAction(act);
				}
			}
		}else{
			super.applyAnnotation(annotation,field,value);
		}
	}

}
