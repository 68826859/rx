package com.rx.ext.panel;

import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtConfig;
import com.rx.ext.selection.Model;

@ExtClass(alias="widget.table")
public class Table extends Panel{

	
	@ExtConfig
	public Boolean autoLoad;
	
	@ExtConfig
	public Model selModel;
}
