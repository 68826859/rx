package com.rx.pub.role.vo;

import com.rx.base.model.annotation.RxModelField;
import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtGridColumn;
import com.rx.extrx.spring.SpringProviderStore;
import com.rx.pub.role.controller.PubRoleController;
import com.rx.pub.role.po.PubRolePo;

@ExtClass(alias="widget.roleselectgrid",alternateClassName={"RoleSelectGrid"})
public class RoleSelectGrid extends com.rx.ext.grid.Panel {
	public RoleSelectGrid(){
		SpringProviderStore<PubRolePo> store = new SpringProviderStore<PubRolePo>(PubRolePo.class,PubRoleController.class,"listPubRole");
		store.getProxy().addExtraParam("status", 1);
		store.setAutoLoad(Boolean.TRUE);
		this.setStore(store);
		this.setColumnClass(RoleSelectColumn.class);
	}
}

class RoleSelectColumn {

	@RxModelField(isID = true)
	private String code;

	@ExtGridColumn(ordinal = 1, text = "角色名称" ,flex = 1)
	@RxModelField(isDisplay = true)
	private String roleName;

}