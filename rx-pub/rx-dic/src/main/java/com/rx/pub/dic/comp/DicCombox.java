package com.rx.pub.dic.comp;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import com.rx.base.result.type.ValidateException;
import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtFormField;
import com.rx.ext.data.proxy.Server;
import com.rx.ext.form.field.ComboBox;
import com.rx.extrx.spring.SpringProviderStore;
import com.rx.model.base.DictionaryDir;
import com.rx.pub.dic.controller.PubDictionaryController;
import com.rx.pub.dic.utils.DictionaryMgr;
import com.rx.pub.dic.vo.DicOption;


@ExtClass(alias="widget.diccombox",alter="Rx.widget.DicComboBox")
public class DicCombox extends ComboBox{
	private static String method = "listDicByParentId";
	
	public DicCombox(){
		super();
		SpringProviderStore<DicOption> sto = new SpringProviderStore<DicOption>(DicOption.class,PubDictionaryController.class,method);
		this.setStore(sto);
	}
	@Override
	public void applyAnnotation(Annotation annotation,Field field,Object value) throws Exception{
		if(annotation instanceof ExtFormField){
				ExtFormField formField = (ExtFormField)annotation;
				String[] args = formField.args();
				if(args.length < 1){
					throw new ValidateException("字典字段缺少args配置:" + field.getName());
				}
		        Server server = (Server)this.getStore().getProxy();
		        DictionaryDir dir = DictionaryMgr.getDictionaryDirByCodeOrName(args[0]);
				if(dir == null){
					throw new ValidateException("字典字段:"+field.getName()+" 找不到args对应的字典配置:"+args[0]);
				}
		        server.addExtraParam("parentId",dir.getCode());
		}
		super.applyAnnotation(annotation,field,value);
	}
}
