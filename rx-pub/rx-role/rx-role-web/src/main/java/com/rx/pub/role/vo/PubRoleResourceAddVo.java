package com.rx.pub.role.vo;
  
import com.rx.pub.role.controller.PubRoleResourceController;    
import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtFormAction;
import com.rx.ext.annotation.ExtFormField;
import com.rx.ext.form.field.Hidden;
import com.rx.extrx.spring.SpringAction;
import com.rx.extrx.widget.ActionForm;
import com.rx.extrx.widget.FkCombox;
import com.rx.pub.role.enm.RoleResourceReverseEumn;
import org.springframework.format.annotation.DateTimeFormat;
import com.alibaba.fastjson.annotation.JSONField;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.rx.base.enm.RxDatePattern;
import com.rx.base.model.annotation.RxModelField;
/**
 * 新增角色资源(PubRoleResource)
 *
 * @author klf
 * @since 2019-12-30 15:07:24
 */    
@ExtClass(extend=ActionForm.class,alternateClassName="PubRoleResourceAddVo")
public class PubRoleResourceAddVo{
    
    @ExtFormAction
    SpringAction submitAction = new SpringAction(PubRoleResourceController.class,"addPubRoleResource");
    
  
    @ExtFormField(label = "角色ID",allowBlank=false)  
    @NotBlank(message = "角色ID不能为空")
    private String roleId;
    
  
    @ExtFormField(label = "资源ID",allowBlank=false)  
    @NotBlank(message = "资源ID不能为空")
    private String resourceId;
    
  
    @ExtFormField(label = "角色资源关系",  allowBlank=false,em = RoleResourceReverseEumn.class)
    @NotNull(message = "角色资源关系不能为空")
    private Integer reverse;
    

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