package com.rx.extrx.model;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.rx.base.enm.RxDatePattern;
import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtFormField;
import com.rx.ext.annotation.ExtGridColumn;
import com.rx.ext.data.Model;
import com.rx.ext.enums.NullEnum;
import com.rx.ext.form.field.Base;
import com.rx.ext.form.field.Text;
import com.rx.ext.grid.column.Column;
import com.rx.ext.grid.column.Date;
import com.rx.extrx.component.EnumComboBox;
import com.rx.extrx.widget.EnumColumn;
//import com.rx.base.enm.RxDatePattern;

@ExtClass(alter="Rx.model.SimpleModel")
public class SimpleModel extends Model{
	
	@Override
	public void applyAnnotation(Annotation annotation,Field field,Object value) throws Exception{
	if(annotation instanceof ExtGridColumn){
			ExtGridColumn columnField = (ExtGridColumn)annotation;
			Class<? extends Column> cls = columnField.type();
			if(cls == Column.class){
				if(RxDatePattern.NULL != columnField.datePattern()){
					cls = Date.class;
				}
				Class<? extends Enum<?>> ecls = columnField.em();
				if(ecls != NullEnum.class){
					cls = EnumColumn.class;
				}
			}
			this.addRequire(cls);
			Column f = cls.newInstance();
			f.applyAnnotation(columnField,field,value);
			//super.applyAnnotation(annotation, field, value);
			
			List<Column> columns = (List<Column>)this.getStatic("columns");
			if(columns == null){
				columns = new ArrayList<Column>();
				this.addStatic("columns", columns);
			}
			columns.add(f);
			
	}else if(annotation instanceof ExtFormField){
			
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
				}
			}
			
			this.addRequire(cls);
			Base ins = cls.newInstance();
			ins.applyAnnotation(formField,field,value);
			
			List<Base> formfields = (List<Base>)this.getStatic("formfields");
			if(formfields == null){
				formfields = new ArrayList<Base>();
				this.addStatic("formfields", formfields);
			}
			formfields.add(ins);
	}
	super.applyAnnotation(annotation, field, value);
	}
}
