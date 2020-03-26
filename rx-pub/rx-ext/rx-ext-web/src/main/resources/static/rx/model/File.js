Ext.define('Rx.model.File',{
    extend: 'Ext.data.Model',
    idProperty: 'id',
    fields: [
       {name: 'id'},//图片id
       {name: 'fileName'},//图片名称
       {name: 'src'},//图片地址
       {name: 'contentType'},//文件类型
       {name: 'uploadSize', type: 'float'},//文件大小
       {name:'createTime'}//创建时间
    ]
});