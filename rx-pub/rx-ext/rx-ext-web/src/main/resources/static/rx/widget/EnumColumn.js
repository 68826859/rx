Ext.define('Rx.widget.EnumColumn', {
	requires:[],
    uses:[],
    extend: 'Ext.grid.column.Column',
    alias:'widget.enumcolumn',
    
    em:null,
    
	initComponent:function(){
		var me = this;
		var sto = Ext.create(this.em);
		me.renderer = function(v){
			if(typeof v == 'string' && v.indexOf(',') != -1){
				var varr = v.split(',');
				var res = [];
				for(var i in varr){
					var rec = sto.getByValue(varr[i]);
					if(rec){
						res.push(rec.get('name'));
					}
				}
				return res.join(' , ');
			}else{
				var rec = sto.getByValue(v);
				if(rec){
					return rec.get('name');
				}
			}
			return v;
		};
		me.callParent(arguments);
	}
});
