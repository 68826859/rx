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
import com.rx.pub.role.enm.RoleResourceReverseEumn;

/**
 * 角色资源(PubRoleResource)实体类
 *
 * @author klf
 * @date 2019-12-30 15:07:24
 */
@RxModel(text = "角色资源")
@Table(name = "pub_role_resource")
@ExtClass(extend = Model.class, alternateClassName = "PubRoleResourcePo")
public class PubRoleResourcePo implements Serializable {
    private static final long serialVersionUID = 564814582033713219L;
    
    @Id
    @RxModelField(text = "角色资源关系", isID = true)
    @Column(name = "role_resource_ref")
    private String roleResourceRef;
    
    @Column(name = "role_id")
    @RxModelField(text = "角色ID")
    private String roleId;
    
    @Column(name = "resource_id")
    @RxModelField(text = "资源ID")
    private String resourceId;
    
    @Column(name = "reverse")
    @RxModelField(text = "角色资源关系",em = RoleResourceReverseEumn.class)
    private Integer reverse;
    
    public PubRoleResourcePo() { }

    public PubRoleResourcePo(String id) {
        if (id == null) {
            id = StringUtil.getUUIDPure();
        }
        this.roleResourceRef = id;
    }


    public String getRoleResourceRef() {
        return roleResourceRef;
    }

    public void setRoleResourceRef(String roleResourceRef) {
        this.roleResourceRef = roleResourceRef;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public Integer getReverse() {
        return reverse;
    }

    public void setReverse(Integer reverse) {
        this.reverse = reverse;
    }

}