/*
 * 表单下拉框，下拉传递表单的值
 * */
Ext.define('Rx.widget.FormComBox', {
    extend: 'Ext.form.field.ComboBox',
    alias:'widget.formcombox',
    requires:[],
    editable:false,
    triggerAction:'all',
    typeAhead : true,
    autoLoadOnValue:true,
    
    setValue:function(v){
    	var me = this;
    	if(v && typeof v =='string'){
            var store = me.getStore(),
            autoLoadOnValue = me.autoLoadOnValue,
            isLoaded = store.getCount() > 0 || store.isLoaded(),
            pendingLoad = store.hasPendingLoad(),
            unloaded = autoLoadOnValue && !isLoaded && !pendingLoad;
    		if (pendingLoad || unloaded || !isLoaded){
				store.load();
    		}
    	}
    	return me.callParent(arguments);
    },
    
    getStoreListeners:function(){
    	var me = this;
        var res = me.callParent(arguments);
        res.beforeload = me.onStoreBeforeload;
        return res;
    },
    onStoreBeforeload:function(sto,option,e){
    	var fm = this.up('form');
    	if(fm){
    		sto.getProxy().setExtraParams(fm.getValues());
    	}
    	return true;
    }
    /*,
    getParams: function(queryString) {
        var params = this.up('form').getValues();
        var param = this.queryParam;
 
        if (param) {
            params[param] = queryString;
        }
        return params;
    }
    */
});