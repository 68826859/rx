Ext.define('Rx.model.FkModel',{
	requires:[],
    extend: 'Ext.data.Model',
    idProperty: 'key',
    parentIdProperty:'parentId',
    fields:['key','display','parentId']
});