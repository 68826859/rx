package com.rx.pub.dic.service.impl;

import com.rx.base.result.type.BusinessException;
import com.rx.model.base.DictionaryDir;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.rx.pub.dic.mapper.PubDictionaryMapper;
import com.rx.pub.dic.po.PubDictionary;
import com.rx.pub.dic.service.IPubDictionaryService;
import tk.mybatis.mapper.entity.Example;
import com.rx.pub.mybatis.impls.MybatisBaseService;

@Service
public class PubDictionaryServiceImpl extends MybatisBaseService<PubDictionary> implements IPubDictionaryService {

	@Resource
	private PubDictionaryMapper pubDictionaryMapper;

	@Override
	public void addDictionary(PubDictionary dic) {
		dic.setId(dic.getParentId() + "-" + dic.getValue());
		dic.setCreateTime(new Date());
		Example example = new Example(PubDictionary.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("parentId", dic.getParentId());
		dic.setSeq(pubDictionaryMapper.selectCountByExample(example));
		
		try {
			pubDictionaryMapper.insert(dic);
		}catch (Exception e) {
			dic.setId(dic.getParentId() + "-" + dic.getValue()  + "-1");
			pubDictionaryMapper.insert(dic);
		}
	}

	@Override
	public PubDictionary findPubDictionary(String parentId, String value) {
		Example example = new Example(PubDictionary.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("parentId", parentId);
		criteria.andEqualTo("value", value);
		example.setOrderByClause("seq asc");
		List<PubDictionary> resultList = pubDictionaryMapper.selectByExample(example);
		if (null == resultList || resultList.isEmpty())
			return null;
		if (resultList.size() != 1)
			throw new BusinessException("存在多个相同值的字典");
		return resultList.get(0);
	}

	@Override
	public List<PubDictionary> listEnumDic(DictionaryDir dicE) {
		return this.listByParentId(dicE.getCode());
	}

	public List<PubDictionary> listByParentId(String parentId) {
		Example example = new Example(PubDictionary.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("parentId", parentId);
		example.setOrderByClause("seq asc");
		return pubDictionaryMapper.selectByExample(example);
	}

	@Override
	public void updateStair(PubDictionary dic, boolean isUp) {
		List<PubDictionary> list = this.listByParentId(dic.getParentId());
		int index, size = list.size();
		PubDictionary it;
		if (isUp) {
			for (index = size - 1; index >= 0; index--) {
				it = list.get(index);
				if (it.getId().equals(dic.getId()) && index > 0) {
					PubDictionary u = list.get(index - 1);
					u.setSeq(index);
					this.updateByPrimaryKey(u);
					it.setSeq(index - 1);
					index--;
				} else {
					it.setSeq(index);
				}
				this.updateByPrimaryKey(it);
			}
		} else {
			for (index = 0; index < size; index++) {
				it = list.get(index);
				if (it.getId().equals(dic.getId()) && index < size - 1) {
					PubDictionary u = list.get(index + 1);
					u.setSeq(index);
					this.updateByPrimaryKey(u);
					it.setSeq(index + 1);
					index++;
				} else {
					it.setSeq(index);
				}
				this.updateByPrimaryKey(it);
			}
		}
	}
}
