/**
 * create by Ray 2018-5-13
 */
Ext.define('Rx.spring.SpringProvider', {
	requires:[],
    uses:[],
    //maxRetries:0,
	//alias:'direct.spring',
	//type: 'spring',
    extend: 'Rx.widget.ServerProvider'
},function(){
	this.$onExtended.unshift({fn:function(cls,data){
		var acs = data.actions;
		var ns = data.namespace;
		var methods;
		for(var r in acs){
			if(data.$className.indexOf("."+r) != -1){
				methods = acs[r];
			}
		}
		if(methods.length > 0){
			var md,fn;
			for(var i in methods){
				md = methods[i];
				fn = function(pa){
					var mmd = arguments.callee.$method;
					Ext.Ajax.request(Ext.apply({url:mmd.url,method:mmd.method},pa));
				};
				fn.url = md.url;
				fn.method = md.method;
				fn.$method = md;
				cls[md.name] = fn;
			}
		}
	},scope:this});
});
