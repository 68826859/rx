package com.rx.pub.role.po;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import com.rx.ext.annotation.ExtClass;
import com.rx.base.model.annotation.RxModel;
import com.rx.base.model.annotation.RxModelField;
import com.rx.ext.data.Model;
import com.rx.base.utils.StringUtil;

/**
 * 授权(PubRoleOwner)实体类
 *
 * @author klf
 * @date 2019-12-30 15:06:14
 */
@RxModel(text = "授权")
@Table(name = "pub_role_owner")
@ExtClass(extend = Model.class, alternateClassName = "PubRoleOwnerPo")
public class PubRoleOwnerPo implements Serializable {
    private static final long serialVersionUID = -77755325772176475L;
    
    @Id
    @RxModelField(text = "授权ID", isID = true)
    @Column(name = "role_owner_ref")
    private String roleOwnerRef;
    
    @Column(name = "owner_id")
    @RxModelField(text = "角色拥有者ID")
    private String ownerId;
    
    @Column(name = "owner_type")
    @RxModelField(text = "角色拥有者类型")
    private String ownerType;
    
    @Column(name = "role_id")
    @RxModelField(text = "角色ID")
    private String roleId;
    
    public PubRoleOwnerPo() { }

    public PubRoleOwnerPo(String id) {
        if (id == null) {
            id = StringUtil.getUUIDPure();
        }
        this.roleOwnerRef = id;
    }


    public String getRoleOwnerRef() {
        return roleOwnerRef;
    }

    public void setRoleOwnerRef(String roleOwnerRef) {
        this.roleOwnerRef = roleOwnerRef;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(String ownerType) {
        this.ownerType = ownerType;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

}