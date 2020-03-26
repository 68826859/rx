/*
 * 文件选择窗体
 * */
Ext.define('Rx.view.file.FileSelectWindow',{
	requires:['Rx.view.file.List'],
    extend: 'Rx.widget.Window',
    alias:'widget.fileselectwindow',
    uses:[],
    //resizable:false,
    width:0.6,
    height:0.8,
    minWidth:360,
    minHeight:300,
    layout:'fit',
    title:'文件选择',
    
    multiSelect:false,//是否多选
    canUpload:false,//能否上传
    
    uploadParam:null,//图片上传参数
    
    initComponent:function(){
    	var me = this;
    	var multiSelect = me.multiSelect;
		var canUpload = me.canUpload;
		var uploadParam = me.uploadParam;
    	Ext.apply(me,{
    		bodyStyle:'',
    	    items:[{
				xtype:'filelist',
				itemId:'list',
				multiSelect:multiSelect,
   				canUpload:canUpload,
   				uploadParam:uploadParam
			}],
    	    buttons:['->',{
    	    	text:'确定',
    	    	handler:function(btn){
    	    		var win = btn.up('fileselectwindow');
    	    		var grid = win.lookupI('list').getComponent(0);
    	    		var records = grid.getSelectionModel().getSelection();
    	    		win.close(true);
    	    		win.returnValue(records);
    	    	}
    	    },{
    	    	text:'取消',
    	    	handler:function(btn){
    	    		btn.up('fileselectwindow').close();
    	    	}
    	    }]
    	});
    	me.callParent(arguments);
    }
});