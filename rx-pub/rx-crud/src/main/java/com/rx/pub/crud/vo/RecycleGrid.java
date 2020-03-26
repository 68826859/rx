package com.rx.pub.crud.vo;

import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtComp;
import com.rx.ext.annotation.ExtComp.DockEnum;
import com.rx.ext.annotation.ExtConfig;
import com.rx.ext.annotation.ExtGridColumn;
import com.rx.ext.button.Button;
import com.rx.ext.grid.column.Action;
import com.rx.ext.toolbar.Toolbar;
//import com.rx.extrx.Icons;
import com.rx.extrx.button.ActionButton;
import com.rx.extrx.spring.SpringProviderStore;
import com.rx.extrx.widget.PagingGrid;
import com.rx.pub.crud.controller.RecycleController;
import com.rx.pub.crud.po.PubCrud;
import com.rx.pub.crud.vo.RecycleVo;

@ExtClass(alias = "widget.recyclegrid", alternateClassName = { "RecycleGrid" }, requires = "Rx.controller.recycle.MainController")
public class RecycleGrid extends PagingGrid {

	@ExtComp(dock = DockEnum.top)
	Class<?> dto = RecycleSearchVo.class;
	
	//@ExtComp(dock = DockEnum.top)
	Toolbar too = new Toolbar() {
		{
			addItem(new Button() {
				{
					text = "清空回收站";
					handler = "removeBeanList";
//            setConfirm("确定清空回收站吗？");
				}
			});
		}
	};
	// Layout layout = HBox.stretch;
	{
		this.controller = "recycle";
		this.frame = Boolean.TRUE;
		this.margin = "10 10 10 10";
	}

	@ExtConfig
	Boolean frame = Boolean.TRUE;
	@ExtGridColumn(text = "操作", width = 80)
	public Action actionColumn = new Action() {
		{
			addItem(new ActionButton() {
				{
					//icon = Icons.png_16_31.getPath();
					tooltip = "还原";
					setHandler("restoreBean");
					setConfirm("确定还原该记录吗？");
				}
			});
			addItem(ActionButton.delete);
		}
	};

	public RecycleGrid() {
		this.setStore(new SpringProviderStore<>(PubCrud.class, RecycleController.class, "listDeletedBean"));
		this.setColumnClass(RecycleVo.class);
	}

}