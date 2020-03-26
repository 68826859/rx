package com.rx.pub.role.service;

import com.rx.pub.role.enm.RoleResourceReverseEumn;
import com.rx.pub.role.po.PubRoleResourcePo;
import com.rx.base.user.RxRoleable;

import java.util.HashSet;
import java.util.List;
import com.rx.base.page.Pager;
import com.rx.base.service.BaseService;

/**
 * 角色资源(PubRoleResource)Service
 *
 * @author klf
 * @since 2019-12-30 15:07:24
 */
public interface PubRoleResourceService extends BaseService<PubRoleResourcePo> {

    /**
     * 分页查询
     */
    Pager<PubRoleResourcePo> searchPage(PubRoleResourcePo po);
    
    
    List<PubRoleResourcePo> getListRoleResourceByRole(RxRoleable role,RoleResourceReverseEumn roleResourceReverseEumn);

    //List<RxPermissionable> getListPermissionItemByRole(RxRoleable role,RoleResourceReverseEumn roleResourceReverseEumn);


	int addRoleResources(String roleId, String[] resIds, int reverse);


	List<PubRoleResourcePo> getListRoleResourceByRoles(HashSet<String> roleIds,RoleResourceReverseEumn roleResourceReverseEumn);


	//List<PubRoleResourceDto> getListRoleResourceDtoByRole(String roleId, RoleResourceReverseEumn roleResourceReverseEumn);
    
    
}