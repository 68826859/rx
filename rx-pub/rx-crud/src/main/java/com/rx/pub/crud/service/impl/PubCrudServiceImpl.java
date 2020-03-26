package com.rx.pub.crud.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import com.rx.base.page.PageExcute;
import com.rx.base.page.Pager;
import com.rx.base.result.type.BusinessException;
import com.rx.base.service.BaseService;
import com.rx.pub.crud.dto.CrudSearchDto;
import com.rx.pub.crud.enm.CrudTypeEnum;
import com.rx.pub.crud.mapper.PubCrudMapper;
import com.rx.pub.crud.po.PubCrud;
import com.rx.pub.crud.service.IPubCrudService;
import com.rx.spring.utils.SpringContextHelper;
import com.rx.web.utils.HttpServletHelper;
import com.rx.pub.mybatis.impls.MybatisBaseService;

@Service
public class PubCrudServiceImpl extends MybatisBaseService<PubCrud> implements IPubCrudService {

	@Resource
	private PubCrudMapper pubCrudMapper;

	@Override
	public void restoreRecycle(String id) throws Exception {
		PubCrud rtRecycle = selectByPrimaryKey(id);
        if (rtRecycle == null){
            throw new BusinessException("不存在此元素");
        }
        if(!CrudTypeEnum.删除.getCode().equals(rtRecycle.getCrudType())) {
        	throw new BusinessException("元素不是回收站元素");
        }
        Object restoreObj = null;
        Class<?> objClass = Class.forName(rtRecycle.getDataClass());
        if (objClass == null){
            throw new BusinessException("该元素类型已不存在");
        }
        if (rtRecycle.getData() != null){
            restoreObj = JSON.toJavaObject(JSON.parseObject(rtRecycle.getData()), objClass);
        }
        if (restoreObj == null){
            throw new BusinessException("该元素结构不一致");
        }
        BaseService baseService = SpringContextHelper.getBeanService(objClass);
        try {
        	baseService.insert(restoreObj);
        }catch (DuplicateKeyException e){
            throw new BusinessException("元素已存在");
        }
	}


	@Override
	public void deleteRecycle(String id) throws Exception {
		PubCrud rtRecycle = selectByPrimaryKey(id);
        if (rtRecycle == null){
            throw new BusinessException("不存在此元素");
        }
        
        if(!CrudTypeEnum.删除.getCode().equals(rtRecycle.getCrudType())) {
        	throw new BusinessException("元素不是回收站元素");
        }
        PubCrud del = new PubCrud();
        del.setDataId(rtRecycle.getDataId());
        del.setDataClass(rtRecycle.getDataClass());
		delete(del);
	}

	@Override
	public List<PubCrud> listRecycle(CrudSearchDto searchDto) {
		searchDto.setCrudType(CrudTypeEnum.删除.getCode());
		return pubCrudMapper.listRecycle(searchDto);
	}

	@Override
	public Pager<PubCrud> listRecyclePage(CrudSearchDto searchDto) {
		return this.getPageExcuter().selectByPage(new PageExcute<PubCrud>() {
            @Override
            public List<PubCrud> excute() {
            	searchDto.setCrudType(CrudTypeEnum.删除.getCode());
                return pubCrudMapper.listRecycle(searchDto);
            }
        },HttpServletHelper.getPager(PubCrud.class));
	}
	
	
	@Override
	public List<PubCrud> listCrud(CrudSearchDto searchDto) {
		return pubCrudMapper.listCrud(searchDto);
	}

	@Override
	public Pager<PubCrud> listCrudPage(CrudSearchDto searchDto) {
		return this.getPageExcuter().selectByPage(new PageExcute<PubCrud>() {
            @Override
            public List<PubCrud> excute() {
                return pubCrudMapper.listCrud(searchDto);
            }
        },null);
	}

}
