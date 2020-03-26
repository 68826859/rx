Ext.define('Rx.ux.TreeColumn', {
	override:'Ext.tree.Column',
	treeRenderer: function(value, metaData, record, rowIdx, colIdx, store, view){
        var me = this,
            cls = record.get('cls'),
            renderer = me.origRenderer,
            data = record.data,
            parent = record.parentNode,
            rootVisible = view.rootVisible,
            lines = [],
            parentData;
        var rid = record.get('rightId');
        if(rid){
        	if(!Rx.User.hasRight(rid)){
        		if(cls){
        			cls = cls + " r-hidden";
        		}else{
        			cls = "r-hidden";
        		}
        	}
        }
        if (cls) {
            metaData.tdCls += ' ' + cls;
        }
        while (parent && (rootVisible || parent.data.depth > 0)) {
            parentData = parent.data;
            lines[rootVisible ? parentData.depth : parentData.depth - 1] =
                    parentData.isLast ? 0 : 1;
            parent = parent.parentNode;
        }
        return me.getTpl('cellTpl').apply({
            record: record,
            baseIconCls: me.iconCls,
            iconCls: data.iconCls,
            icon: data.icon,
            checkboxCls: me.checkboxCls,
            checked: data.checked,
            elbowCls: me.elbowCls,
            expanderCls: me.expanderCls,
            textCls: me.textCls,
            leaf: data.leaf,
            expandable: record.isExpandable(),
            isLast: data.isLast,
            blankUrl: Ext.BLANK_IMAGE_URL,
            href: data.href,
            hrefTarget: data.hrefTarget,
            lines: lines,
            metaData: metaData,
            // subclasses or overrides can implement a getChildCls() method, which can
            // return an extra class to add to all of the cell's child elements (icon,
            // expander, elbow, checkbox).  This is used by the rtl override to add the
            // "x-rtl" class to these elements.
            childCls: me.getChildCls ? me.getChildCls() + ' ' : '',
            value: renderer ? renderer.apply(me.origScope, arguments) : value
        });
    }/*,
	afterRender:function(){
		var me = this;
    	me.callParent(arguments);
    	if(me.initialConfig.noRight && !me.initialConfig.rightHidden){
    		try{
    			this.getEl().dom.title="没有权限";
    		}catch(e){
    			console.error(e);
    		}
    	}
	}*/
});