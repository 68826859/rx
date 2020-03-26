Ext.define('Rx.widget.RedirectColumn', {
	requires:[],
    uses:[],
    extend: 'Ext.grid.column.Column',
    alias:'widget.redirectcolumn',
    
    redirectTarget:null,
    renderer:function(value, metaData, record, rowIndex, colIndex, store, view){
    	if(Ext.isArray(value)){
    		var va = '';
    		for(var o in value){
    			va = va + '<img style="margin:0 0 -4px 5px;" alt="跳转" onclick="Rx.redirectTo(\''+metaData.column.redirectTarget+'\/'+value[o]+'\')" src="'+Rx.Icons.png_16_10.path+'"/>&nbsp;' + value[o] + " ";
    		}
    		return va;
    	}else if(value){
			return '<img style="margin:0 0 -4px 5px;" alt="跳转" onclick="Rx.redirectTo(\''+metaData.column.redirectTarget+'\/'+value+'\')" src="'+Rx.Icons.png_16_10.path+'"/>&nbsp;' + value;
    	}
    	return value;
	}/*,
	initComponent:function(){
		var me = this;
		me.scope = me;
		if(!me.store.isStore){
        	me.store = Ext.Factory.store(me.store, "tree");
        }
		me.callParent(arguments);
	}*/
});
