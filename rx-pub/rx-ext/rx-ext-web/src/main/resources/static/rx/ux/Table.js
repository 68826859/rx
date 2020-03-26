Ext.define('Rx.ux.Table', {
	requires:['Ext.panel.Table'],
	override:'Ext.panel.Table',
	columnLines:true,
   	constructor: function (config){
		var me = this;
		if(Ext.isArray(me.columns)){
			if(config){
				var clums = null;
				var x = 0;
				if(Ext.isArray(config.columns)){
					clums = config.columns;
					x = 1;
				}else if(config.columns && Ext.isArray(config.columns.items)){
					clums = config.columns.items;
					x = 2;
				}
				if(clums){
					var nc = [];
					var cs = me.columns,csi;
					loop1:for(var i =0;i<cs.length;i++){
						csi = cs[i];
						for(var j =0;j<clums.length;j++){
							if(clums[j] && clums[j].dataIndex == csi.dataIndex){
								nc.push(Ext.apply({},clums[j],csi));
								clums[j] = null;
								continue loop1;
							}
						}
						nc.push(Ext.apply({},csi));
					}
					for(var j =0;j<clums.length;j++){
						if(clums[j]){
							nc.push(clums[j]);
						}
					}
					if(x == 1){
						config.columns = nc;
					}else if(x ==2){
						config.columns.items=nc;
					}
				}
				
			}
		}
		if(config && !me.noRight && config.rightId){
			if(Rx.User && !Rx.User.hasRight(config.rightId)){
				//config.emptyText='无权访问';
				config.autoLoad = false;
				me.noRight = true;
			}
		}
		me.callParent(arguments);
    },
    afterRender:function(){
		var me = this;
    	me.callParent(arguments);
    	if(me.noRight){
    		Rx.Relax.add(me,'setEmptyText','没有权限访问');
    		//me.setEmptyText("没有权限访问");
    	}
	},
	/**
	 * 重新刷新store，如果没有权限就弹出消息
	 * @param {} msg
	 */
	refreshOnRight:function(msg,loadOption){
		if(!this.noRight){
			this.getStore().reload(loadOption);
		}else if(msg){
			Rx.Msg.alert('提示',msg);
		}
	},
	loadWithExtraParams:function(pa,cancelIfNoRight){
		var sto = this.getStore();
		sto.getProxy().extraParams = pa;
		if(cancelIfNoRight && this.noRight){
			return false;
		}
		sto.load();
		return true;
	}
},function(){
	
});