/*
 * 外键下拉框
 * */
Ext.define('Rx.widget.ParamComBox', {
    extend: 'Ext.form.field.ComboBox',
    alias:'widget.paramcombox',
    requires:[],
    editable:false,
    pageSize:0,
    triggerAction:'all',
    extraParams:null,
    autoLoadOnValue:true,
    initComponent:function(){
    	var me = this;
    	me.callParent(arguments);
    	if(me.store && me.extraParams){
        	me.store.getProxy().setExtraParams(me.extraParams);
        }
    }
});