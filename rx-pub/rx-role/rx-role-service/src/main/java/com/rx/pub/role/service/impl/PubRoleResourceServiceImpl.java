package com.rx.pub.role.service.impl;

import com.rx.pub.role.po.PubRoleResourcePo;
import com.rx.pub.role.enm.RoleResourceReverseEumn;
import com.rx.pub.role.mapper.PubRoleMapper;
import com.rx.pub.role.mapper.PubRoleResourceMapper;
import com.rx.pub.role.service.PubRoleResourceService;
import com.rx.base.user.RxPermissionable;
import com.rx.base.user.RxRoleable;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.rx.base.page.Pager;
import com.rx.base.result.type.BusinessException;
import com.rx.base.page.PageExcute;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import com.rx.pub.mybatis.impls.MybatisBaseService;





/**
 * 角色资源(PubRoleResource)Service实现类
 *
 */
@Service
public class PubRoleResourceServiceImpl extends MybatisBaseService<PubRoleResourcePo> implements PubRoleResourceService {

    private static final Logger log = LoggerFactory.getLogger(PubRoleResourceServiceImpl.class);

    @Resource
    private PubRoleResourceMapper pubRoleResourceMapper;
    
    @Resource
    private PubRoleMapper pubRoleMapper;
    
    @Override
    public Pager<PubRoleResourcePo> searchPage(PubRoleResourcePo po) {
        return this.getPageExcuter().selectByPage(new PageExcute<PubRoleResourcePo>() {
            @Override
            public List<PubRoleResourcePo> excute() {
                return pubRoleResourceMapper.searchList(po);
            }
        },this.getPagerProvider().getPager(PubRoleResourcePo.class));
    }

	@Override
	public List<PubRoleResourcePo> getListRoleResourceByRole(RxRoleable role, RoleResourceReverseEumn roleResourceReverseEumn) {
		
		PubRoleResourcePo search = new PubRoleResourcePo();
		search.setRoleId(role.getId());
		if(roleResourceReverseEumn != null) {
			search.setReverse(roleResourceReverseEumn.getCode());
		}
		return pubRoleResourceMapper.select(search);
	}
	
	
	@Override
	public List<PubRoleResourcePo> getListRoleResourceByRoles(HashSet<String> roleIds, RoleResourceReverseEumn roleResourceReverseEumn) {
		if(roleIds.isEmpty()) {
			return new ArrayList<PubRoleResourcePo>();
		}
		Example ex = new Example(PubRoleResourcePo.class);
		Criteria ci = ex.createCriteria();
		
		ci.andIn("roleId", roleIds);
		if(roleResourceReverseEumn != null) {
			ci.andEqualTo("reverse", roleResourceReverseEumn.value());
		}
		return pubRoleResourceMapper.selectByExample(ex);
	}
	
	
	/*
	@Override
	public List<PubRoleResourceDto> getListRoleResourceDtoByRole(String roleId,RoleResourceReverseEumn roleResourceReverseEumn) {
		RxRoleable role = pubRoleMapper.selectByPrimaryKey(roleId);
		if (role == null) {
			throw new BusinessException("新增失败,该角色不存在");
		}
		List<PubRoleResourcePo> list = this.getListRoleResourceByRole(role, roleResourceReverseEumn);
		List<PubRoleResourceDto> res = new ArrayList<PubRoleResourceDto>();
		for(PubRoleResourcePo po : list) {
			res.add(new PubRoleResourceDto(po));
		}
		return res;
	}
	
	*/
	
	@Override
	public int addRoleResources(String roleId, String[] resIds, int reverse) {
		
		RxRoleable role = pubRoleMapper.selectByPrimaryKey(roleId);
		if (role == null) {
			throw new BusinessException("新增失败,该角色不存在");
		}
		List<PubRoleResourcePo> list = this.getListRoleResourceByRole(role,null);
		List<PubRoleResourcePo> newRes = new ArrayList<PubRoleResourcePo>();
		int len = 0;
		boolean contained = false;
		if (list != null && list.size() > 0) {
			for (String res : resIds) {
				contained = false;
				loop1 : for(PubRoleResourcePo item : list) {
					if(item.getResourceId().equals(res)) {
						contained = true;
						break loop1;
					}
				}
				if (contained) {
					PubRoleResourcePo ref = new PubRoleResourcePo();
					ref.setResourceId(res);
					ref.setRoleId(roleId);
					ref.setReverse(Integer.valueOf(reverse));
					Example ex = new Example(PubRoleResourcePo.class);
					Criteria ci = ex.createCriteria();
					ci.andEqualTo("resourceId", res);
					ci.andEqualTo("roleId", roleId);
					this.updateByExample(ref, ex);
				}else {
					PubRoleResourcePo ref = new PubRoleResourcePo(null);
					ref.setResourceId(res);
					ref.setRoleId(roleId);
					ref.setReverse(Integer.valueOf(reverse));
					newRes.add(ref);
				}
			}
		}else {
			for (String res : resIds) {
				PubRoleResourcePo ref = new PubRoleResourcePo(null);
				ref.setResourceId(res);
				ref.setRoleId(roleId);
				ref.setReverse(Integer.valueOf(reverse));
				newRes.add(ref);
			}
		}
		if (newRes.size() > 0) {
			this.insertList(newRes);
		}
		
		return len;
	}

	/*
	
	@Override
	public List<RxPermissionable> getListPermissionItemByRole(RxRoleable role,RoleResourceReverseEumn roleResourceReverseEumn) {
		
		List<PubRoleResourcePo> list = this.getListRoleResourceByRole(role, roleResourceReverseEumn);
		List<RxPermissionable> res = new ArrayList<RxPermissionable>();
		for(PubRoleResourcePo po : list) {
			//res.add(PermissionMgr.getPermissionEntity(po.getResourceId()));
		}
		return res;
	}
	*/
    
}