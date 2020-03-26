package com.rx.pub.role.vo;
  
import javax.validation.constraints.NotBlank;
import com.rx.base.enm.StatusEnum;
import com.rx.pub.role.controller.PubRoleController;
import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtFormAction;
import com.rx.ext.annotation.ExtFormField;
import com.rx.ext.form.field.Hidden;
import com.rx.extrx.spring.SpringAction;
import com.rx.extrx.widget.ActionForm;

/**
 * 修改(PubRole)
 *
 * @author klf
 * @since 2019-12-30 14:47:25
 */    
@ExtClass(extend=ActionForm.class,alternateClassName="PubRoleUpdateVo")
public class PubRoleUpdateVo{
    
    @ExtFormAction
    SpringAction submitAction = new SpringAction(PubRoleController.class,"updatePubRole");
    
  
	@NotBlank(message = "角色ID不能为空")
    @ExtFormField(label = "角色ID", allowBlank = false, comp = Hidden.class)
    private String roleId;
	
    @ExtFormField(label = "角色名称",allowBlank=false)  
    @NotBlank(message = "角色名称不能为空")
    private String roleName;
    
   
    @ExtFormField(label = "状态",allowBlank=false,em = StatusEnum.class)
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