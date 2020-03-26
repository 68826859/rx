package com.rx.pub.role.vo;
  
import javax.validation.constraints.NotBlank;
import com.rx.base.enm.StatusEnum;
import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtFormAction;
import com.rx.ext.annotation.ExtFormField;
import com.rx.extrx.spring.SpringAction;
import com.rx.extrx.widget.ActionForm;
import com.rx.pub.role.controller.PubRoleController;
/**
 * 新增(PubRole)
 *
 * @author klf
 * @since 2019-12-30 14:47:25
 */    
@ExtClass(extend=ActionForm.class,alternateClassName="PubRoleAddVo")
public class PubRoleAddVo{
    
    @ExtFormAction
    SpringAction submitAction = new SpringAction(PubRoleController.class,"addPubRole");
    
  
    @ExtFormField(label = "角色名称",allowBlank=false)  
    @NotBlank(message = "角色名称不能为空")
    private String roleName;
    
  
    @ExtFormField(label = "状态",allowBlank=false,em = StatusEnum.class)
    private Integer status;
    

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