Ext.define('Rx.widget.CrudForm', {
	requires:[],
    uses:[],
    extend: 'Ext.form.Panel',
    alias:'widget.crudform',
    
    cUrl:null,
    rUrl:null,
    uUrl:null,
    dUrl:null,
    
	initComponent:function(){
		var me = this;
		
		var btns = me.buttons;
		var tid = me.tid;
		
		if(Ext.isArray(btns)){
			var it;
			for(var o in btns){
				it = btns[o];
				if(it == 'c'){
					btns[o] = {
						text:'保存',
		    	    	itemId:'c',
		    	    	//hidden:true,
		    	    	handler:function(btn){
		    	    		btn.up('crudform').doCreate();
		    	    	}
					};
				}else if(it == 'u'){
					btns[o] = {
						text:'修改',
		    	    	itemId:'u',
		    	    	hidden:true,
		    	    	handler:function(btn){
		    	    		btn.up('crudform').toStatus('u');
		    	    	}
					};
					btns.push({
						text:'保存修改',
		    	    	itemId:'u_',
		    	    	hidden:true,
		    	    	handler:function(btn){
		    	    		btn.up('crudform').doUpdate();
		    	    	}
					});
				}
			}
		}
		me.callParent(arguments);
	},
	toStatus:function(st){
		var t = this.lookupI('c',true);
		if(t){
			t.setVisible(st=='c');
		}
		t = this.lookupI('u',true);
		if(t){
			t.setVisible(st=='r');
		}
		t = this.lookupI('u_',true);
		if(t){
			t.setVisible(st=='u');
		}
	},
    doRetrieve:function(params){
		this.el.mask('加载中...');
		Ext.Ajax.request({
			method:'GET',
			//getMethod:function(){return "GET";},
			url:this.rUrl,       	
			params:params,
			callback:this._retrieveBack,
			scope:this
		});
	},
	_retrieveBack:function(o,f,r){
		this.el.unmask();
		this._callback(o,f,r,'r');
	},
	doUpdate:function(){
    	var vas = this.getFormValues('u');
    	if(vas == null)return;
		this.el.mask('修改中...');
		Ext.Ajax.request({
			method:'post',
			//getMethod:function(){return "post"},
			url:this.uUrl,       	
			params:vas,
			callback:this._updateBack,
			scope:this
		});
    },
    _updateBack:function(o,f,r){
    	this.el.unmask();
    	this._callback(o,f,r,'u');
    },
	doCreate:function(){
		var vas = this.getFormValues('c');
		if(vas == null)return;
		this.el.mask('保存中...');
		Ext.Ajax.request({
			method:'post',
			//getMethod:function(){return "post"},
			url:this.cUrl,       	
			params:vas,
			callback:this._createBack,
			scope:this
		});
    },
    _createBack:function(o,f,r){
    	this.el.unmask();
    	this._callback(o,f,r,'c');
    },
    getFormValues:function(s){
    	return this.getValues();
    },
    _callback:function(o,f,r,s){
    	if(this.callback){
    		this.callback(o,f,r,s);
    	}
    	if(this.callbackProxy && (typeof this.callbackProxy.callback ==='function')){
    		this.callbackProxy.callback(o,f,r,s);
    	}
    },
    callback:null,
    callbackProxy:null
});
