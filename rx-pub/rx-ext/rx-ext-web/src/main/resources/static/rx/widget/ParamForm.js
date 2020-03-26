/**
 * create by Ray 2017-10-19
 */
Ext.define('Rx.widget.ParamForm', {
	requires:['Rx.util.Relax'],
    uses:[],
    extend: 'Ext.form.Panel',
    alias:'widget.paramform',
    style:{padding:"10px"},
    layout:'auto',
	defaults:{
		xtype:'textfield',
		//labelWidth:70,
		labelWidth:'100%',
		style:{float:"left",margin:"5px 0 5px 10px"}
	},
	keepParentItems:true,
	autoSearch:false,
	autoCreateBtns:false,
	searchBtn:null,
	resetBtn:null,
	loadParams:null,
	searchBack:function(records,o,f){},
	btnHandler:function(){return true;},
	storeHandler:function(){
		return this.up('grid').getStore();
	},
	_autoSearchHandler:function(){
		try{
    		this.lookupI('searchBtn').handler();
		}catch(e){
			console.error(e);
		}
    },
	initItems:function(){
		var me = this;
		if(me.autoCreateBtns){
			me.items = me.items || [];
			me.items = me.items.concat(Ext.apply({
				xtype:'button',
				text:'清除',
				itemId:'resetBtn',
				iconCls: 'x-fa fa-eraser',
				cls:'r-btn-font-normal',
		    	style:{float:'right',margin:"5px 10px 5px 0"},
				handler:function(){
					var f = this.up('form');
					if(f.btnHandler.call(f,this) === false){
						return;
					}
					f.getForm().reset();							
				}
			},me.resetBtn),Ext.apply({
				xtype:'button',
				text:'查询',
				itemId:'searchBtn',
				iconCls: 'x-fa fa-search',
				cls:'r-btn-font-normal',
		    	style:{float:'right',margin:"5px 10px 5px 10px"},
				handler: function(){
					var f = this.up('form');
					if(f.btnHandler.call(f,this) === false){
						return;
					}
					var fm = f.getForm();
					if(!fm.isValid())
					{
						return;
					}
					var vs = fm.getValues();
					var sto = f.storeHandler.call(f);
					if(f.loadParams){
						Ext.applyIf(vs,f.loadParams);
					}
					sto.getProxy().extraParams = vs;
	    			
	    			this.setDisabled(true);
	    			sto.loadPage(1,{
					    scope: this,
					    callback:function(records,o,f){
					    	this.setDisabled(false);
					    	var ff = this.up('form');
			    			if(typeof ff.searchBack == 'function'){
			    				ff.searchBack.apply(ff,arguments);
			    			}
					    }
					});
                }
			},me.searchBtn));
		}
		me.callParent(arguments);
	},
	doAutoSearch:function(){
		var me = this;
		if(me.autoSearch && !me.isDisabled()){
			Rx.Relax.lazy(500,me,'_autoSearchHandler');
		}
		if(typeof me.searchBack == 'string'){
			var sto = me.storeHandler.call(me);
			if(sto){
				sto.on('load',me.searchBack);
			}
		}
	},
	afterRender:function(){
		var me = this;
		me.callParent(arguments);
		me.doAutoSearch();
	}
});
