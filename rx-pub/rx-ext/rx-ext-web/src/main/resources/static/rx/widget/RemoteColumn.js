Ext.define('Rx.widget.RemoteColumn', {
	requires:[],
    uses:[],
    extend: 'Ext.grid.column.Column',
    alias:'widget.remotecolumn',
    
    store:null,
    displayField:null,
    
    renderer:function(v){
		var rec = this.store.getById(v);
		if(rec){
			return rec.get(this.displayField);
		}
		return v;
	},
	initComponent:function(){
		var me = this;
		me.scope = me;
		if(!me.store.isStore){
        	me.store = Ext.Factory.store(me.store, "tree");
        }
		me.callParent(arguments);
	}
});
