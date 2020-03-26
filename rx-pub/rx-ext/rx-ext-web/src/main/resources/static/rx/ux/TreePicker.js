Ext.define('Rx.ux.TreePicker', {
	override:'Ext.ux.TreePicker',
	onItemClick: function(view, record, node, rowIndex, e) {
        this.selectItem(record);
    },
    onPickerKeyDown: function(treeView, record, item, index, e) {
        var key = e.getKey();
        if (key === e.ENTER || (key === e.TAB && this.selectOnTab)) {
            this.selectItem(record);
        }
    },
    setValue: function(value) {
        var me = this,record;
        me.value = value;
        if (me.store.loading) {
            return me;
        }
        if (value != undefined && this.valueLoaded != value){
        	record = me.store.getNodeById(value);
        	if(!record){
        		var proxy = me.store.getProxy();
        		
        		if(proxy.type == 'ajax'){
        			this.valueLoaded = value;
        			Ext.Ajax.request({
        				url:proxy.url,
            			params:{
            				value:value
            			},
            			callback:function(o,f,r){
            				if(f){
            					this.valueLoaded = o.params.value;
            					var records = this.store.getProxy().getReader().read(Ext.decode(r.responseText));
            					if(records && records.getTotal() > 0){
    	        					var rec = records.getRecords()[0];
    	        					this.setRawValue(rec.get(this.displayField));
            					}
            				}else{
            					this.valueLoaded = undefined;
            				}
            			},
            			scope:me
            		});
        		}
        		
        		
        	}
        }
        return me.callParent(arguments);
    },
    createPicker: function() {
        var me = this,
            picker = new Ext.tree.Panel({
                baseCls: Ext.baseCSSPrefix + 'boundlist',
                shrinkWrapDock: 2,
                store: me.store,
                floating: true,
                displayField: me.displayField,
                columns: me.columns,
                minHeight: me.minPickerHeight,
                maxHeight: me.maxPickerHeight,
                manageHeight: true,
                shadow: false,
                listeners: {
                    scope: me,
                    itemclick: me.onItemClick,
                    itemkeydown: me.onPickerKeyDown
                }
            }),
            view = picker.getView();
 
        if (Ext.isIE9 && Ext.isStrict) {
            // In IE9 strict mode, the tree view grows by the height of the horizontal scroll bar when the items are highlighted or unhighlighted.
            // Also when items are collapsed or expanded the height of the view is off. Forcing a repaint fixes the problem.
            view.on({
                scope: me,
                highlightitem: me.repaintPickerView,
                unhighlightitem: me.repaintPickerView,
                afteritemexpand: me.repaintPickerView,
                afteritemcollapse: me.repaintPickerView
            });
        }
        return picker;
    }
});