package com.rx.pub.dic.utils;

import java.util.List;
import com.rx.base.service.BaseService;
import com.rx.model.base.DictionaryDir;
import com.rx.pub.dic.po.PubDictionary;
import com.rx.pub.dic.service.IPubDictionaryService;
import com.rx.spring.utils.SpringContextHelper;

import java.util.ArrayList;

public class DictionaryMgr {
	private static List<DictionaryDir> dictionaryDirs = new ArrayList<DictionaryDir>();

	private static List<Class<? extends DictionaryDir>> dictionaryEnums = new ArrayList<Class<? extends DictionaryDir>>();

	public static void registerDictionarys(Class<? extends DictionaryDir> es) {

		if (dictionaryEnums.contains(es)) {
			return;
		}
		allDictionaryDir = null;
		dictionaryEnums.add(es);
		DictionaryDir[] em = (DictionaryDir[]) es.getEnumConstants();
		for (DictionaryDir ee : em) {
			dictionaryDirs.add(ee);
		}
	}

	private static List<DictionaryDirEntity> allDictionaryDir = null;

	public static List<DictionaryDirEntity> getAllDictionaryDirs() {
		if (allDictionaryDir == null) {
			allDictionaryDir = new ArrayList<DictionaryDirEntity>();
			for (DictionaryDir li : dictionaryDirs) {
				allDictionaryDir.add(new DictionaryDirEntity(li));
			}
		}
		return allDictionaryDir;
	}

	public static DictionaryDir getDictionaryDirByCode(String code) {
		for (Class<? extends DictionaryDir> it : dictionaryEnums) {
			for(DictionaryDir dir:it.getEnumConstants()) {
				if(dir.getCode().equals(code)) {
					return dir;
				}
			}
		}
		return null;
	}
	
	public static DictionaryDir getDictionaryDirByCodeOrName(String codeOrName) {
		for (DictionaryDir it : dictionaryDirs) {
			if (it.getCode().equals(codeOrName) || it.getName().equals(codeOrName)) {
				return new DictionaryDirEntity(it);
			}
		}
		return null;
	}

	public static List<PubDictionary> listEnumDic(DictionaryDir dicE) {
		BaseService<?> service = SpringContextHelper.getBeanService(PubDictionary.class);
		if (service != null) {
			IPubDictionaryService ps = (IPubDictionaryService) service;
			return ps.listEnumDic(dicE);
		}
		return null;
	}
}
