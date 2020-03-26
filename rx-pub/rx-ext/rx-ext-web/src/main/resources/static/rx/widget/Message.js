Ext.define('Rx.widget.Message',{
    //extend: 'Ext.AbstractManager',
    //alternateClassName: 'Rich.WindowManager',
    //singleton: true,
    //typeName: 'xtype',
	requires:['Ext.window.MessageBox'],
	createBox:function(t,s,isError){
		 /*return ['<div class="msg" style="border-radius: 8px;-moz-border-radius: 8px;background: #F6F6F6;-moz-opacity:0.8;opacity:0.8;filter:alpha(opacity=80);border:1px solid #ccc;margin-top: 2px;padding: 10px 15px;color:#555;">',
                '<div class="x-box-tl"><div class="x-box-tr"><div class="x-box-tc"></div></div></div>',
                '<div class="x-box-ml"><div class="x-box-mr"><div class="x-box-mc"><h3>', t, '</h3>', s, '</div></div></div>',
                '<div class="x-box-bl"><div class="x-box-br"><div class="x-box-bc"></div></div></div>',
                '</div>'].join('');*/
		var bg = isError?'#FF7777':'#F6F6F6';
		var cr = isError?'#FFF':'#555';
       return '<div style="width:340px;border-radius:5px;-moz-border-radius:5px;background-color:'
       			+ bg +';-moz-opacity:1.0;opacity:1.0;filter:alpha(opacity=100);border:1px solid #ccc;margin-top: 2px;padding: 10px 15px;color:'
       			+ cr +';"><h3 style="margin: 0 0 8px;font-weight:bold;font-size:15px;">' 
       			+ t + '</h3><p style="margin:0;overflow-wrap:break-word;">'
       			+ s + '</p></div>';
	},
	createWaitBox:function(mg,id){
		return '<div id="'+ id +'" style="border-radius:5px;-moz-border-radius:5px;background: #F6F6F6;-moz-opacity:1.0;opacity:1.0;filter:alpha(opacity=100);border:1px solid #ccc;margin-top: 2px;padding: 5px 7px;color:#555;">'
			+'<div class="x-mask-msg-text" style="margin:0;padding: 0 0 0 21px;background-position: left 0;width:100%;overflow-wrap:break-word;">' + mg + '</div></div>';
	},
	constructor: function(){
        var me = this;
        me.callParent(arguments);
        Ext.onReady(me.init,me);
    },
    /**
     * private
     * @returns
     */
    getMsgCt:function(){
    	var me = this;
    	if(!me.msgCt)
    	{
    		me.msgCt = Ext.DomHelper.insertFirst(document.body, {style:'position:absolute;left:50%;top:5px;max-width:680px;min-width:340px;margin-left:-200px;z-index:200000;'}, true);
    	}
    	return me.msgCt;
    },
    init:function(){
    	this.getMsgCt();
    },
    alert:function(title,format,delay){
    	var msgct = this.getMsgCt();
        var s = Ext.String.format.apply(String, Array.prototype.slice.call(arguments, 1));
        var m = Ext.DomHelper.append(msgct, this.createBox(title,s), true);
        m.hide();
        m.slideIn('t').ghost("t",{delay:(delay?delay:2500),remove:true});
    },
    error:function(title,format){
    	var msgct = this.getMsgCt();
        var s = Ext.String.format.apply(String, Array.prototype.slice.call(arguments, 1));
        var m = Ext.DomHelper.append(msgct, this.createBox(title,s,true), true);
        m.hide();
        m.slideIn('t').ghost("t",{delay:3000,remove:true});
    },
    wait:function(msg,key){
    	//this.cancelWait(key);
    	var wbox = document.getElementById(key);
    	if(wbox){
    		wbox.firstChild.innerHTML = msg;
    	}else{
    		var msgct = this.getMsgCt();
    		var m = Ext.DomHelper.append(msgct,this.createWaitBox(msg,key), true);
    	}
        //m.hide();
        //m.slideIn('t');
    },
    cancelWait:function(key){
    	var el = Ext.get(key);
    	while(el){
    		el.stopAnimation();
    		el.remove();
    		el = Ext.get(key);
    	}
    },
    parseResult:function(res){
    	//{"type":${msge.type},"msg":"${msge.msg}","success":${msge.result},"data":[]}
    	var type = res.type;
    	/*if(type == 0)
    	{
    		
    	}else */
    	if(type == 1){
    		Rx.Msg.alert('消息',res.msg);//无需关闭的提示
    	}else if(type == 2){
    		Ext.Msg.alert('消息',res.msg);//需要关闭的提示
    	}else if(type == 3){
    		Rx.Msg.error('错误',res.msg);//无需关闭的错误
    	}else if(type == 4){
    		Ext.Msg.error('错误',res.msg);//需要关闭的错误
    	}else if(type == -1)//-1是登录超时
    	{
    		if(!window.isDevelop)
    		window.location = Rich.Urls.loginPath;
    	}
    	if(res.success === true)
    	{
    		return true;
    	}
    	return false;
    }
},
function(){
	Rx.Msg = new this();
});