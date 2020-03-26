/**
 * create by Ray 2018-4-12
 */
Ext.define('Rx.widget.ActionForm', {
	requires:['Rx.util.Relax'],
    uses:[],
    extend: 'Ext.form.Panel',
    alias:'widget.actionform',
    layout: 'anchor',
	bodyStyle:'overflow-y:auto;padding:10px;',
	defaults:{
		anchor:'100%'
	},
	keepParentItems:true,
	loadAction:null,
	loadParams:null,
	autoLoad:true,
	initRecord:null,
	initValues:null,
	submitAction:null,
	submitParams:null,
	autoCreateBtns:false,
	trackResetOnLoad:true,
	formActions:null,
	actionBack:function(fm,res,data){
		var win = this.up("richwindow");
		if(win){
			win.returnValue(fm,res,data);
		}
	},
	_actionBack:function(fm,res,data){
		this.setLoading(false);
		if(this.actionBack){
			this.actionBack(fm,res,data);
		}
	},
	btnHandler:function(){return true;},
	storeHandler:function(){
		return this.up('grid').getStore();
	},
	doAutoLoad:function(){
		Rx.Relax.lazy(100,this,'_autoLoadHandler');
	},
	_loadSuccess:function(){
		this.setLoading(false);
		if(this.loadSuccess){
			this.loadSuccess.call(this,arguments);
		}
	},
	_loadFailure:function(){
		this.setLoading(false);
		if(this.loadFailure){
			this.loadFailure.call(this,arguments);
		}
	},
	_autoLoadHandler:function(){
		if(this.initValues){
			this.getForm().setValues(this.initValues);
		}
		if(this.initRecord){
			this.loadRecord(this.initRecord);
		}
		try{
			var fa = this.getFormAction('load');
			if(fa){
				var la = Ext.apply({},fa);
				if(this.loadParams){
					la.params = this.loadParams;
				}
				la.success = this._loadSuccess;
				la.failure = this._loadFailure;
				la.scope = this;
				this.setLoading('...');
    			this.load(la);
			}
		}catch(e){
			console.error(e);
		}
    },
	initItems:function(){
		var me = this;
		if(me.autoCreateBtns){
			me.dockedItems = me.dockedItems || [];
			me.dockedItems.unshift({
				xtyle:'toolbar',
				dock:'bottom',
				items:[{
					xtype:'button',
					text:'重置',
					itemId:'resetBtn',
					iconCls: 'x-fa fa-eraser',
					cls:'r-btn-font-normal',
			    	style:{float:'right',margin:"5px 10px 5px 0"},
					handler:function(){
						var fm = this.up('form');
						if(fm.btnHandler.call(fm,this) === false){
							return;
						}
						fm.getForm().reset();							
					}
				},{
					xtype:'button',
					text:'提交',
					itemId:'searchBtn',
					iconCls: 'x-fa fa-check',
					cls:'r-btn-font-normal',
			    	style:{float:'right',margin:"5px 10px 5px 10px"},
					handler: function(){
						var fm = this.up('form');
						if(fm.btnHandler.call(fm,this) === false){
							return;
						}
						if(!fm.isValid())
						{
							Rx.Msg.error('错误','有不合法的输入.');
							return;
						}
						fm.setLoading('...');
		    			fm.submit(Ext.apply({
		    				submitEmptyText:false,
						    scope: fm,
						    params:fm.submitParams,
						    success:fm._actionBack,
						    failure:fm._actionBack
						},fm.getFormAction('submit')));
	                }
				}]
			});
		}
		me.callParent(arguments);
	},
	getFormAction:function(type){
		var me = this;
		
		var act = null;
		if(type == 'submit'){
			if(me.submitAction){
				act = me.submitAction;
			}
		}else if(type == 'load'){
			if(me.loadAction){
				act = me.loadAction;
			}
		}
		 if(me.formActions && me.formActions.length > 0){
			var fa = me.formActions;
			for(var ai in fa){
				if(fa[ai].type == type){
					act = Ext.apply(fa[ai],act);
				}
			}
		 }
		 return act;
	},
	initComponent:function(){
		var me = this;
		me.callParent(arguments);
		if(this.autoLoad){
			this.doAutoLoad();
		}
	}
});
