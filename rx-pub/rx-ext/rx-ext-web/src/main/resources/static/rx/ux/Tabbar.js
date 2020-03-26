Ext.define('Rx.ux.Tabbar', {
	override:'Ext.tab.Bar',
	requires:['Ext.tab.Bar'],
	closeTab: function(toClose) {
        var me = this,card = toClose.card,tabPanel = me.tabPanel,toActivate;
        if (card && card.fireEvent('beforeclose', card) === false) {
            return false;
        }
        toActivate = me.findNextActivatable(toClose);
        Ext.suspendLayouts();
        if (tabPanel && card) {
        	if(toClose.closeAction == 'hide'){
        		toClose.hide();
        		card.hide();
        	}else{
	            delete toClose.ownerCt;
	            card.fireEvent('close', card);
	            tabPanel.remove(card);
	            if (!tabPanel.getComponent(card)) {
	                toClose.fireClose();
	                me.remove(toClose);
	            } else {
	                toClose.ownerCt = me;
	                Ext.resumeLayouts(true);
	                return false;
	            }
        	}
        }
        if (toActivate) {
            if (tabPanel) {
                tabPanel.setActiveTab(toActivate.card);
            } else {
                me.setActiveTab(toActivate);
            }
            toActivate.focus();
        }
        Ext.resumeLayouts(true);
    },
    setActiveTab: function(tab, initial) {
        var me = this;
        if (tab && !tab.disabled && tab !== me.activeTab) {
            if (me.activeTab) {
                if (me.activeTab.isDestroyed) {
                    me.previousTab = null;
                } else {
                    me.previousTab = me.activeTab;
                    me.activeTab.deactivate();
                }
            }
            if(tab.hidden){
            	tab.show();
            }
            tab.activate();
            me.activeTab = tab;
            me.needsScroll = true;
            if (!initial) {
                me.fireEvent('change', me, tab, tab.card);
                me.updateLayout();
            }
        }
    }
});