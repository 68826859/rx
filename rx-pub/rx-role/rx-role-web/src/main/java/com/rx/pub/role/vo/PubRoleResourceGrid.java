package com.rx.pub.role.vo;

import com.rx.pub.role.enm.RoleResourceReverseEumn;
import com.rx.pub.role.po.PubRoleResourcePo;
import com.rx.pub.role.controller.PubRoleResourceController;
import com.rx.ext.annotation.ExtClass;
import com.rx.extrx.spring.SpringProviderStore;
import com.rx.extrx.widget.PagingGrid;
import com.rx.ext.annotation.ExtGridColumn;
import com.rx.ext.data.Store;
import com.rx.base.model.annotation.RxModelField;


/**
 * 角色资源(PubRoleResourceGrid)
 *
 */
@ExtClass(alias = "widget.pubroleresourcegrid", alternateClassName = {"PubRoleResourceGrid"})
public class PubRoleResourceGrid  extends com.rx.ext.grid.Panel {
    public PubRoleResourceGrid() {
    	Store<PubRoleResourcePo> store = new SpringProviderStore<>(PubRoleResourcePo.class, PubRoleResourceController.class, "listPubRoleResource");
        store.setGroupField("group");
    	this.setStore(store);
        this.setColumnClass(PubRoleResourceColumn.class);
    }
}

class PubRoleResourceColumn {
    
    //@RxModelField(text = "角色资源关系", isID = true)
    //@ExtGridColumn
    //private String roleResourceRef;
    
    //@RxModelField(text = "角色ID")
    //@ExtGridColumn
    //private String roleId;
    
    //@RxModelField(text = "资源ID")
    //@ExtGridColumn
    //private String resourceId;
	
	@RxModelField(text = "角色名称")
    @ExtGridColumn
    private String name;
	
	@RxModelField(text = "分组")
    @ExtGridColumn
    private String group;
	
	@RxModelField(text = "描述")
    @ExtGridColumn
    private String desc;
    
    @RxModelField(text = "角色资源关系",em = RoleResourceReverseEumn.class)
    @ExtGridColumn(em = RoleResourceReverseEumn.class)
    private Integer reverse;
    
}