Ext.define('Rx.widget.WindowManager', {
    extend: 'Ext.AbstractManager',
    alternateClassName: 'Rx.WindowManager',
    singleton: true,
    //typeName: 'xtype',
    constructor: function() {
        var me = this;
        me.callParent(arguments);
        this.all.getKey = function(o) {
            //return o.winId;
        	return o.id || o.itemId;
        };
    },
    createWindow:function(cls,cfg){
    	var tp = cls;
    	if(tp.indexOf('.') == -1){
    		tp = this.getTypeByWinId(cls);
    	}
    	return Ext.create(tp,cfg);
    },
    create: function(component, defaultType){
        if (typeof component == 'string') {
            return Ext.widget(component);
        }
        if (component.isComponent) {
            return component;
        }
        return Ext.widget(component.xtype || defaultType, component);
    },
    registerType: function(type, cls) {
        this.types[type] = cls;
        cls[this.typeName] = type;
        cls.prototype[this.typeName] = type;
    },
    getTypeByWinId:function(wid){
    	var mp = this.mapping;
    	for(var i=0,len=mp.length;i<len;i++)
    	{
    		if(mp[i].winId == wid)
    		{
    			return mp[i].type;
    		}
    	}
    	return null;
    },
    mapping:[
    ]
},
function () {
});