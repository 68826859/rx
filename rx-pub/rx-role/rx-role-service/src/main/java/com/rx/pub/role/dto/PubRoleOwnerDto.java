package com.rx.pub.role.dto;


import com.rx.ext.annotation.ExtClass;
import com.rx.base.model.annotation.RxModel;
import com.rx.base.model.annotation.RxModelField;
import com.rx.ext.data.Model;
import com.rx.pub.role.po.PubRoleOwnerPo;

/**
 * 授权(PubRoleOwner)实体类
 *
 */
@RxModel(text = "授权DTO")
@ExtClass(extend = Model.class, alternateClassName = "PubRoleOwnerDto")
public class PubRoleOwnerDto extends PubRoleOwnerPo {
    private static final long serialVersionUID = -7775532577217647L;
    
    @RxModelField(text = "角色名称")
    private String roleName;

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}