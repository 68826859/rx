package com.rx.extrx.widget;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtConfig;
import com.rx.ext.annotation.ExtGridColumn;
import com.rx.ext.grid.column.Column;

@ExtClass(alias="widget.rendercolumn",alter="Rx.widget.RenderColumn")
public class RenderColumn extends Column{
	
	
	public final static String imgUrlRender = "Rx.widget.RenderColumn.imgUrlRender";
	public final static String booleanRender = "Rx.widget.RenderColumn.booleanRender";
	
	@ExtConfig()
	private String renderFn;
	
	public RenderColumn(){}
	
	@Override
	public void applyAnnotation(Annotation annotation,Field field,Object value) throws Exception{
		if(annotation instanceof ExtGridColumn){
			String[] args = ((ExtGridColumn) annotation).args();
			if(args.length > 0){
				renderFn = args[0];
			}
		}
		super.applyAnnotation(annotation,field,value);
	}
}
