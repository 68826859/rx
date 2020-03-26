package com.rx.pub.dic.comp;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import com.rx.base.result.type.ValidateException;
import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtConfig;
import com.rx.ext.annotation.ExtGridColumn;
import com.rx.ext.data.Store;
import com.rx.ext.data.proxy.Server;
import com.rx.extrx.spring.SpringProviderStore;
import com.rx.extrx.widget.RemoteColumn;
import com.rx.model.base.DictionaryDir;
import com.rx.pub.dic.controller.PubDictionaryController;
import com.rx.pub.dic.utils.DictionaryMgr;
import com.rx.pub.dic.vo.DicOption;

@ExtClass(alias="widget.diccolumn",alter="Rx.widget.DicColumn")
public class DicColumn extends RemoteColumn{
	
	
	private static String method="listDicByParentId";
	
	@ExtConfig
	public String parentId;
	
	public DicColumn(){
		Store<DicOption> sto = new SpringProviderStore<DicOption>(DicOption.class,PubDictionaryController.class,method);
		this.setStore(sto);
	}
	
	public DicColumn(String parentId){
		this();
		this.parentId = parentId;
	}
	
	@Override
	public void applyAnnotation(Annotation annotation,Field field,Object value) throws Exception{
		if(annotation instanceof ExtGridColumn){
				String[] args = ((ExtGridColumn) annotation).args();
				if(args.length < 1){
					throw new ValidateException("字典列缺少args配置:" + field.getName());
				}
		        Server server = (Server)this.getStore().getProxy();
		        DictionaryDir dir = DictionaryMgr.getDictionaryDirByCodeOrName(args[0]);
				if(dir == null){
					throw new ValidateException("字典列:"+field.getName()+" 找不到args对应的字典配置:"+args[0]);
				}
		        server.addExtraParam("parentId",dir.getCode());
		        this.parentId = dir.getCode();
		}
		super.applyAnnotation(annotation,field,value);
	}
}
