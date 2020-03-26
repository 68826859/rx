package com.rx.pub.dic.dto;

import com.rx.base.model.annotation.RxModelField;
import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtGridColumn;
import com.rx.extrx.spring.SpringProviderStore;
import com.rx.pub.dic.controller.PubDictionaryController;
import com.rx.pub.dic.utils.DictionaryEntity;

@ExtClass(alias="widget.dicgrid",alternateClassName={"DicGrid"})
public class DicGrid extends com.rx.ext.grid.Panel {
	public DicGrid(){
	this.setStore(new SpringProviderStore<DictionaryEntity>(DictionaryEntity.class,PubDictionaryController.class,"listDicByParentId"));
	this.setColumnClass(DicColumn.class);
	}
}
class DicColumn {

	// @ExtGridColumn()
	@RxModelField(isID = true)
	private String id;

	@ExtGridColumn(flex=1)
	@RxModelField(text = "名称",isDisplay = true)
	private String name;

	@ExtGridColumn(flex=1)
	@RxModelField(text = "值")
	private String value;

	
}