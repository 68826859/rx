/*
 * 分段加载容器SectionTab类
 * */
Ext.define('Rx.widget.SectionTabpanel', {
	extend:'Ext.tab.Panel',
	alias: 'widget.sectiontabpanel',
    
    //requires:[],
    initComponent:function(){
    	var me = this;
    	var hasSection = true;
    	var items = me.items;
    	if (items) {
            if (!Ext.isArray(items)) {
                items = [items];
            }
            var newItems = [],barItems = [],itm;
            for(var i=0,len=items.length;i<len;i++)
            {
            	itm = items[i];
            	if(itm.ctype)
            	{
            		itm = Ext.apply({
            			xtype:'tab',
            			text:itm.title
            		},itm);
            		itm.cardConfig = items[i];
            		barItems.push(itm);
            	}else{
            		newItems.push(items[i]);
            	}
            	items[i] = null;
            }
            me.items = newItems;
            
            if(barItems.length > 0)
            {
            	hasSection = true;
            	me.tabBar = me.tabBar || {};
            	me.tabBar.items = me.tabBar.items?barItems.concat(me.tabBar.items):barItems;
            }
            
        }
    	var activeTab = me.activeTab || (me.activeTab = 0);
    	me.activeTab = 0;
    	me.callParent(arguments);
    	if(hasSection)
    	{
    		//me.tabBar.tabPanel = null;
	    	/*for(var i=0,len=chs.length;i<len;i++)
	    	{
	    		chs[i] = Ext.applyIf(chs[i], {
	    				itemId:chs[i].moduleId,
	                    xtype: 'tab',
	                    ui: me.tabBar.ui,
	                    card: null,
	                    //disabled: item.disabled,//根据权限来判断
	                    //closable: item.closable,
	                    closable:false,
	                    text:chs[i].title,
	                    //hidden: item.hidden && !item.hiddenByLayout, // only hide if it wasn't hidden by the layout itself
	                    //tooltip: item.tooltip,
	                    tabBar: me.tabBar,
	                    position: me.tabPosition
	                    //closeText: item.closeText
	                })
	    	}
	    	me.tabBar.add(chs);*/
	    	me.tabBar.on('change',me.onTabChange,me);//me.fireEvent('change', me, tab, tab.card);
    	}
    	if(typeof activeTab == 'number'){
    		me.tabBar.setActiveTab(me.tabBar.getComponent(activeTab));
    	}
    },
    /*
    onShow:function(){
    	var me = this;
    	me.callParent(arguments);
    	if(!me.activeTab)
    	{
    		//this.setActiveTab(0);
    		
    		var its = me.tabBar.items.items,len = its.length;
    		var tab;
    		for(var i=0;i<len;i++)
    		{
    			tab = its[i];
    			if (tab && tab.isDisabled && !tab.isDisabled())
    			{
	            	me.onTabChange(me.tabBar,tab,tab.card);
	                tab.focus();
	                return;
    	        }
    		}
    	}
    },*/
    onTabChange:function(tabbar,tab,card){
    	var me = this;
    	if(card)
    	{
    		//this.setActiveTab(tab.card);
    		var previous = me.getActiveTab();
            /*if (previous !== card && me.fireEvent('beforetabchange', me, card, previous) === false) {
                return false;
            }*/
            me.activeTab = card;
            Ext.suspendLayouts();//暂停布局 其后必须跟着Ext.resumeLayouts()
            me.layout.setActiveItem(card);
            Ext.resumeLayouts(false);//设置成为false，执行时间大为缩减
            card = me.activeTab = me.layout.getActiveItem();
            if (card && card !== previous) {
                //me.tabBar.setActiveTab(card.tab);
                //Ext.resumeLayouts(true);
                if (previous !== card) {
                    me.fireEvent('tabchange', me, card, previous);
                }
            }else{
                //Ext.resumeLayouts(true);
            }
    	}else if(tab.ctype){
	    	var ctype = tab.ctype;
	    	
	    	var T = Ext.ClassManager.get(ctype);
	    	if(T){
	    		var card = new T(tab.cardConfig);
	    		me.add(card);
	    		me.setActiveTab(card);
	    	}else{
	    		var hold = tab;
	    		Ext.require(ctype,function(){
	    			var cd = Ext.create(hold.ctype,hold.cardConfig);
	    			this.add(cd);
	    			this.setActiveTab(cd);
	    		},me);
	    		if(me.activeTab){
	    			me.activeTab.hide();
	    		}
	    		me.setLoading('正在加载模块文件.');
	    	}
    	}
    },
    addSection:function(item){
    	var me = this;
    	var tab;
    	if(item.ctype){
    		var itm = Ext.apply({
            			xtype:'tab',
            			tabBar: me.tabBar,
	                	position: me.tabPosition,
	                	ui: me.tabBar.ui,
            			text:item.title
            		},item);
            itm.cardConfig = item;
            //delete itm.ctype;
            tab = me.tabBar.add(itm);
    	}else{
    		tab = me.tabBar.add(item);
    	}
    	//setTimeout(function(){
    		me.tabBar.setActiveTab(tab);//直接加入直接显示
    	//},50);
    	
    },
    onAdd: function(item, index) {
    	//debugger
        var me = this;
    	if (me.rendered && me.loadMask){
            Ext.destroy(me.loadMask);
            me.loadMask = null;
    	}
    	var tab = null;
        var tabs = me.tabBar.items.items;
        for(var i=0,len=tabs.length;i<len;i++)
        {
        	tab = tabs[i];
        	if(!tab.card && tab.text == item.title && tab.ctype == item.ctype)//以title 和 ctype来判断
        	{
        		break;
        	}else{
        		tab = null;
        	}
        }
        if(tab){
        	item.tab = tab;
        	//tab.tabBar = me.tabBar;
        	tab.card = item;
        	//tab.position = me.tabPosition;
        }else{
	        var cfg = item.tabConfig || {},
	            defaultConfig = {
	                xtype: 'tab',
	                ui: me.tabBar.ui,
	                card: item,
	                disabled: item.disabled,
	                closable: item.closable,
	                hidden: item.hidden && !item.hiddenByLayout, // only hide if it wasn't hidden by the layout itself
	                tooltip: item.tooltip,
	                tabBar: me.tabBar,
	                position: me.tabPosition,
	                closeText: item.closeText
	            };
	
	        cfg = Ext.applyIf(cfg, defaultConfig);
	        item.tab = me.tabBar.insert(index, cfg);
        }
        
        item.on({
            scope : me,
            enable: me.onItemEnable,
            disable: me.onItemDisable,
            beforeshow: me.onItemBeforeShow,
            iconchange: me.onItemIconChange,
            iconclschange: me.onItemIconClsChange,
            titlechange: me.onItemTitleChange
        });
        if (item.isPanel) {
            if (me.removePanelHeader) {
                if (item.rendered) {
                    if (item.header) {
                        item.header.hide();
                    }
                } else {
                    item.header = false;
                }
            }
            if (item.isPanel && me.border) {
                item.setBorder(false);
            }
        }
    }
});