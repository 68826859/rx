/*
 * 窗体基类
 * */
Ext.define('Rx.widget.Window', {
    extend: 'Ext.window.Window',
    alternateClassName:['Rx.Window'],
    alias:'widget.richwindow',
    requires:['Rx.widget.WindowManager','Rx.util.Relax'],
    modal:true,
    shadow:'frame',
    /**
     * widthPercent,heightPercent 宽和高在没有设置的情况下，占页面可视区大小的比例
     */
    width:0.8,
    height:0.8,
    closeToolText:'关闭',
    sizeTarget:'active',//'viewport','active'
    constrainHeader:true,
    cls:'x-selectable',
    /*constructor: function(config) {
        var me = this;
        me.callParent(arguments);
        Rx.WindowManager.register(me);
    },*/
    initConfig:function(conf){
    	var me = this;
    	var target = conf.sizeTarget || this.sizeTarget;
    	if(Ext.isString(target)){
    		if(target == 'active'){
    			target = Ext.WindowManager.getActive();
    			if(!target){
    				target = 'viewport';
    			}
    		}
    	}
    	var wh = conf.width;
    	if(!wh){
    		wh = me.width;
    	}
        if(wh && Ext.isNumber(wh) && wh > 0 && wh < 1)
        {
        	var w = 0;
        	if(target == 'viewport'){
        		w = Ext.Element.getViewportWidth();
        	}else{
        		w = target.getWidth();
        	}
        	w = w*wh;
        	if(!Ext.isNumber(w) || w < 300){
        		w = 300;
        	}
        	conf.width = me.width = w;
        }
        wh = conf.height;
        if(!wh){
    		wh = me.height;
    	}
        if(wh && Ext.isNumber(wh) && wh > 0 && wh < 1)
        {
        	var h = 0;
        	if(target == 'viewport'){
        		h = Ext.Element.getViewportHeight();
        	}else{
        		h = target.getHeight();
        	}
        	h = h*wh;
        	if(!Ext.isNumber(h) || h < 300){
        		h = 300;
        	}
        	conf.height = me.height = h;
        }
    	me.callParent(arguments);
    	Rx.WindowManager.register(me);
    },
    destroy:function(){
    	var me = this;
    	Rx.WindowManager.unregister(me);
    	me.callParent(arguments);
    },
    
    
    returnFn:null,
    returnScope:null,
    showFor:function(fn,scope){
    	this.returnFn = fn;
    	this.returnScope = scope;
    	this.show();
    },
    returnValue:function(){
    	if(this.returnFn){
    		var res = this.returnFn.apply(this.returnScope || window,arguments);
    		if(res === false){
    		}else{
    			this.returnFn = null;
    			this.returnScope = null;
    			this.close(true);
    		}
    	}
    },
    close:function(delay){
    	if(delay === true){
    		Rx.Relax.lazy(50,this,'close');
    		return;
    	}
    	this.callParent(arguments);
    }
});