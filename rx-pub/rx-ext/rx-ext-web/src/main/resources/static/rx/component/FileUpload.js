/*
 * 文件上传组件
 * */
Ext.define('Rx.component.FileUpload', {
    extend: 'Ext.panel.Panel',
    requires:[
    'Ext.form.field.Hidden',
    'Ext.form.Panel',
    'Ext.Img',
	'Ext.form.field.File',
	'Rx.widget.Message',
	'Ext.layout.container.Absolute'
	],
    alias:'widget.fileupload',
    
    url:"",
    name:'cover',//表单提交值用
    fieldLabel: '照片',
    labelWidth:100,
	buttonText: '选择图片',
	
	buttonStyle:{
		'background':'transparent',
		//'width':'76px',
		'margin-bottom':'0px !important'
	},
	
	hideLabel:false,
	value:null,
	src:null,
	
	formLayout:'fit',
	formItems:[],
	formStyle:'',
    
	setValue:function(va,img){
		this.lookupI('valueField').setValue(va);
		this.lookupI('displayField').setSrc(img);
	},
    style:{'border':'1px solid #b5b8c8'},
   	getUrl:function(){
   		return this.url;
   	},
   	
   	uploadBack:function(f,fm,act){
   		var data = act.result.data;
   		if(data){
    		this.setValue(data.id,data.src);
    	}else{
    		Rx.Msg.alert('提示','返回结果有误.');
    	}
   	},
   	
    initComponent:function(){
    	
    	var me = this;
    	Ext.apply(me,{		
        	layout:'absolute',
        	items:[{
        		itemId:'valueField',
        		xtype:'hiddenfield',
        		name:me.name,
        		value:me.value
        	},{
        		xtype:'image',
        		itemId:'displayField',
        		x:0,
		        y:0,
		        src:me.src,
		        alt:'图片',
		        anchor:'100% 100%'
        	},{
	        	x:0,
			    y:0,
			    anchor: '100% 100%',
				xtype:'form',
				layout:me.formLayout,
				style:me.formStyle,
				bodyStyle:{
					'background':'transparent'
					//'padding':'5px'
				},
				items:me.formItems.concat({
					xtype:'filefield',
					name: 'file',
					buttonOnly:true,
					style:me.buttonStyle,
					frame:true,
					fieldLabel: me.fieldLabel,
					labelWidth:me.labelWidth,
					hideLabel: me.hideLabel,
					buttonText: me.buttonText,
					listeners:{
						change:function(){
							//debugger
							var form = this.up('form').getForm();
				            if(form.isValid()){
				            	var url = this.up('fileupload').getUrl();
				                form.submit({
				                    url: url,
				                    waitMsg: '上传中...',
				                    success: function(fm,act) {
				                    	fm.owner.up('fileupload').uploadBack(true,fm,act);
				                    },
				                    failure:function(fm,act){
				                    	fm.owner.up('fileupload').uploadBack(false,fm,act);
				                    }
				                });
				            }else{
				            	Rx.Msg.error('提示','缺少必输入项');
				            	//this.setValue();
				            }
						}
					}
				})
        	}]
    	});
    	
    	me.callParent(arguments);
    }
});