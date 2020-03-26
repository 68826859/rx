package com.rx.pub.role.vo;
  
import com.rx.pub.role.controller.PubUserGroupController;    
import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtFormAction;
import com.rx.ext.annotation.ExtFormField;
import com.rx.extrx.spring.SpringAction;
import com.rx.extrx.widget.ActionForm;
import com.rx.pub.role.enm.UserGroupRelationEnum;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 新增用户组(PubUserGroup)
 *
 * @author klf
 * @since 2019-12-30 15:14:21
 */    
@ExtClass(extend=ActionForm.class,alternateClassName="PubUserGroupAddVo")
public class PubUserGroupAddVo{
    
    @ExtFormAction
    SpringAction submitAction = new SpringAction(PubUserGroupController.class,"addPubUserGroup");
    
  
    @ExtFormField(label = "用户ID",allowBlank=false)  
    @NotBlank(message = "用户ID不能为空")
    private String userId;
    
  
    @ExtFormField(label = "用户类型",allowBlank=false)  
    @NotBlank(message = "用户类型不能为空")
    private String userType;
    
  
    @ExtFormField(label = "组ID",allowBlank=false)  
    @NotBlank(message = "组ID不能为空")
    private String groupId;
    
  
    @ExtFormField(label = "组类型",allowBlank=false)  
    @NotBlank(message = "组类型不能为空")
    private String groupType;
    
  
    @ExtFormField(label = "组关系",  allowBlank=false,em = UserGroupRelationEnum.class)
    @NotNull(message = "组关系不能为空")
    private Integer relation;
    

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
    
    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
    
    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }
    
    public Integer getRelation() {
        return relation;
    }

    public void setRelation(Integer relation) {
        this.relation = relation;
    }
    
}