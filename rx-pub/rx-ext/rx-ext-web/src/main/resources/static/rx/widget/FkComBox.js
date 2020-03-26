/*
 * 外键下拉框
 * */
Ext.define('Rx.widget.FkComBox', {
    extend: 'Ext.form.field.ComboBox',
    alias:'widget.fkcombox',
    requires:['com.rx.pub.ext.controller.ExtController','Rx.model.FkModel'],
    className:null,
    displayField:'display',
    valueField:'key',
    pageSize:0,
    minChars:1,
    triggerAction:'all',
    typeAhead : true,
    autoLoadOnValue:true,
    forceSelection:true,//只能是匹配的值，不能由用户输入
    setValue:function(v){
    	var me = this;
    	if(v && typeof v =='string'){
            var store = me.getStore(),
            autoLoadOnValue = me.autoLoadOnValue,
            isLoaded = store.getCount() > 0 || store.isLoaded(),
            pendingLoad = store.hasPendingLoad(),
            unloaded = autoLoadOnValue && !isLoaded && !pendingLoad;
    		if (pendingLoad || unloaded || !isLoaded){
				store.load({
	    			params:{
	    				keys:[v]
	    			}
	    		});
    		}
    	}
    	return me.callParent(arguments);
    },
    initComponent:function(){
    	var me = this;
    	if(!me.store){
    		me.store = {
				//autoLoad:true,
				pageSize: me.pageSize,
		    	model:'Rx.model.FkModel',
		    	proxy:{
		    		type:'ajax',
		    		url:ExtController.listModel.url,
		    		reader: {
			            type: 'json',
			            totalProperty: me.pageSize == 0?null:'data.totalRows',
			            rootProperty: me.pageSize == 0?'data':'data.pageData'
			        }
		    	}
		    };
    	}
    	me.callParent(arguments);
    	if(me.store && me.className){
        	me.store.getProxy().setExtraParam("className",me.className);
        }
    }
    /*,
    bindStore:function(){
    	var me = this;
    	me.callParent(arguments);
    	if(me.store && me.parentId){
        	me.store.getProxy().setExtraParam("className",me.className);
        }
    }
    */
});