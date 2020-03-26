package com.rx.extrx.widget;

import com.rx.ext.annotation.ExtClass;
import com.rx.ext.data.Store;
import com.rx.ext.grid.column.Column;

@ExtClass(alias = "widget.paginggrid", alter = "Rx.widget.PagingGrid")
public class PagingGrid extends com.rx.ext.grid.Panel {


    public PagingGrid() {

    }

    public PagingGrid(Store store, Class<Column> clazz) {
        super(store, clazz);
    }

    @Override
    public void setStore(Store store) {
        super.setStore(store);
        store.getProxy().getReader().setRootProperty("data.list");
    }
}
