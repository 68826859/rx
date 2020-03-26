package com.rx.pub.role.vo;
  
import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtFormField;
import com.rx.extrx.widget.ParamForm;
import com.rx.pub.role.enm.RoleResourceReverseEumn;


@ExtClass(extend = ParamForm.class, alternateClassName = "PubRoleResourceSearchVo")
public class PubRoleResourceSearchVo{

  
    @ExtFormField(label = "角色资源关系")
    private String roleResourceRef;
    
  
    @ExtFormField(label = "角色ID")
    private String roleId;
    
  
    @ExtFormField(label = "资源ID")
    private String resourceId;

    @ExtFormField(label = "角色资源关系", em = RoleResourceReverseEumn.class)
    private Integer reverse;
    

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