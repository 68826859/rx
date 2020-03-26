Ext.define('Rx.widget.DicColumn', {
	requires:[
		'Rx.widget.RemoteColumn',
		'com.rx.pub.dic.vo.DicOption',
		'com.rx.pub.dic.controller.PubDictionaryController'
	],
    uses:[],
    extend: 'Rx.widget.RemoteColumn',
    alias:'widget.diccolumn',
    parentId:null,
    displayField:'name',
	initComponent:function(){
		var me = this;
		
		if(!me.store){
    		me.store = {
				autoLoad:true,
		    	model:'com.rx.pub.dic.vo.DicOption',
		    	proxy:{
		    		type:'ajax',
		    		extraParams:{
		    			parentId:me.parentId
		    		},
		    		url:PubDictionaryController.listDicByParentId.url,
		    		reader: {
			            type: 'json',
			            rootProperty: 'data'
			        }
		    	}
		    };
    	}
		
        me.callParent(arguments);
        if(me.store && me.parentId){
        	me.store.getProxy().setExtraParam("parentId",me.parentId);
        }
	}
});
