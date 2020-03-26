/*
 * 
 * */
Ext.define('Rx.component.FileInputField', {
    extend: 'Ext.container.Container',
    requires:[
    'Rich.Url',
    'Ext.form.field.Hidden',
    'Ext.form.field.Text',
    'Ext.Img',
	'Ext.form.field.File',
	'Rx.widget.Message'
	],
	uses:['Rich.view.file.FileSelectWindow'],
    alias:'widget.fileinput',
    cls:'r-fileinputfield',
    fullPath:false,
	setValue:function(id){
		this.getComponent('field').setValue(id);
	},
	getValue:function(){
		return this.getComponent('field').getValue();
	},
	needRotate:true,//是否需要旋转，默认需要
	getImgViewerHref:function(id){
		if(this.needRotate){
			var re = Ext.Object.toQueryString({src:this.getImgPath(id)});
			return Ext.ServerContextPath +'/imgviewer.html?'+re;
		}else{
			return this.getImgPath(id);
		}
	},
	getImgPath:function(v,w,h){
		return v;
		//return Rich.Urls.getImgPath(v,w,h);
	},
	typeSuffix:null,
	contentTypes:null,
    initComponent:function(){
    	var me = this;
    	var xType = me.initialConfig.readOnly?'textfield':'triggerfield';
    	var tc = me.initialConfig.readOnly?undefined:'x-fa fa-image';
        var allowBlank = me.initialConfig.allowBlank === false?false:true;
        var scalable = me.initialConfig.scalable;

        var href = me.initialConfig.value?me.getImgViewerHref(me.initialConfig.value):"javascript:void(0);";
        var src = me.initialConfig.value?me.getImgPath(me.initialConfig.value,60,60):Ext.BLANK_IMAGE_URL;

    	Ext.apply(me,{
    		style:'padding-top:5px;padding-bottom:5px;',
        	layout:{
        		type:'hbox',
        		align:'stretch'
        	},
        	items:[{
        		style:'border:1px solid #d0d0d0;margin-right:5px;width:60px;height:60px;overflow:hidden;',
        		xtype:'image',
        		autoEl:{tag:'a',href:href,target:'_blank',style:'width:60px;height:60px'},
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
        		fieldLabel:me.initialConfig.fieldLabel,
        		value:me.initialConfig.value,
        		editable:false,
        		allowBlank:allowBlank,
        		msgTarget:me.initialConfig.msgTarget,
        		triggerCls:tc,
        		transformRawValue:function(v){
        			var fi = this.up('fileinput');
        			var imgf = fi.getComponent('_img_');
        			if(v && v!=""){
        				if(imgf){
        					imgf.setSrc(fi.getImgPath(v,60,60));
                            imgf.el.dom.href = fi.getImgViewerHref(v);
        					imgf.el.dom.target = '_blank';
        				}
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
	            	var win = Ext.create('Rich.view.file.FileSelectWindow',{sizeTarget:'viewport',fileValidateMsg:ct.fileValidateMsg,typeSuffix:ct.typeSuffix,contentTypes:ct.contentTypes,multiSelect:false,canUpload:true});
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
    }
});