/*
 * 枚举下拉框
 * */
Ext.define('Rx.component.EnumComboBox', {
    extend: 'Ext.form.field.ComboBox',
    alias:'widget.enumcombo',
    requires:[],
    displayField: 'displayField',
    //valueField:'id',
    valueField:'valueField',
    editable:false,
    url:'pub/enum',
    fields:null,
    className:null,//必传参数
    params:null,//可选参数
    
    setClassName:function(className){
    	this.setParams("className",className);
    },
    
    setParams:function(n,v){
    	this.store.getProxy().setExtraParam(n,v);
    	this.store.load();
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
    	if(me.em){
    		me.store = Ext.create(me.em);
    		this.valueField = me.store.getModel().prototype.idProperty;
    		this.displayField = me.store.getModel().prototype.displayProperty;
    	}else{
	    	var df = me.displayField;
	    	var vf = me.valueField;
	    	var fs = me.fields;
	    	if(!fs){
	    		fs = [
			       {name: df, type: 'string'},
			       {name: vf, type: 'string'}
			    ];
	    	}
			me.store = Ext.create('Ext.data.Store', {
			    fields:fs,
			    autoLoad:false,
		        proxy: {
			        type: 'ajax',
			        url: me.url,
			        extraParams:{
			        	className:me.className
			        },
			        reader: {
			            type: 'json',
			            rootProperty: 'data',
			            idProperty: vf
			        }
			    }
			});
			if(me.params){
				Ext.apply(me.store.getProxy().extraParams,me.params);
			}
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