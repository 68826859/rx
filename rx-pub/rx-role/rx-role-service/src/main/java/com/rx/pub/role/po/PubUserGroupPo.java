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
import com.rx.pub.role.enm.UserGroupRelationEnum;

/**
 * 
 */
@RxModel(text = "用户组")
@Table(name = "pub_user_group")
@ExtClass(extend = Model.class, alternateClassName = "PubUserGroupPo")
public class PubUserGroupPo implements Serializable {
    private static final long serialVersionUID = -85088829299871057L;
    
    @Id
    @RxModelField(text = "用户组关系", isID = true)
    @Column(name = "user_group_ref")
    private String userGroupRef;
    
    @Column(name = "user_id")
    @RxModelField(text = "用户ID")
    private String userId;
    
    @Column(name = "user_type")
    @RxModelField(text = "用户类型")
    private String userType;
    
    @Column(name = "group_id")
    @RxModelField(text = "组ID")
    private String groupId;
    
    @Column(name = "group_type")
    @RxModelField(text = "组类型")
    private String groupType;
    
    @Column(name = "relation")
    @RxModelField(text = "组关系",em = UserGroupRelationEnum.class)
    private Integer relation;
    
    public PubUserGroupPo() { }

    public PubUserGroupPo(String id) {
        if (id == null) {
            id = StringUtil.getUUIDPure();
        }
        this.userGroupRef = id;
    }


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