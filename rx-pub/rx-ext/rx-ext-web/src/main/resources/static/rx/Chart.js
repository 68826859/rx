Ext.define('Rx.Chart', {
	singleton:true,
	requires:['Ext.util.CSS'],
	extend:'Ext.util.Observable',
    constructor: function(config) {
    	var me = this;
        me.callParent(arguments);
        Ext.onReady(function(){
        	//me.fireEvent('complete',me,f,res);
        },me);
    },
	onReady:function(fn,scope){
		if(Ext.chart && Ext.chart.Chart){
			fn.call(scope);
		}else{
			if(!this.isLoading){
				Ext.util.CSS.swapStyleSheet("chart-classic",Ext.ExtBasePath+"packages/charts/classic/classic/resources/charts-all.css");
				Ext.util.CSS.swapStyleSheet("chart-triton",Ext.ExtBasePath+"packages/charts/classic/triton/resources/charts-all.css");
				Ext.Loader.loadScript({url:Ext.ExtBasePath+"packages/charts/classic/charts.js",onLoad:this.onload,onError:this.onerror,scope:this});
				this.isLoading = true;
			}
			this.addListener('complete',fn,scope);
		}
	},
	isLoading:false,
	onload:function(){
		this.isLoading = false;
		this.fireEvent('complete',this);
	},
	onError:function(){
		this.isLoading = false;
	}
},function(){
	
});