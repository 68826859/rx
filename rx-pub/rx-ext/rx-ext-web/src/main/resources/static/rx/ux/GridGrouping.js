Ext.define('Rx.ux.GridGrouping', {
	override:'Ext.grid.feature.Grouping',
    requires:['Ext.grid.feature.Grouping'],
    expandTip:"点击展开，按住CTRL键收起其它",
    collapseTip:"点击收起，按住CTRL键收起其它",
    init:function(grid){
    	var me = this;
    	me.callParent(arguments);
    	//me.view.getRecord = me.getRecord;
    }
    /*
	getRecord:function(node) {
        node = this.getNode(node);
        if (node) {
            return this.dataSource.data.get(node.getAttribute('data-recordId'));
        }
    }
    */
});
