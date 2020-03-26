package com.rx.ext.grid.column;

import com.rx.base.model.annotation.RxModelField;
import com.rx.ext.Component;
import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtConfig;
import com.rx.ext.annotation.ExtGridColumn;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

@ExtClass(alias="widget.gridcolumn")
public class Column extends Component{
	
	@ExtConfig()
	private String text;
	
	@ExtConfig()
	private String dataIndex;
	@ExtConfig()
	private Boolean hidden;//是否隐藏
	
	@ExtConfig()
	private Boolean sortable;//可否排序
	
	@ExtConfig()
	private Boolean draggable;
	
	@ExtConfig()
	private Boolean enableColumnHide;
	
	@ExtConfig()
	private Boolean menuDisabled;
	
	@ExtConfig()
	private Integer width;
	
	@ExtConfig()
	private Integer minWidth;
	
	@ExtConfig()
	private Integer flex;
	
	
	@Override
	public void applyAnnotation(Annotation annotation,Field field,Object value) throws Exception{
		
		if(annotation instanceof ExtGridColumn){
			ExtGridColumn columnField = (ExtGridColumn)annotation;
			
			RxModelField modelField = null;
			if(field != null){
				modelField = field.getAnnotation(RxModelField.class);
			}
			
			
			if(!ExtGridColumn.NULL.equals(columnField.text())){
				this.setText(columnField.text());
			}else if(modelField != null && !RxModelField.NULL.equals(modelField.text())){
				this.setText(modelField.text());
			}
			
			
			if(ExtGridColumn.NULL.equals(columnField.dataIndex())){
				if(modelField != null && !RxModelField.NULL.equals(modelField.name())){
					this.setDataIndex(modelField.name());
				}else if(field != null){
					this.setDataIndex(field.getName());
				}
			}else{
				this.setDataIndex(columnField.dataIndex());
			}
			
			
			this.setHidden(Boolean.valueOf(columnField.hidden()));
			this.setSortable(Boolean.valueOf(columnField.sortable()));
			this.setDraggable(Boolean.valueOf(columnField.draggable()));
			this.setEnableColumnHide(Boolean.valueOf(columnField.enableColumnHide()));
			this.setMenuDisabled(Boolean.valueOf(columnField.menuDisabled()));
			
			/*if(columnField.hidden()){
				this.setHidden(Boolean.TRUE);
			}
			if(columnField.sortable()){
				this.setSortable(Boolean.TRUE);
			}
			if(!columnField.draggable()){
				this.setDraggable(Boolean.FALSE);
			}
			if(columnField.enableColumnHide()){
				this.setEnableColumnHide(Boolean.TRUE);
			}
			if(!columnField.menuDisabled()){
				this.setMenuDisabled(Boolean.FALSE);
			}*/
			
			
			if(columnField.flex() != 0){
				this.setFlex(Integer.valueOf(columnField.flex()));
			}
			this.setOrdinal(columnField.ordinal());
			this.setMinWidth(Integer.valueOf(columnField.minWidth()));
			this.setWidth(Integer.valueOf(columnField.width()));
			
			if(ExtGridColumn.NULL.equals(columnField.itemId())){
				if(field != null){
					this.setItemId(field.getName());
				}
			}else{
				this.setItemId(columnField.itemId());
			}
		}
		super.applyAnnotation(annotation, field, value);
	}

	public String getText() {
	return text;
	}

	public void setText(String text) {
	this.text = text;
	}

	public String getDataIndex() {
	return dataIndex;
	}

	public void setDataIndex(String dataIndex) {
	this.dataIndex = dataIndex;
	}

	public Boolean getHidden() {
	return hidden;
	}

	public void setHidden(Boolean hidden) {
	this.hidden = hidden;
	}

	public Boolean getSortable() {
	return sortable;
	}

	public void setSortable(Boolean sortable) {
	this.sortable = sortable;
	}

	public Boolean getDraggable() {
	return draggable;
	}

	public void setDraggable(Boolean draggable) {
	this.draggable = draggable;
	}

	public Boolean getEnableColumnHide() {
	return enableColumnHide;
	}

	public void setEnableColumnHide(Boolean enableColumnHide) {
	this.enableColumnHide = enableColumnHide;
	}

	public Boolean getMenuDisabled() {
	return menuDisabled;
	}

	public void setMenuDisabled(Boolean menuDisabled) {
	this.menuDisabled = menuDisabled;
	}

	public Integer getWidth() {
	return width;
	}

	public void setWidth(Integer width) {
	this.width = width;
	if(this.width < ExtGridColumn.default_width && this.width < this.minWidth){
			this.minWidth = this.width;
	}
	}

	public Integer getMinWidth() {
	return minWidth;
	}

	public void setMinWidth(Integer minWidth) {
	this.minWidth = minWidth;
	}

	public Integer getFlex() {
	return flex;
	}

	public void setFlex(Integer flex) {
	this.flex = flex;
	}

}
