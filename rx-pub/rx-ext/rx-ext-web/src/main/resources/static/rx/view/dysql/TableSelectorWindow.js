/*
 * 主表选择窗体
 * */
Ext.define('Rx.view.dysql.TableSelectorWindow',{
	requires:[
		'Ext.grid.Panel',
		'com.rx.pub.dysql.dto.DySqlTableGrid'
	],
    extend: 'Rx.widget.Window',
    alias:'widget.tableselectorwindow',
    //uses:[],
    //resizable:false,
    width:500,
    height:400,
    minWidth:360,
    minHeight:300,
    layout:'fit',
    autoScroll:true,
    title:'选取主表',
    singleCheck:false,
    initComponent:function(){
    	var me = this;
    	var singleCk = this.singleCheck?true:false;
    	Ext.apply(me,{
    	    items:[{
    	    	xclass:'DySqlTableGrid',
    	    	selType: 'checkboxmodel',
    	    	itemId:'list',
    	    	selModel:{
			   		mode:singleCk?'SINGLE':'MULTI',
			   		toggleOnClick:false,
			   		//checkOnly:true,
			   		allowDeselect:false
    	    	},
    	    	columnLines: true
    	    }],
    	    buttons:[{
    	    	text:'刷新',
    	    	//hidden:true,
    	    	handler:function(btn){
    	    		var win = btn.up('window');
    	    		var list = win.getComponent('list');
    	    		list.getStore().reload();
    	    	}
    	    },'->',{
    	    	text:'确定',
    	    	handler:function(btn){
    	    		var win = btn.up('window');
    	    		var grid = win.getComponent('list');
    	    		var records = grid.getSelectionModel().getSelection();
    	    		var fn = win.returnFn,scope = win.returnScope;
    	    		win.returnFn = null;
    	    		win.returnScope = null;
    	    		if(fn){
    	    			fn.call(scope||window,records);
    	    		}
    	    		win.close();
    	    	}
    	    },{
    	    	text:'取消',
    	    	handler:function(btn){
    	    		btn.up('window').close();
    	    	}
    	    }]
    	});
    	me.callParent(arguments);
    }
});