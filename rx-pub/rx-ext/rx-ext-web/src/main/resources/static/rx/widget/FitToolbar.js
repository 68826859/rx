/*
 * 自适应toolbar
 * */
Ext.define('Rx.widget.FitToolbar',{
    extend: 'Ext.panel.Panel',
    alias:'widget.fittoolbar',
    layout:{
    	type: 'hbox',
        align: 'stretch'
    },
    btnMinWidth:26,
    defaultType:'button',
    /*
    initComponent:function(){
    },*/
    onResize:function(width, height, oldWidth, oldHeight){
    	this.doMyLayout(width, height);
    	this.callParent(arguments);
    },
    onBoxReady:function(w,h){
    	this.doMyLayout(w,h);
    	this.callParent(arguments);
    },
    doMyLayout:function(w,h){
    	var me = this;
    	var num = Math.floor(w/this.btnMinWidth);
    	

		var menu = me.moreMenu || (me.moreMenu = Ext.create('Ext.menu.Menu',{cls:'x-menu-24'}));
		
		var mitems = menu.items.items;
		var mlen = mitems.length;
		var items = this.items.items,it,b;
		var len = items.length;
		for(var i = 0;i < len;i++)
		{
			b = i<num;
			it = items[i];
			it.setVisible(b);
			if(i < mlen)//有菜单
			{
				mitems[i].setVisible(!b);
			}else{
				it = items[i];
				menu.add({
					text:(it.text || it.title),
					iconCls:it.iconCls,
					disabled:it.disabled,
					hidden:b,
					scope:it,
					handler:function(){
						this.handler.call(this.scope || this,this);
					}
				});
			}
		}
		var more = this.getDockedComponent('more');
		var hasMore = num < len;
		if(more)
		{
			more.setVisible(hasMore);
		}else if(hasMore){
			this.addDocked({
				itemId:'more',
				cls:'fittoolbar-more',
				dock:'right',
				moreMenu:menu,
				handler:function(mu,e){
					mu.moreMenu.showBy(mu);
				}
			});
		}
    	
    }
});