package com.rx.pub.msgq.dto;

import com.rx.ext.annotation.ExtClass;
import com.rx.ext.data.Store;
import com.rx.extrx.spring.SpringProviderStore;
import com.rx.extrx.widget.PagingGrid;
import com.rx.pub.msgq.controller.MsgqController;
import com.rx.pub.msgq.po.MsgqPo;

@ExtClass(alias = "widget.msgqgrid", alternateClassName = {"MsgqGrid"})
public class MsgqGrid extends PagingGrid {
    public MsgqGrid() {
        Store<?> sto = new SpringProviderStore(MsgqPo.class, MsgqController.class, "listMsgqPage");
        this.setStore(sto);
        this.setColumnClass(MsgqPo.class);
    }
}
