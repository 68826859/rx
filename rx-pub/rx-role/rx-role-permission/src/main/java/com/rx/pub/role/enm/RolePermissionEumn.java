package com.rx.pub.role.enm;

import com.rx.base.model.annotation.RxModelField;
import com.rx.base.user.RxPermissionable;

public enum RolePermissionEumn implements RxPermissionable {

	新增角色("sys_role_add","权限管理","新增角色"),
	修改角色("sys_role_update","权限管理","修改角色"),
	删除角色("sys_role_del","权限管理","删除角色"),
	//查找角色("sys_role_find","角色管理","查找角色信息"),
	角色列表("sys_role_findList","权限管理","查询角色列表"),
	
	/*角色资源管理*/
	角色的资源列表("sys_role_listRoleResource","权限管理","查询角色权限列表"),
	新增角色的资源("sys_role_addRoleResource","权限管理","给角色添加权限"),
	删除角色的资源("sys_role_delRoleResource","权限管理","给角色删除权限"),
	
	
	/*授权管理*/
	授权列表("owner_listRole","权限管理","查看用户或组织的授权列表"),
	授权("owner_addRole","权限管理","给组织或个人授权"),
	解除授权("owner_delRole","权限管理","解除组织或个人的权限");
	
	@RxModelField(isID = true)
	private String code;

	@RxModelField()
	private String group;

	@RxModelField()
	private String desc;

	RolePermissionEumn(String code, String group, String desc) {
		this.code = code;
		this.group = group;
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	@Override
	public String getId() {
		return code;
	}

	public String getGroup() {
		return group;
	}

	@Override
	public String getName() {
		return this.name();
	}

}
