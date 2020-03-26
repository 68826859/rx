package com.rx.extrx.widget;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import com.rx.base.result.type.ValidateException;
import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtConfig;
import com.rx.ext.annotation.ExtFormField;
import com.rx.ext.data.Store;
import com.rx.ext.data.proxy.Server;
import com.rx.ext.form.field.ComboBox;
import com.rx.base.model.annotation.RxModelField;


@ExtClass(alias="widget.fkcombox",alter="Rx.widget.FkComBox")
public class FkCombox extends ComboBox{
	private static String method = "listModel";
	
	
	@ExtConfig
	public String className;
	
	public FkCombox() throws ClassNotFoundException{
		//super(new SpringProviderStore<ListVo>(TreeVo.class,ExtController.class,method));
	}
	@Override
	public void applyAnnotation(Annotation annotation,Field field,Object value) throws Exception{
		
		if(annotation instanceof ExtFormField){
			String[] args = ((ExtFormField) annotation).args();
			if(args.length > 0){
				this.pageSize = Integer.parseInt(args[0]);
				Store<?> sto = this.getStore();
				if(sto != null) {
					sto.pageSize = this.pageSize;
				}
			}else {
				this.editable = Boolean.FALSE;
			}
			
			if (field != null) {
				RxModelField modelField = field.getAnnotation(RxModelField.class);
                Class<?> cls = modelField.fk();
				if(cls == Object.class){
					throw new ValidateException("外键下拉框缺少外键配置:" + field.getName());
				}
				Store<?> sto = this.getStore();
				if(sto != null) {
					Server server = (Server)this.getStore().getProxy();
		        	server.addExtraParam("className",cls.getName());
				}
		        this.className = cls.getName();
            }
		}
		super.applyAnnotation(annotation,field,value);
	}
}
