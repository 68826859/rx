Ext.define('Rx.ux.Action', {
	override:'Ext.form.action.Action',
    requires:['Ext.form.action.Action'],
    processResponse : function(response){
    	try{
	    	if(response.status == 200){
	    		var res = Ext.decode(response.responseText);
	    		res.success = true;
	    		response.responseText = Ext.encode(res);
	    	}
    	}catch(e){console.error(e)}
        return this.callParent(arguments);
    }
});