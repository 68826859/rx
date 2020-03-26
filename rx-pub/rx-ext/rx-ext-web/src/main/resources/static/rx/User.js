Ext.define('Rx.User', {
	requires:[
		'Rx.ux.JsonAjax',
		'Rx.ux.Table',
		'Rx.ux.Action',
		'Rx.ux.Button',
		'Rx.ux.Component',
		'Rx.ux.Container',
		'Rx.ux.GridGrouping',
		'Rx.ux.GridPanel',
		'Rx.ux.TreeColumn',
		'Rx.ux.TreeItem',
		'Rx.ux.TriggerField',
		'Rx.ux.HtmlEditor',
		'Rx.ux.Tabbar',
		'Rx.widget.Message',
		'Rx.ux.ActionColumn',
		'Rx.ux.TreePicker'
	],
	extend:'Ext.util.Observable',
	userName:null,
	isAdmin:false,
	
	rxUserClass:null,
	rights:[],
	
    constructor: function(config) {
    	var me = this;
        Ext.apply(this, config || {});
        //this.all = new Ext.util.HashMap();
        this.rights = this.rights || [];
        //me.addEvents('complete');
        
        if(me.rxUserClass){
        	Ext.data.request.Ajax.prototype.rxUserClass = me.rxUserClass;
        }
        
        me.callParent();
        Ext.onReady(function(){
        	Ext.tip.QuickTipManager.init();
        	this.init();
        	
        },me);
        //return me;
        Rx.User.instance = me;
    },
	init:Ext.emptyFn,
	
	doFreshUser:Ext.emytyFn,
	freshUserBack:function(o,f,r){
		if(f){
			var info = r.responseJson.data;
			this.setUserInfo(info);
		}
	},
    setUserInfo:function(info){
    	var oinfo = this.userInfo;
    	this.userInfo = info;
    	var dif = false;
		if(info != oinfo){
			dif = true;
			//if(info && oinfo && info.userName == oinfo.userName){
			//	dif = false;
			//}
		}
		if(dif){
			this.fireEvent('userchange',this);
		}
    },
    
    
    
    /**
     * 初始化回调函数
     * @private
     */
    initCallback:function(o,f,res){
    	var me = this;
    	if(f){
            	me.rights = res.responseJson.data.rights;
            	me.userInfo = res.responseJson.data.userInfo;
            	me.UserName = me.userInfo.nickName;
            	me.isReady = true;
            	Ext.Ajax.autoRefresh = true;
            	var i=0,len = me.rights.length;
            	var codes = [];
            	var names = [];
            	for(;i<len;i++){
            		codes.push(me.rights[i].code);
            		names.push(me.rights[i].name);
            	}
            	me.rightCodes = codes;
            	me.rightNames = names;
            	me.rightCodeString = ','+codes.join(',') + ',';
            	me.rightNameString = ','+names.join(',') + ',';
            	if(me.doFreshUser != Ext.emptyFn){
            		window.setInterval(function(){me.doFreshUser();},5*60*1000);//5分钟检查一次
            	}
    	}
    	me.fireEvent('complete',me,f,res);
    },
    /**
     * 注册ready事件,此事件触发肯定是在Ext.onReady之后
     * @public
     */
    onReady:function(fn,scope,option){
    	var me = this;
    	me.addListener('complete',fn,scope,option);
    },
    /**
     * 通过id获得一个right
     *
     * @public
     */
    getRightById:function(id){
    	var rts = this.rights;
    	var i;
    	for(i in rts)
		{
    		if(rts[i].code == id)
    		{
    			return rts[i];
    		}
		}
    	return null;
    },
    hasRight:function(rightId){
    	if(!rightId)return true;
    	if(Ext.isArray(rightId)){
    		for(var o in rightId){
    			if(this.hasRight(rightId[o])){
    				return true;
    			}
    		}
    		return false;
    	}
    	var rep = ','+rightId +',';
    	return this.rightCodeString.indexOf(rep) != -1 || this.rightNameString.indexOf(rep) != -1;
    }
	
},function(){
	Rx.User.hasRight = function(rightId){
		if(Rx.User.instance){
			return Rx.User.instance.hasRight(rightId);
		}
		return false;
	}
});