package com.rx.pub.role.vo;

import com.rx.pub.role.enm.UserGroupRelationEnum;
import com.rx.pub.role.po.PubUserGroupPo;
import com.rx.pub.role.controller.PubUserGroupController;
import com.rx.ext.annotation.ExtClass;
import com.rx.extrx.spring.SpringProviderStore;
import com.rx.extrx.widget.PagingGrid;
import com.rx.ext.annotation.ExtGridColumn;
import com.rx.base.model.annotation.RxModelField;


/**
 * 用户组(PubUserGroupGrid)
 *
 * @author klf
 * @since 2019-12-30 15:14:21
 */
@ExtClass(alias = "widget.pubusergroupgrid", alternateClassName = {"PubUserGroupGrid"})
public class PubUserGroupGrid extends PagingGrid {
    public PubUserGroupGrid() {
        this.setStore(new SpringProviderStore<>(PubUserGroupPo.class, PubUserGroupController.class, "listPubUserGroupPage"));
        this.setColumnClass(PubUserGroupColumn.class);
    }
}

class PubUserGroupColumn {
    
    @RxModelField(text = "用户组关系", isID = true)
    @ExtGridColumn
    private String userGroupRef;
    
    @RxModelField(text = "用户ID")
    @ExtGridColumn
    private String userId;
    
    @RxModelField(text = "用户类型")
    @ExtGridColumn
    private String userType;
    
    @RxModelField(text = "组ID")
    @ExtGridColumn
    private String groupId;
    
    @RxModelField(text = "组类型")
    @ExtGridColumn
    private String groupType;
    
    @RxModelField(text = "组关系",em = UserGroupRelationEnum.class)
    @ExtGridColumn
    private Integer relation;
    
}