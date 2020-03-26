package com.rx.ext.grid;

import com.rx.base.enm.RxDatePattern;
import com.rx.base.enm.RxNullEnum;
import com.rx.base.model.annotation.RxModelField;
import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtConfig;
import com.rx.ext.annotation.ExtGridColumn;
import com.rx.ext.data.Store;
import com.rx.ext.enums.NullEnum;
import com.rx.ext.grid.column.Column;
import com.rx.ext.grid.column.Date;
import com.rx.ext.panel.Table;
import com.rx.extrx.widget.EnumColumn;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@ExtClass(alias="widget.gridpanel")
public class Panel extends Table{
	@ExtConfig(tag="com.rx.ext.grid.Panel.columns")
	private List<Column> columns;
	
	
	@ExtConfig()
	private Store<?> store;
	
	
	public Panel(){
		
	}
	
	public Panel(Store<?> store,Class<Column> clazz){
		this.setStore(store);
		this.setColumnClass(clazz);
	}
	
	public List<Column> getColumns() {
		if(this.columns == null){
			this.columns = new ArrayList<Column>();
		}
		return this.columns;
	}
	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}
	
	public void setColumnClass(Class<?> clazz){
		try {
			this.applyClass(clazz,null,false);
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void addColumn(Column column) {
		this.getColumns().add(column);
	}
	
	public Store<?> getStore() {
		return store;
	}
	public void setStore(Store<?> store){
		this.store = store;
	}
	
	@Override
	public void applyAnnotation(Annotation annotation,Field field,Object value) throws Exception{
		if(annotation instanceof ExtGridColumn){
			ExtGridColumn columnField = (ExtGridColumn)annotation;
			Column f = null;
			if(value != null && value instanceof Column){
				f = (Column)value;
			}else{
				Class<? extends Column> cls = columnField.type();
				if(cls == Column.class){
                    RxModelField mf = field.getAnnotation(RxModelField.class);
                    if(mf != null){
                        if(!RxNullEnum.class.equals(mf.em())){
                            cls = EnumColumn.class;
                        }
                        if(RxDatePattern.NULL != mf.datePattern()){
                            cls = Date.class;
                        }
                    }
					if(RxDatePattern.NULL != columnField.datePattern()){
						cls = Date.class;
					}
					if(columnField.em() != NullEnum.class){
						cls = EnumColumn.class;
					}
				}
				f = cls.newInstance();
			}
			f.applyAnnotation(columnField,field,value);
			this.addColumn(f);
		}else{
			if(annotation instanceof ExtConfig){
				ExtConfig cfg = (ExtConfig)annotation;
				if(cfg.tag().equals("com.rx.ext.grid.Panel.columns")){
					if(this.columns != null && this.columns.size() > 0){
						Collections.sort(this.columns,new Comparator<Column>(){
							@Override
							public int compare(Column o1, Column o2) {
								return o1.getOrdinal() > o2.getOrdinal()?-1:1;
							}
						});
					}
				}
			}
			super.applyAnnotation(annotation, field, value);
		}
	}
}
