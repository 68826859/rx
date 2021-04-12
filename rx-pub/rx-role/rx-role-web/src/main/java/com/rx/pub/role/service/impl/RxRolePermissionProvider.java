package com.rx.pub.role.service.impl;

import com.rx.pub.role.dto.PubRoleResourceDto;
import com.rx.pub.role.enm.RoleResourceReverseEumn;
import com.rx.pub.role.mapper.PubRoleMapper;
import com.rx.pub.role.po.PubRoleOwnerPo;
import com.rx.pub.role.po.PubRoleResourcePo;
import com.rx.pub.role.po.PubUserGroupPo;
import com.rx.pub.role.service.PubRoleOwnerService;
import com.rx.pub.role.service.PubRoleResourceService;
import com.rx.pub.role.service.PubUserGroupService;
import com.rx.pub.role.service.RxRolePermissionService;
import com.rx.pub.role.utils.RxRolePermissionMgr;
import com.rx.base.result.type.BusinessException;
import com.rx.base.user.RxGroupable;
import com.rx.base.user.RxPermissionProvider;
import com.rx.base.user.RxPermissionable;
import com.rx.base.user.RxRoleable;
import com.rx.base.user.RxUserable;
import com.rx.web.user.PermissionMgr;
import com.rx.web.user.RxUser;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;


/**
 *
 * @author Ray
 */
@Service
public class RxRolePermissionProvider implements RxPermissionProvider,RxRolePermissionService {

    private static final Logger logger = LoggerFactory.getLogger(RxRolePermissionProvider.class);

    @Resource
    private PubRoleMapper pubRoleMapper;
    
    @Resource
    private PubRoleResourceService pubRoleResourceService;

    @Resource
    private PubRoleOwnerService pubRoleOwnerService;
    
    @Resource
    private PubUserGroupService pubUserGroupService;
    
    
	@Override
	public List<? extends RxPermissionable> getUserPermissions(RxUserable user) {
		
		RxUser<?> user2 = (RxUser<?>)user;
		
		if(user2.isAdmin()) {
			return PermissionMgr.getAllPermissionItems(user2.getClass());
		}else {
			//获取用户角色
			PubRoleOwnerPo search = new PubRoleOwnerPo();
			search.setOwnerId(user2.getId());
			search.setOwnerType(user2.getClass().getName());
			List<PubRoleOwnerPo> rops = pubRoleOwnerService.select(search);
			
			HashSet<String> roleIds = new HashSet<String>();
			
			for(PubRoleOwnerPo rop:rops) {
				roleIds.add(rop.getRoleId());
			}
			Class<? extends RxGroupable> groupClass = RxRolePermissionMgr.getUserRoleGroupType(user2.getClass());
			if(groupClass != null) {
				//找出用户所属组织
				List<PubUserGroupPo> ugs = pubUserGroupService.listUserGroups(user2, groupClass,null);
				if(!ugs.isEmpty()) {
					List<String> ownerIds = new ArrayList<String>();
					for(PubUserGroupPo ug:ugs) {
						ownerIds.add(ug.getGroupId());
					}
					List<PubRoleOwnerPo> rops2 = pubRoleOwnerService.getListByOwners(ownerIds,groupClass.getName());
					for(PubRoleOwnerPo rop:rops2) {
						roleIds.add(rop.getRoleId());
					}
				}
			}
			List<PubRoleResourcePo> addList = pubRoleResourceService.getListRoleResourceByRoles(roleIds,RoleResourceReverseEumn.添加);
			
			HashSet<String> resources = new HashSet<String>();
			
			for(PubRoleResourcePo rrp: addList) {
				resources.add(rrp.getResourceId());
			}
			List<PubRoleResourcePo> delList = pubRoleResourceService.getListRoleResourceByRoles(roleIds,RoleResourceReverseEumn.去除);
			for(PubRoleResourcePo rrp: delList) {
				resources.remove(rrp.getResourceId());
			}
			List<RxPermissionable> allPermissionItem = new ArrayList<RxPermissionable>();
			
			List<? extends RxPermissionable> list = PermissionMgr.getAllPermissionItems(user2.getClass());
			for(String resId : resources) {
				for(RxPermissionable item : list) {
					if(resId.equals(item.getId())) {
						allPermissionItem.add(item);
					}
				}
			}
			return allPermissionItem;
		}
	}
	
	
	@Override
	public void addUserRole(RxUserable user, RxRoleable role) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public List<PubRoleResourceDto> getListRoleResourceDtoByRole(String roleId,RoleResourceReverseEumn roleResourceReverseEumn) {
		RxRoleable role = pubRoleMapper.selectByPrimaryKey(roleId);
		if (role == null) {
			throw new BusinessException("该角色不存在");
		}
		List<PubRoleResourcePo> list = pubRoleResourceService.getListRoleResourceByRole(role, roleResourceReverseEumn);
		List<PubRoleResourceDto> res = new ArrayList<PubRoleResourceDto>();
		List<? extends RxPermissionable> items = PermissionMgr.getAllPermissionItems(null);
		
		for(PubRoleResourcePo po : list) {
			PubRoleResourceDto dto = new PubRoleResourceDto();
			dto.setRoleResourceRef(po.getRoleResourceRef());
			dto.setRoleId(po.getRoleId());
			dto.setReverse(po.getReverse());
			dto.setResourceId(po.getResourceId());
			for(RxPermissionable pn : items) {
				if(Objects.equals(pn.getId(), po.getResourceId())) {
					dto.setName(pn.getName());
					dto.setGroup(pn.getGroup());
					dto.setDesc(pn.getDesc());
				}
			}
	        res.add(dto);
		}
		return res;
	}
	
}