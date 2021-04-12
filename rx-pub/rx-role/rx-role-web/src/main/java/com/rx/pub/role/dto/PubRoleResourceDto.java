package com.rx.pub.role.dto;

import com.rx.ext.annotation.ExtClass;
import com.rx.base.model.annotation.RxModel;
import com.rx.base.model.annotation.RxModelField;
import com.rx.base.user.RxPermissionable;
import com.rx.ext.data.Model;
import com.rx.pub.role.po.PubRoleResourcePo;
import com.rx.web.user.PermissionEntity;
import com.rx.web.user.PermissionMgr;

/**
 */
@RxModel(text = "角色资源DTO")
@ExtClass(extend = Model.class, alternateClassName = "PubRoleResourceDto")
public class PubRoleResourceDto extends PubRoleResourcePo {
    
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
    @RxModelField(text = "名称")
    private String name;
    
    @RxModelField(text = "分组")
    private String group;
    
    @RxModelField(text = "描述")
    private String desc;
	

	public PubRoleResourceDto() { }

	/*
    public PubRoleResourceDto(PubRoleResourcePo po) {
        this.setRoleResourceRef(po.getRoleResourceRef());
        this.setRoleId(po.getRoleId());
        this.setReverse(po.getReverse());
        this.setResourceId(po.getResourceId());
        
        RxPermissionable pe = PermissionMgr.getPermissionEntity(po.getResourceId());
        if(pe != null) {
	        this.setName(pe.getName());
	        this.setGroup(pe.getGroup());
	        this.setDesc(pe.getDesc());
        }
    }
    */

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}