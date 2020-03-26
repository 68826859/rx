package com.rx.pub.dic.dto;

import java.util.List;
import com.rx.base.model.annotation.RxModelField;
import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtGridColumn;
import com.rx.ext.data.Store;
import com.rx.ext.data.proxy.Memory;
import com.rx.pub.dic.utils.DictionaryDirEntity;
import com.rx.pub.dic.utils.DictionaryMgr;

@ExtClass(alias="widget.dicdirgrid",alternateClassName={"DicDirGrid"})
public class DicDirGrid extends com.rx.ext.grid.Panel {
	public DicDirGrid(){
		Store<DictionaryDirEntity> store = new Store<DictionaryDirEntity>(DictionaryDirEntity.class,new Memory());
		List<DictionaryDirEntity> list = DictionaryMgr.getAllDictionaryDirs();
		store.setData(list);
		this.setStore(store);
		this.setColumnClass(DicDirColumn.class);
	}
}

class DicDirColumn {

	// @ExtGridColumn()
	@RxModelField(isID = true)
	private String code;

	@ExtGridColumn(flex=1)
	@RxModelField(text = "名称",isDisplay = true)
	private String name;

	// @ExtGridColumn(text="分组")
	@RxModelField()
	private String group;

	@ExtGridColumn(flex=1)
	@RxModelField(text = "描述")
	private String desc;
	
}