package com.rx.pub.role.service.impl;

import com.rx.pub.role.po.PubRoleOwnerPo;
import com.rx.pub.role.po.PubRoleResourcePo;
import com.rx.pub.role.dto.PubRoleOwnerDto;
import com.rx.pub.role.mapper.PubRoleOwnerMapper;
import com.rx.pub.role.service.PubRoleOwnerService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.rx.base.page.Pager;
import com.rx.base.page.PageExcute;
import java.util.List;
import com.rx.pub.mybatis.impls.MybatisBaseService;





/**
 * 授权(PubRoleOwner)Service实现类
 *
 * @author klf
 * @since 2019-12-30 15:06:14
 */
@Service
public class PubRoleOwnerServiceImpl extends MybatisBaseService<PubRoleOwnerPo> implements PubRoleOwnerService {

    private static final Logger loger = LoggerFactory.getLogger(PubRoleOwnerServiceImpl.class);

    @Resource
    private PubRoleOwnerMapper pubRoleOwnerMapper;
    
    @Override
    public Pager<PubRoleOwnerDto> searchPage(PubRoleOwnerPo po) {
        return this.getPageExcuter().selectByPage(new PageExcute<PubRoleOwnerDto>() {
            @Override
            public List<PubRoleOwnerDto> excute() {
                return pubRoleOwnerMapper.searchList(po);
            }
        },this.getPagerProvider().getPager(PubRoleOwnerDto.class));
    }
    
    @Override
    public List<PubRoleOwnerPo> getListByOwners(List<String> ownerIds,String ownerType){
    	Example ex = new Example(PubRoleOwnerPo.class);
		Criteria ci = ex.createCriteria();
		ci.andIn("ownerId", ownerIds);
		ci.andEqualTo("ownerType", ownerType);
		return pubRoleOwnerMapper.selectByExample(ex);
    }
    
}