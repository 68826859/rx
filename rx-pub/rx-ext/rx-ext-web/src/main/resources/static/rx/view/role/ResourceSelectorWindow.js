/*
 * 资源选择窗体
 * */
Ext.define('Rx.view.role.ResourceSelectorWindow',{
	requires:[
		'Ext.grid.feature.Grouping',
		'Rx.ux.GridGrouping',
		'Ext.selection.CheckboxModel',
		'com.rx.pub.role.vo.AllResourceGrid'
	],
    extend: 'Rx.widget.Window',
    //uses:[],
    //resizable:false,
    width:600,
    height:0.8,
    minWidth:360,
    minHeight:300,
    layout:'fit',
    autoScroll:true,
    title:'选取资源',
    singleCheck:false,
    initComponent:function(){
    	var me = this;
    	var singleCk = this.singleCheck?true:false;
    	Ext.apply(me,{
    	    items:[{
    	    	xtype:'gridpanel',
    	    	itemId:'list',
		    	//forceFit:true,
		    	xclass:'AllResourceGrid',
    	    	selType: 'checkboxmodel',
    	    	selModel:{
			   		mode:singleCk?'SINGLE':'MULTI'
			   		//toggleOnClick:false,
			   		//checkOnly:true,
			   		//allowDeselect:false
			   },
			   features: [{
			        ftype: 'grouping',
			        groupHeaderTpl: '{groupValue} ({rows.length})',
			        hideGroupedHeader: true,
			        //collapsible:true,
			        startCollapsed:true
			        //,id: 'featuresGroupName'
			    }],
			   columnLines: true
    	    }],
    	    buttons:[{
    	    	xtype:'label',
    	    	text:'不包含'
    	    },{
    	    	itemId:'checkbox',
    	    	xtype:'checkboxfield'
    	    },{
    	    	xtype:'label',
    	    	text:'(勾选表示去除所选权限)'
    	    },{
    	    	text:'刷新',
    	    	hidden:true,
    	    	handler:function(btn){
    	    		var win = btn.up('window');
    	    		var list = win.getComponent('list');
    	    		list.getStore().reload();
    	    	}
    	    },'->',{
    	    	text:'确定',
    	    	handler:function(btn){
    	    		var win = btn.up('window');
    	    		var list = win.getComponent('list');
    	    		var records = list.getSelectionModel().getSelection();
    	    		var fn = win.returnFn,scope = win.returnScope;
    	    		win.returnFn = null;
    	    		win.returnScope = null;
    	    		if(fn){
    	    			var b = btn.ownerCt.getComponent('checkbox').checked;
    	    			fn.call(scope||window,records,b);
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