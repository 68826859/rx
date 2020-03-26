package com.rx.pub.role.vo;
  
import com.rx.pub.role.controller.PubRoleOwnerController;    
import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtFormAction;
import com.rx.ext.annotation.ExtFormField;
import com.rx.ext.form.field.Hidden;
import com.rx.extrx.spring.SpringAction;
import com.rx.extrx.widget.ActionForm;
import com.rx.extrx.widget.FkCombox;
import org.springframework.format.annotation.DateTimeFormat;
import com.alibaba.fastjson.annotation.JSONField;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.rx.base.enm.RxDatePattern;
import com.rx.base.model.annotation.RxModelField;

/**
 * 修改授权(PubRoleOwner)
 *
 * @author klf
 * @since 2019-12-30 15:06:14
 */    
@ExtClass(extend=ActionForm.class,alternateClassName="PubRoleOwnerUpdateVo")
public class PubRoleOwnerUpdateVo{
    
    @ExtFormAction
    SpringAction submitAction = new SpringAction(PubRoleOwnerController.class,"updatePubRoleOwner");
    
  
	@NotBlank(message = "授权ID不能为空")
    @ExtFormField(label = "授权ID", allowBlank = false, comp = Hidden.class)
    private String roleOwnerRef;

   
    @ExtFormField(label = "角色拥有者ID",allowBlank=false)  
    @NotBlank(message = "角色拥有者ID不能为空")
    private String ownerId;
    
   
    @ExtFormField(label = "角色拥有者类型",allowBlank=false)  
    @NotBlank(message = "角色拥有者类型不能为空")
    private String ownerType;
    
   
    @ExtFormField(label = "角色ID",allowBlank=false)  
    @NotBlank(message = "角色ID不能为空")
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