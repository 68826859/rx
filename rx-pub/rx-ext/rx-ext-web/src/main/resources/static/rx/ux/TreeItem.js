Ext.define('Rx.ux.TreeItem', {
	override:'Ext.list.TreeItem',
	constructor: function (config) {
		if(config && config.node.data.rightId){
			var hasR = Rx.User.hasRight(config.node.data.rightId);
			if(!hasR){
				config.node.data.iconCls = config.iconCls = config.iconCls + ' r-hidden';
				//config.rowCls = config.rowCls+'';
				config.hidden = true;
				config.style='display:none';
				this.noRight = true;
			}
		}
        this.callParent([config]);
    }
});