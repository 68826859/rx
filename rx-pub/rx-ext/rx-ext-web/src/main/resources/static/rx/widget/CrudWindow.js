Ext.define('Rx.widget.CrudWindow',{
	requires:['Rx.util.Relax'],
    extend: 'Rx.widget.Window',
    alias:'widget.crudwindow',
    uses:[],
    //resizable:false,
    minWidth:300,
    minHeight:200,
    
    crudForm:null,
    
    showByParams:function(params){
    	this.show();
    	this.getComponent(0).doRetrieve(params);
    },
    initComponent:function(){
    	var me = this;
    	var form  = this.crudForm;
    	if(Ext.isString(form)){
    		form = {xtype:form};
    	}
    	form.callbackProxy = me;
    	Ext.apply(me,{
    		layout:'fit',
    		items:form
    	});
    	me.callParent(arguments);
    },
    //CrudFrom 接口
    callback:function(o,f,r,s){
    	if(f && s == 'c'){
    		this.close(true);
    		this.returnValue(f);
    	}
    }
});