Ext.define('Rx.widget.EnumStore', {
	requires:[],
    uses:[],
    extend: 'Ext.data.Store',
    alias:'store.enumstore',
    
    em:null,
    
	initComponent:function(){
		var me = this;
		me.callParent(arguments);
	},
	getByValue:function(v){
		return this.getById(v);
	}
});
