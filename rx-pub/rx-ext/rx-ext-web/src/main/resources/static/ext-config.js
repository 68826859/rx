Ext.BLANK_IMAGE_URL= "resources/images/s.gif";
Ext.PATH_APP_RESOURCES = "resources/";
Ext.Loader.setConfig({
	enabled:true,
	disableCaching:true
});
Ext.Loader.addClassPathMappings({
	'Rx': 'rx',
	'cn': 'define',
	'com': 'define',
	'tech': 'define',
	'Ext':Ext.ExtBasePath + 'packages/core/src',
	'Ext.chart':Ext.ExtBasePath + 'packages/charts/src/chart',
	'Ext.draw':Ext.ExtBasePath + 'packages/charts/src/draw',
	'Ext.ux':Ext.ExtBasePath + 'packages/ux/classic/src'
});

var Rx = Rx || {};

(function(){
	Ext.apply(Rx,{
		onReady:function(fn,scope){
			Ext.require([
			'Rx.util.Relax',
			'Rx.ux.FormFieldVTypes',
			'Rx.ux.Action',
			'Rx.ux.ActionColumn',
			'Rx.ux.Button',
			'Rx.ux.Component',
			'Rx.ux.Container',
			'Rx.ux.GridGrouping',
			'Rx.ux.GridPanel',
			'Rx.ux.HtmlEditor',
			'Rx.ux.JsonAjax',
			'Rx.ux.Tabbar',
			'Rx.ux.Table',
			'Rx.widget.Message',
			'Rx.widget.PagingGrid',
			'Rx.widget.ActionForm',
			'Rx.widget.CrudForm',
			'Rx.widget.CrudWindow',
			'Rx.widget.EnumColumn',
			'Rx.widget.EnumStore',
			'Rx.widget.FitToolbar',
			'Rx.widget.ParamForm',
			'Rx.widget.RemoteColumn',
			'Rx.widget.SectionPanel',
			'Rx.widget.SectionTabpanel',
			'Rx.widget.SectionWindow',
			'Rx.widget.ServerMethod',
			'Rx.widget.ServerProvider',
			'Rx.widget.Window',
			'com.rx.extrx.IconCls',
			'com.rx.extrx.Icons',
			'com.rx.extrx.Icons16'
			],function(){
				Ext.onReady(fn,scope);
			});
		},
		redirectTo:function(){
			Ext.app.BaseController.prototype.redirectTo.apply(window,arguments);
		},
		redirectToModule:function(md,param){
			if(typeof param == 'string'){
				Rx.redirectTo(md+'\/'+param);
			}else if(typeof param == 'object'){
				Rx.redirectTo(md+'\/?'+Ext.Object.toQueryString(param));
			}else{
				Rx.redirectTo(md);
			}
		}
	});
})();



Ext.Inventory.prototype.getPath2 = Ext.Inventory.prototype.getPath;
Ext.Inventory.prototype.getPath = function(className){
	var me = this,
            paths = me.paths,
            ret = '',
            prefix;
        if (className in paths) {
            ret = paths[className];
        }
        else {
            prefix = me.nameToPrefix[className] || (me.nameToPrefix[className] = me.getPrefix(className));
            
            if (prefix) {
                ret = paths[prefix];
                if (ret) {
                	if(ret == 'define'){
	            	 	return 'ext/define?className='+ className;
	            	 }
                    ret += '/';
                }
                className = className.substring(prefix.length + 1);
            }
            ret += className.replace(me.dotRe, '/') + '.js';
        }
        return ret;
}
window.console = window.console || (function () {  
    var c = {}; c.log = c.warn = c.debug = c.info = c.error = c.time = c.dir = c.profile  
    = c.clear = c.exception = c.trace = c.assert = function () { };  
    return c;  
})();
Ext.Loader.loadScriptss=function(urls,callback,scope){
	var paths = [].concat(urls);
	var onerror = function(){
		callback.call(scope,false);
	};
	var onload = function(){
		if(paths.length > 0){
			Ext.Loader.loadScript({url:paths.shift(),onLoad:onload,onError:onerror,scope:Ext.Loader});
		}else{
			callback.call(scope,true);
		}
	};
	var url = paths.shift();
	Ext.Loader.loadScript({url:url,onLoad:onload,onError:onerror,scope:Ext.Loader});
};