package com.rx.pub.role.po;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import com.rx.base.enm.StatusEnum;
import com.rx.ext.annotation.ExtClass;
import com.rx.base.model.annotation.RxModel;
import com.rx.base.model.annotation.RxModelField;
import com.rx.base.user.RxPermissionable;
import com.rx.base.user.RxRoleable;
import com.rx.base.user.RxUserable;
import com.rx.ext.data.Model;
import com.rx.base.utils.StringUtil;

/**
 * (PubRole)实体类
 *
 * @date 2019-12-30 14:47:22
 */
@RxModel(text = "角色")
@Table(name = "pub_role")
@ExtClass(extend = Model.class, alternateClassName = "PubRolePo")
public class PubRolePo implements RxRoleable {
    private static final long serialVersionUID = -94559437672730209L;
    
    @Id
    @RxModelField(text = "角色ID", isID = true)
    @Column(name = "role_id")
    private String roleId;
    
    @Column(name = "role_name")
    @RxModelField(text = "角色名称")
    private String roleName;
    
    @Column(name = "user_type")
    @RxModelField(text = "用户类型")
    private String userType;
    
    @Column(name = "group_id")
    @RxModelField(text = "所属组")
    private String groupId;
    
    @Column(name = "status")
    @RxModelField(text = "状态",em = StatusEnum.class)
    private Integer status;
    
    public PubRolePo() { }

    public PubRolePo(String id) {
        if (id == null) {
            id = StringUtil.getUUIDPure();
        }
        this.roleId = id;
    }
    public String getRoleId() {
        return roleId;
    }
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    
	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getUserType() {
		return userType;
	}
    

	@Override
	public List<RxPermissionable> getPermissions() {
		return null;
		//return SpringContextHelper.getBean(PubRoleResourceService.class).getListPermissionItemByRole(this, RoleResourceReverseEumn.添加);
	}

	@Override
	public List<RxPermissionable> getPermissionReverses() {
		return null;
		//return SpringContextHelper.getBean(PubRoleResourceService.class).getListPermissionItemByRole(this, RoleResourceReverseEumn.去除);
	}

	@Override
	public Class<? extends RxUserable> getUserClass() {
		if(this.userType != null) {
			try {
				return (Class<? extends RxUserable>)Class.forName(this.userType);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public String getId() {
		return this.roleId;
	}

	@Override
	public String getName() {
		return this.roleName;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

}