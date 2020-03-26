package com.rx.extrx.widget;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtConfig;
import com.rx.ext.annotation.ExtGridColumn;
import com.rx.ext.grid.column.Column;

@ExtClass(alias="widget.redirectcolumn",alter="Rx.widget.RedirectColumn")
public class RedirectColumn extends Column{
	
	@ExtConfig()
	private String redirectTarget;
	
	public RedirectColumn(){}
	
	@Override
	public void applyAnnotation(Annotation annotation,Field field,Object value) throws Exception{
		if(annotation instanceof ExtGridColumn){
			String[] args = ((ExtGridColumn) annotation).args();
			if(args.length > 0){
				redirectTarget = args[0];
			}
		}
		super.applyAnnotation(annotation,field,value);
	}
}
