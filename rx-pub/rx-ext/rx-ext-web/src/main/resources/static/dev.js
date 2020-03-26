

Rx.onReady(function(){
	Ext.removeNode(document.getElementById('loading'));
	Ext.create('Ext.container.Viewport',{
		layout:{
			type:'hbox',
			align:'stretch'
		},
		
		items:[{
			title:'Rx.IconCls.*.code',
			//width:450,
			flex:1,
			frame:true,
			//height:300,
			margin:'10 0 10 10',
			//tbar:[],
			columnLines:true,
			xtype:'grid',
			columns:[{text:'name',dataIndex:'name',flex:1},{text:'code',flex:1,dataIndex:'code'},{text:'display',dataIndex:'code',renderer:function(v){
				return '<a class="'+v+'"/>';
			}}],
			store:new com.rx.extrx.IconCls()
		},{xtype:'splitter'},{
			title:'Rx.Icons.*.path',
			//width:450,
			flex:1,
			//tbar:[{text:'ddd'}],
			frame:true,
			margin:'10 10 10 0',
			columnLines:true,
			//height:300,
			xtype:'grid',
			columns:[{text:'name',dataIndex:'name',flex:1},{text:'path',flex:1,dataIndex:'path',width:100},{text:'display',dataIndex:'path',renderer:function(v){
				return '<img src="'+v+'"/>';
			}}],
			store:new com.rx.extrx.Icons()
		},{xtype:'splitter'},{
			title:'Rx.Icons16.*.path',
			//width:450,
			flex:1,
			//tbar:[],
			frame:true,
			margin:'10 10 10 0',
			columnLines:true,
			//height:300,
			xtype:'grid',
			columns:[{text:'name',dataIndex:'name',flex:1},{text:'path',flex:1,dataIndex:'path',width:100},{text:'display',dataIndex:'path',renderer:function(v){
				return '<img src="'+v+'"/>';
			}}],
			store:new com.rx.extrx.Icons16()
		}]
	});
});