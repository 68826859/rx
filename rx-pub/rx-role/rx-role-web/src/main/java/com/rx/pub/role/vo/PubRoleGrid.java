package com.rx.pub.role.vo;

import com.rx.base.enm.StatusEnum;
import com.rx.pub.role.po.PubRolePo;
import com.rx.pub.role.controller.PubRoleController;
import com.rx.ext.annotation.ExtClass;
import com.rx.extrx.spring.SpringProviderStore;
import com.rx.extrx.widget.PagingGrid;
import com.rx.ext.annotation.ExtGridColumn;
import com.rx.base.model.annotation.RxModelField;


/**
 * (PubRoleGrid)
 *
 * @author klf
 * @since 2019-12-30 14:47:25
 */
@ExtClass(alias = "widget.pubrolegrid", alternateClassName = {"PubRoleGrid"})
public class PubRoleGrid extends PagingGrid {
    public PubRoleGrid() {
    	SpringProviderStore<PubRolePo> store = new SpringProviderStore<PubRolePo>(PubRolePo.class, PubRoleController.class, "listPubRolePage");
    	store.setAutoLoad(Boolean.TRUE);
    	this.setStore(store);
        this.setColumnClass(PubRoleColumn.class);
    }
}

class PubRoleColumn {
    
    @RxModelField(text = "角色ID", isID = true)
    @ExtGridColumn(hidden = true)
    private String roleId;
    
    @RxModelField(text = "角色名称",isDisplay = true)
    @ExtGridColumn
    private String roleName;
    
    @RxModelField(text = "状态",em = StatusEnum.class)
    @ExtGridColumn(text = "状态",em = StatusEnum.class)
    private Integer status;
    
}