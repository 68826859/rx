/*
 * 
 * */
Ext.define('Rx.view.file.FileInputField', {
    extend: 'Ext.container.Container',
    requires:[
    'Ext.form.field.Hidden',
    'Ext.form.field.Text',
    'Ext.Img',
	'Ext.form.field.File',
	'Rx.widget.Message',
	'com.rx.pub.file.controller.FileController'
	],
	uses:['Rx.view.file.FileSelectWindow'],
    alias:'widget.fileinput',
    cls:'r-fileinputfield',
    
	setValue:function(id){
		this.getComponent('field').setValue(id);
	},
	getValue:function(){
		return this.getComponent('field').getValue();
	},
	needRotate:true,//是否需要旋转，默认需要
	getImgViewerHref:function(id){
		//if(this.needRotate){
			var re = Ext.Object.toQueryString({src:id});
			return Ext.ServerContextPath +'/imgviewer.html?'+re;
		//}else{
		//	return this.getImgPath(id);
		//}
	},
	getImgPath:function(v,w,h){
		return v;
		//return Rich.Urls.getImgPath(v,w,h);
	},
	
	fullPath:false,
	typeSuffix:null,
	contentTypes:null,
	reuseKey:null,
    fileAccess:null,
	
    initComponent:function(){
    	var me = this;
    	var xType = me.initialConfig.readOnly?'textfield':'triggerfield';
    	var tc = me.initialConfig.readOnly?undefined:'x-fa fa-image';
        var allowBlank = me.initialConfig.allowBlank === false?false:true;
        var scalable = me.initialConfig.scalable;

        var href = me.initialConfig.value?me.getImgViewerHref(me.initialConfig.value):"javascript:void(0);";
        //var src = me.initialConfig.value?me.getImgPath(me.initialConfig.value,60,60):Ext.BLANK_IMAGE_URL;

        var src = Ext.BLANK_IMAGE_URL;
        
    	Ext.apply(me,{
    		style:'padding-top:5px;padding-bottom:5px;',
        	layout:{
        		type:'hbox',
        		align:'stretch'
        	},
        	items:[{
        		style:'border:1px dashed #d0d0d0;margin-right:5px;width:60px;height:60px;overflow:hidden;',
        		xtype:'image',
        		autoEl:{tag:'a',href:href,target:'_blank',style:'border:1px dashed #d0d0d0;margin-right:5px;width:60px;height:60px'},
        		itemId:'_img_',
        		width:60,
		        height:60,
		        src:src,
		        alt:'图片',
		        anchor:'100% 100%'
        	},{
        		itemId:'field',
        		xtype:xType,
        		name:me.initialConfig.name,
        		flex:1,
        		labelAlign:'top',
        		hideLabel:me.initialConfig.hideLabel,
        		fieldLabel:me.initialConfig.fieldLabel,
        		value:me.initialConfig.value,
        		editable:me.initialConfig.editable,
        		allowBlank:allowBlank,
        		msgTarget:me.initialConfig.msgTarget,
        		triggerCls:tc,
        		transformRawValue:function(v){
        			var fi = this.up('fileinput');
        			var imgf = fi.getComponent('_img_');
        			if(v && v != ""){
        				FileController.getPath({
        					params:{path:v,width:60,height:60},
        					callback:function(o,f,r){
        						if(f){
        							var imgff = this.getComponent('_img_');
        							imgff.setSrc(r.responseJson.data);
                            		imgff.el.dom.href = this.getImgViewerHref(o.params.path);
        							imgff.el.dom.target = '_blank';
        						}
        					},
        					scope:fi
        				});
        			}else{
        				if(imgf){
        					imgf.setSrc(Ext.BLANK_IMAGE_URL);
        					imgf.el.dom.href = "javascript:void(0);";
        					imgf.el.dom.target = '_self';
        				}
        			}
        			return v;
        		},
	            onTriggerClick:function(){
	            	var ct = this.up('fileinput');
	            	var win = Ext.create('Rx.view.file.FileSelectWindow',{
		            	sizeTarget:'viewport',
		            	multiSelect:false,
		            	canUpload:true,
		            	uploadParam:{
		            		fileValidateMsg:ct.fileValidateMsg,
		            		typeSuffix:ct.typeSuffix,
		            		contentTypes:ct.contentTypes,
		            		fullPath:ct.fullPath,
		            		reuseKey:ct.reuseKey,
                            fileAccess:ct.fileAccess
		            	}
	            	});
	            	win.showFor(this.valueBack,this);
	            },
	            valueBack:function(rds){
	            	if(rds && rds.length > 0)
	            	{
	            		var rd = rds[0];
	            		var imgid = rd.get('id');
	            		this.setValue(imgid);
	            	}
	            }
            }
            // ,{
            //     xtype:'button',
            //     style:'float:right;',
            //     cls:'r-btn-highlight r-btn-transparent',
            //     icon: Rx.Icons.png_16_7.path,
            //     handler:function(btn){
            //         var v = btn.up('validfileinput').value;
            //         var o = btn.up('validfileinput').ocrUrl;
            //
            //
            //     }
            // }
            ]
    	});
    	me.callParent(arguments);
    	
    	if(me.initialConfig.value){
    		
    	}
    }
});