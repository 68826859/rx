/*
 * 异步panel
 * */
Ext.define('Rx.widget.SectionPanel',{
    extend: 'Ext.panel.Panel',
    alias:'widget.sectionpanel',
    initComponent:function(){
    	var me = this;
    	var requires = [];
    	var items = me.items;
    	if (items) {
            if (!Ext.isArray(items)) {
                items = [items];
            }
            var newItems = [],sectionItems = [],itm,T;
            for(var i=0,len=items.length;i<len;i++)
            {
            	itm = items[i];
            	if(itm.ctype)
            	{
            		T = Ext.ClassManager.get(itm.ctype);
            		if(T)
            		{
            			//console.log('有类型'+T.prototype.xtype);
            			itm.xtype = T.prototype.xtype;//itm.ctype;
            			newItems.push(itm);
            		}else{
            			requires.push(itm.ctype);
            			sectionItems.push(itm);
            		}
            	}else{
            		newItems.push(itm);
            	}
            	items[i] = null;
            }
            me.items = newItems;
            me.sectionItems = sectionItems;
        }
    	me.callParent(arguments);
    	if(me.sectionItems && me.sectionItems.length > 0)
    	{
    		me.setLoading('文件加载中...');
    		Ext.require(requires,function(){
    			me.initSectionItems.call(me);
    		});
    		
    	}
    },
    initSectionItems:function(){
    	var me = this;
    	me.setLoading(false);
    	var sItems = me.sectionItems,it;
    	for(var i=0,len=sItems.length;i<len;i++)
    	{
    		it = sItems[i];
    		var T = Ext.ClassManager.get(it.ctype);
	    	me.add(new T(it));
    	}
    }
});