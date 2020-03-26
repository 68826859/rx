/*
 * 字典下拉框
 * */
Ext.define('Rx.widget.DicComboBox', {
    extend:'Ext.form.field.ComboBox',
    alias:'widget.diccombox',
    requires:['com.rx.pub.dic.vo.DicOption','com.rx.pub.dic.controller.PubDictionaryController'],
    parentId:null,
    displayField:'name',
    valueField:'value',
    editable:false,
    initComponent:function(){
    	var me = this;
    	if(!me.store){
    		me.store = {
				autoLoad:true,
				//pageSize: 25,
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
    	
    	if(me.store && me.className){
        	me.store.getProxy().setExtraParam("parentId",me.parentId);
        }
    }/*,
    bindStore:function(){
    	var me = this;
    	me.callParent(arguments);
    	if(me.store && me.parentId){
        	me.store.getProxy().setExtraParam("parentId",me.parentId);
        }
    }*/
});