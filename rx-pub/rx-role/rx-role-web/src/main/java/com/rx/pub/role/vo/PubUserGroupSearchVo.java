package com.rx.pub.role.vo;
  
import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtFormField;
import com.rx.extrx.widget.ParamForm;
import com.rx.pub.role.enm.UserGroupRelationEnum;


@ExtClass(extend = ParamForm.class, alternateClassName = "PubUserGroupSearchVo")
public class PubUserGroupSearchVo{

  
    @ExtFormField(label = "用户组关系")
    private String userGroupRef;
    
  
    @ExtFormField(label = "用户ID")
    private String userId;
    
  
    @ExtFormField(label = "用户类型")
    private String userType;
    
  
    @ExtFormField(label = "组ID")
    private String groupId;
    
  
    @ExtFormField(label = "组类型")
    private String groupType;
    
  
    @ExtFormField(label = "组关系", em = UserGroupRelationEnum.class)
    private Integer relation;
    

    public String getUserGroupRef() {
        return userGroupRef;
    }

    public void setUserGroupRef(String userGroupRef) {
        this.userGroupRef = userGroupRef;
    }
    
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