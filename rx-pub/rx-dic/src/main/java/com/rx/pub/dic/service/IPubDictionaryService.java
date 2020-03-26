package com.rx.pub.dic.service;

import com.rx.base.service.BaseService;
import com.rx.model.base.DictionaryDir;
import com.rx.pub.dic.po.PubDictionary;
import java.util.List;
	public interface IPubDictionaryService extends BaseService<PubDictionary> {
	public List<PubDictionary> listEnumDic(DictionaryDir dicE);
	public void updateStair(PubDictionary dic,boolean isUp);
	public void addDictionary(PubDictionary dic);
	/**
	　　* @Description:获取字典
	　　* @param
	 			parentId:DictionaryEnum->code,
	 			PubDictionary->value
	　　* @return ${return_type}
	　　* @throws
	　　* @author zhanghanrui
	　　* @date 2018-7-6 17:31
	　　*/
	PubDictionary findPubDictionary(String parentId,String value);
}
