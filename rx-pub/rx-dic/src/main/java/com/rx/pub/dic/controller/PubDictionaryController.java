package com.rx.pub.dic.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import com.rx.base.utils.StringUtil;
import com.rx.base.utils.UUIDHelper;

import org.springframework.beans.BeanUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rx.base.Showable;
import com.rx.base.enm.EnumUtil;
import com.rx.base.result.DataResult;
import com.rx.base.result.type.ValidateException;
import com.rx.ext.annotation.ExtClass;
import com.rx.extrx.spring.SpringProvider;
import com.rx.model.base.DictionaryDir;
import com.rx.pub.dic.annotation.PermissionDic;
import com.rx.pub.dic.dto.DicAdd;
import com.rx.pub.dic.dto.DicUpdate;
import com.rx.pub.dic.enm.DicPermissionEnum;
import com.rx.pub.dic.po.PubDictionary;
import com.rx.pub.dic.service.IPubDictionaryService;
import com.rx.pub.dic.utils.DictionaryEntity;
import com.rx.pub.dic.utils.DictionaryMgr;

@RestController
@RequestMapping("/pub/dic")
@ExtClass(extend = SpringProvider.class, alternateClassName = "PubDictionaryController")
public class PubDictionaryController {
	@Resource
	private IPubDictionaryService pubDictionaryService;

	@PermissionDic(DicPermissionEnum.新增字典项)
	@RequestMapping("/add")
	public DataResult add(@Validated DicAdd param) throws Exception {

		DictionaryDir dicE = DictionaryMgr.getDictionaryDirByCodeOrName(param.getParentId());
		if (dicE == null) {
			throw new ValidateException("新增失败，未查到所属字典目录");
		}
		
		PubDictionary pd = pubDictionaryService.findPubDictionary(param.getParentId(),param.getValue());
		if( pd != null ){
			throw new ValidateException("新增失败，已存在值为“"+param.getValue()+"”的字典记录，请勿重复添加");
		}
		PubDictionary po = new PubDictionary();
		BeanUtils.copyProperties(param, po);
		try {
			pubDictionaryService.addDictionary(po);
		} catch (Exception e) {
			e.printStackTrace();
			if (e instanceof DuplicateKeyException) {
				if (e.getMessage().indexOf("PRIMARY") != -1 || e.getMessage().indexOf("违反唯一约束条件") != -1)
					throw new ValidateException("新增失败,值重复.");
			}
			throw e;
		}
		return new DataResult("新增成功");
	}

	@PermissionDic(DicPermissionEnum.删除字典项)
	@RequestMapping("/del")
	public DataResult del(String id) throws Exception {
		pubDictionaryService.deleteByPrimaryKey(id);
		return new DataResult("删除成功");
	}

	@PermissionDic(DicPermissionEnum.修改字典项)
	@RequestMapping("/update")
	public DataResult update(@Validated DicUpdate param) throws Exception {

		PubDictionary po = pubDictionaryService.selectByPrimaryKey(param.getId());
		if (po == null) {
			throw new ValidateException("更新失败,未找到字典项");
		}
		PubDictionary pd = pubDictionaryService.findPubDictionary(po.getParentId(),param.getValue());
		if(pd != null && !StringUtil.equals(pd.getId(),po.getId())){
			throw new ValidateException("修改失败，已存在值为“"+param.getValue()+"”的字典记录，请勿重复");
		}
		BeanUtils.copyProperties(param, po);
		pubDictionaryService.updateByPrimaryKeySelective(po);
		return new DataResult("修改成功");
	}

	@PermissionDic(DicPermissionEnum.移动字典顺序)
	@RequestMapping("/updatestair")
	public DataResult updatestair(String id, boolean isUp) throws Exception {
		PubDictionary po = pubDictionaryService.selectByPrimaryKey(id);
		if (po == null) {
			throw new ValidateException("移动失败,未找到字典项");
		}
		pubDictionaryService.updateStair(po, isUp);
		return new DataResult("移动成功");
	}

	//@Permission(PermissionEnum.查询字典项)
	@RequestMapping("/listDicByParentId")
	public DataResult listDicByParentId(String parentId) throws Exception {
		DictionaryDir dicE = DictionaryMgr.getDictionaryDirByCode(parentId);
		if (dicE == null) {
			throw new ValidateException("错误的字典目录");
		}
		
		List<DictionaryEntity> res = new ArrayList<DictionaryEntity>();
		Class<? extends Showable> enm = dicE.getDefaults();
		if(enm != null) {
			for (Showable<?> obj : enm.getEnumConstants()) {
				res.add(new DictionaryEntity(StringUtil.getUUIDPure(),obj.display(),obj.value().toString(),dicE.getCode(),0,true));
			}
		}
		
		List<PubDictionary> list = pubDictionaryService.listEnumDic(dicE);
		
		for(PubDictionary it : list) {
			res.add(new DictionaryEntity(it.getId(),it.getName(),it.getValue(),it.getParentId(),it.getSeq(),false));
		}
		return new DataResult(res);
	}

}
