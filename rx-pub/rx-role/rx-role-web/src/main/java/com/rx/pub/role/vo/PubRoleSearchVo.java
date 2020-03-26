package com.rx.pub.role.vo;
  
import com.rx.base.enm.StatusEnum;
import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtFormField;
import com.rx.extrx.widget.ParamForm;


@ExtClass(extend = ParamForm.class, alternateClassName = "PubRoleSearchVo")
public class PubRoleSearchVo{

  
    @ExtFormField(label = "角色ID")
    private String roleId;
    
  
    @ExtFormField(label = "角色名称")
    private String roleName;
    
  
    @ExtFormField(label = "状态",em = StatusEnum.class)
    private Integer status;
    

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
    
}