Ext.define('Rx.controller.BaseViewController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.rxbase',
    beforeInit:function(view){
    	var id = view.getItemId();
    	var url = id + '/:p1';
    	Ext.app.route.Router.connect(url,{
    		action:'onRouteChange'
    	},this);
    	
    	/*
    	url = id + '\?:p2';
    	Ext.app.route.Router.connect(url,{
    		action:'onRouteChange2'
    	},this);
    	
    	*/
    	
    	//this.updateRoutes(routes);
    },
    onRouteChange:function(p1){
    	this.actionParams(p1);
    },
    actionParams:function(p1){
    	if(typeof this.getView().actionParams == 'function'){
    		this.getView().actionParams(p1);
    	}
    },
    $recordHandler:function(view,rowIndex,collIndex,item,e,record,row){
    	var args = arguments;
    	Ext.Msg.confirm('消息',item.confirm,function(bId){
            if(bId=='ok' || bId =='yes'){
            	this[args[3].realHandler].apply(this,args);
            }
        },this);
    },
    doDeleteRecord:function(view,rowIndex,collIndex,item,e,record,row){
    	
    },
    doUpdateRecord:function(view,rowIndex,collIndex,item,e,record,row){
    }
});
