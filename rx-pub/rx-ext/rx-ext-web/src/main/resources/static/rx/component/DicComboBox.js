/*
 * 字典下拉框
 * */
Ext.define('Rx.component.DicComboBox', {
    extend: 'Ext.form.field.ComboBox',
    alias:'widget.diccombo',
    requires:[],
    displayField: 'itemDesc',
    //valueField:'id',
    valueField:'itemCode',
    url:'dictionary/items',
    fields:null,
    typeName:null,//必传参数
    params:null,//可选参数
    
    setParams:function(n,v){
    	this.store.getProxy().setExtraParam(n,v);
    	/*
    	var me = this;
    	var url = me.url;
    	if(!pa){
    		pa = {};
    	}
    	pa.typeName = me.typeName;
    	var url = me.url +"?"+ Ext.Object.toQueryString(pa);
    	*/
    	//me.store.getProxy().
    },
    initComponent:function(){
    	var me = this;
    	var df = me.displayField;
    	var vf = me.valueField;
    	var fs = me.fields;
    	if(!fs){
    		fs = [
		       {name: df, type: 'string'},
		       {name: vf, type: 'string'}
		    ];
    	}
    	var url = me.url;
    	if(me.typeName){
    		 url = url+"?typeName="+me.typeName;
    	}
		me.store = Ext.create('Ext.data.Store', {
		    fields:fs,
		    autoLoad:false,
	        proxy: {
		        type: 'ajax',
		        url: url,
		        reader: {
		            type: 'json',
		            rootProperty: 'data',
		            idProperty: vf
		        }
		    }
		});
		if(me.params){
			me.store.getProxy().extraParams = me.params;
		}
		me.store.load();
    	me.callParent(arguments);
    },
    getStoreListeners:function(store){
    	return {
    		load:function(sto, records,successful,e){
    			//debugger
    			//sto.loaded = true;
    			this.setValue(this.value);
    		},
    		scope:this
    	};
    },
    /*
    setValue: function(value, doSelect) {
    	var me = this;
    	
    	if (!me.store.loaded) {
            me.value = value;
            me.setHiddenValue(me.value);
            return me;
        }
    	me.callParent(arguments);
    },
    */
    destroy:function(){
    	var me = this;
    	me.callParent(arguments);
    }
});