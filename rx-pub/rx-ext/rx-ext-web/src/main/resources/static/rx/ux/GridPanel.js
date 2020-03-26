Ext.define('Rx.ux.GridPanel', {
	requires:['Ext.grid.Panel'],
	override:'Ext.grid.Panel',
	columnLines:true,
	emptyText:'没有数据',
   	constructor: function (config){
		var me = this;
		/*
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
					for(var i =0;i<cs.length;i++){
						csi = cs[i];
						for(var j =0;j<clums.length;j++){
							if(clums[j] && clums[j].dataIndex == csi.dataIndex){
								Ext.apply(csi,clums[j]);
								clums[j] = null;
								continue;
							}
						}
						nc.push(csi);
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
		*/
		/*
		try{
	    	var m = config?(config.store?config.store.model:null):null;
	    	if(typeof m == 'string'){
	    		try{
	    			m = Ext.data.schema.Schema.lookupEntity(m);
	    		}catch(e){console.warn(e);}
	    		if(typeof m != 'function'){
	    			Ext.syncRequire(m);
	    			m = Ext.data.schema.Schema.lookupEntity(m);
	    		}
	    	}
	    	if((typeof m=='function') && m.columns){
	    		
	    		var fds = m.columns,fd=null,cms=[],i=0,len=fds.length,clms=config.columns.items,j=0;len2=clms.length,clm=null;
	    		loop:for(;i<len;i++){
	    			fd = fds[i];
	    			for(j=0;j<len2;j++){
	    				clm = clms[j];
	    				if(clm.dataIndex && clm.dataIndex == fd.dataIndex){
	    					cms.push(Ext.apply({flex:1},clm,fd));
			    			clm._loaded_ = true;
			    			continue loop;
	    				}
	    			}
	    			cms.push(Ext.apply({flex:1},fd));
	    			
	    		}
	    		for(j=0;j<len2;j++){
					clm = clms[j];
					if(!clm._loaded_){
						cms.push(Ext.apply({flex:1},clm));
					}
				}
	    		config.columns.items = cms;
	    	}
		}catch(e){
			console.error(e);
		}
		*/
		me.callParent(arguments);
    }
},function(){
	Ext.view.Table.prototype.enableTextSelection = true;
});