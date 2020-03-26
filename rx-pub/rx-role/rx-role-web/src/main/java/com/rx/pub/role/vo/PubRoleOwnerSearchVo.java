package com.rx.pub.role.vo;
  
import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtFormField;
import com.rx.extrx.widget.ParamForm;
import org.springframework.format.annotation.DateTimeFormat;
import com.alibaba.fastjson.annotation.JSONField;
import com.rx.base.enm.RxDatePattern;


@ExtClass(extend = ParamForm.class, alternateClassName = "PubRoleOwnerSearchVo")
public class PubRoleOwnerSearchVo{

  
    @ExtFormField(label = "授权ID")
    private String roleOwnerRef;
    
  
    @ExtFormField(label = "角色拥有者ID")
    private String ownerId;
    
  
    @ExtFormField(label = "角色拥有者类型")
    private String ownerType;
    
  
    @ExtFormField(label = "角色ID")
    private String roleId;
    

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