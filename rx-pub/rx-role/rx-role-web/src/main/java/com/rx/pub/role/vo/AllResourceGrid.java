package com.rx.pub.role.vo;

import com.rx.base.model.annotation.RxModelField;
import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtGridColumn;
import com.rx.ext.data.Store;
import com.rx.extrx.spring.SpringProviderStore;
import com.rx.pub.role.controller.PubResourceController;
import com.rx.pub.role.dto.PermissionEntityModel;

@ExtClass(alias = "widget.allresourcegrid", alternateClassName = { "AllResourceGrid" })
public class AllResourceGrid extends com.rx.ext.grid.Panel {
	public AllResourceGrid() {
		Store<PermissionEntityModel> store = new SpringProviderStore<PermissionEntityModel>(PermissionEntityModel.class,PubResourceController.class, "listAllResource");
		store.setModel(PermissionEntityModel.class);
		//store.setProxy(new Memory());
		store.setAutoLoad(Boolean.TRUE);
		store.setGroupField("group");
		
		
		//List<PermissionEntityModel> list = new ArrayList<PermissionEntityModel>();
		//for (RxPermissionable pe : PermissionMgr.getAllPermissionItems()) {
		//	PermissionEntityModel vo = new PermissionEntityModel(pe);
		//	list.add(vo);
		//}
		
		//store.setData(list);
		this.setStore(store);
		this.setColumnClass(PermissionEntityColumn.class);
	}
}

class PermissionEntityColumn {

	@RxModelField(isID = true)
	private String code;

	@ExtGridColumn(ordinal = 1, text = "权限名称")
	@RxModelField(isDisplay = true)
	private String name;

	@ExtGridColumn(ordinal = 0, text = "分组")
	@RxModelField()
	private String group;

	@ExtGridColumn(ordinal = 0, text = "描述", flex = 1)
	@RxModelField()
	private String desc;

}