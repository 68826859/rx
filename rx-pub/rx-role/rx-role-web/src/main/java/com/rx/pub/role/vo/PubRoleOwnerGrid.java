package com.rx.pub.role.vo;

import com.rx.pub.role.po.PubRoleOwnerPo;
import com.rx.pub.role.controller.PubRoleOwnerController;
import com.rx.ext.annotation.ExtClass;
import com.rx.extrx.spring.SpringProviderStore;
import com.rx.extrx.widget.PagingGrid;
import com.rx.ext.annotation.ExtGridColumn;
import com.rx.base.model.annotation.RxModelField;


/**
 * 授权(PubRoleOwnerGrid)
 *
 * @author klf
 * @since 2019-12-30 15:06:14
 */
@ExtClass(alias = "widget.pubroleownergrid", alternateClassName = {"PubRoleOwnerGrid"})
public class PubRoleOwnerGrid extends PagingGrid {
    public PubRoleOwnerGrid() {
        this.setStore(new SpringProviderStore<>(PubRoleOwnerPo.class, PubRoleOwnerController.class, "listPubRoleOwnerPage"));
        this.setColumnClass(PubRoleOwnerColumn.class);
    }
}

class PubRoleOwnerColumn {
    
    @RxModelField(text = "授权ID", isID = true)
    //@ExtGridColumn
    private String roleOwnerRef;
    
    @RxModelField(text = "角色拥有者ID")
    //@ExtGridColumn
    private String ownerId;
    
    @RxModelField(text = "角色拥有者类型")
    //@ExtGridColumn
    private String ownerType;
    
    @RxModelField(text = "角色ID")
    //@ExtGridColumn
    private String roleId;
    
    @RxModelField(text = "角色ID")
    @ExtGridColumn(flex=1)
    private String roleName;
    
}