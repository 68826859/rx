/*
 * 分页表格
 * */
Ext.define('Rx.widget.PagingGrid',{
    extend: 'Ext.grid.Panel',
    alias:'widget.paginggrid',
    requires:['Ext.toolbar.Paging','Ext.grid.ColumnManager','Ext.grid.RowContext'],
    emptyText:'没有数据',
    columnLines:true,
    loadMask:true,
    pagingItems:['-'],
    initComponent:function(){
    	var me = this;
    	//debugger
    	//var isc = me.store.model.$isClass;
    	//var p = me.store.model.prototype;
    	//var fs = p.fields;
    	if(me.store && me.store.proxy){
    		
    		if(!me.store.proxy.reader){
    			me.store.proxy.reader = {
					type: 'json',
					rootProperty: 'data.list',
					totalProperty: 'data.total'		           
				}
    		}else if(me.store.proxy.reader.isReader){
    			me.store.proxy.reader.setRootProperty('data.list');
    			me.store.proxy.reader.setTotalProperty('data.total');
    		}else if(me.store.proxy.reader){
    			me.store.proxy.reader.rootProperty = 'data.list';
    			me.store.proxy.reader.totalProperty= 'data.total';
    		}
    	}
    	var store = me.store = Ext.data.StoreManager.lookup(me.store || 'ext-empty-store');
    	me.bbar = Ext.create('Ext.toolbar.Paging',{
    		itemId:'pagingtoolbar',
	        store: store,
	        displayInfo: true,
	        displayMsg: '显示 {0} - {1} 共 {2}',
	        emptyMsg: "没有数据",
	        items:me.pagingItems,
	        listeners:{
	        	'change':function(toobar,pageData){
	        		var items = toobar.items.items;
	        		for(var inx in items){
	        			if(typeof items[inx].pagingChange == 'function'){
	        				items[inx].pagingChange(toobar,pageData);
	        			}
	        		}
	        	}
	        }
       	});
		me.callParent(arguments);
    },
    /*
    onStoreLoad:function(sto,records,f,o){
    	if(records.length == 0){
			var tel = this.getView().getTargetEl();
			if(tel){
				//Ext.core.DomHelper.insertHtml('beforeEnd', tel.dom, '<center><div style="color:red;background-color:#C1DDF1;">'+this.emptyText+'<div></center>');
			}
		}
    },*/
    loadPage:function(page,params,options){
    	var sto = this.getStore();
	    sto.getProxy().extraParams = params;
	    sto.loadPage(page,options);
    }
},function(){
	
});