Ext.define('Rx.widget.FkColumn', {
	requires:[
		'Rx.widget.RemoteColumn',
		'Rx.model.FkModel',
		'com.rx.pub.ext.controller.ExtController'
	],
    uses:[],
    extend: 'Rx.widget.RemoteColumn',
    alias:'widget.fkcolumn',
    className:null,
    displayField:'display',
	initComponent:function(){
		var me = this;
		
		if(!me.store){
    		me.store = {
				autoLoad:true,
		    	model:'Rx.model.FkModel',
		    	proxy:{
		    		type:'ajax',
		    		extraParams:{
		    			className:me.className
		    		},
		    		url:ExtController.listFK.url,
		    		reader: {
			            type: 'json',
			            rootProperty: 'data'
			        }
		    	}
		    };
    	}
		
        me.callParent(arguments);
        if(me.store && me.className){
        	me.store.getProxy().setExtraParam("className",me.className);
        }
	}
});
