Ext.define('Rx.view.file.List', {
	requires:[
		'Rx.widget.Message',
		'Ext.grid.Panel',
        'Ext.form.field.Hidden',
        'Ext.form.field.Trigger',
        'Ext.form.field.ComboBox',
        'Ext.grid.column.Action',
        'Rx.model.File',
        'Ext.form.field.Checkbox',
        'Ext.selection.CheckboxModel',
        'Rx.widget.PagingGrid',
        'Rx.component.EnumComboBox',
        'Ext.view.View',
        'Ext.ux.DataView.DragSelector',
        'Ext.ux.DataView.LabelEditor',
        'com.rx.pub.file.controller.FileController'
    ],
    uses:[],
    extend: 'Ext.panel.Panel',
    alias:'widget.filelist',
    
    
    multiSelect:false,//是否多选
    canUpload:false,//是否有上传按钮
    
    uploadParam:null,//图片上传参数
    
	initComponent:function(){
		
		var me = this;
		var multiSelect = me.multiSelect;
		var canUpload = me.canUpload;
		
		var url = FileController.list.url;
		var extraParams = {};
		if(me.uploadParam){
			Ext.apply(extraParams,me.uploadParam);
		}
	    var store = Ext.create('Ext.data.Store', {
	        model: 'Rx.model.File',
	        pageSize:24,
	        autoLoad:true,
	        proxy: {
	            type: 'ajax',
	            url: url,
	            extraParams:extraParams,
	            reader: {
	                type: 'json',
	                root: 'data.list',
	                totalProperty: 'data.total'
	            }
	        }
	    });
	    
	    var items = [];
	    if(canUpload){
	    	
	    	var formItems = [{
				xtype:'filefield',
				name: 'file',
				buttonOnly:true,
				style:{
					'background':'transparent'
				},
				//frame:true,
				fieldLabel:"",
				labelWidth:0,
				hideLabel:true,
				buttonText:"上传",
				listeners:{
					change:function(){
						var formCt = this.up('form'),form = formCt.getForm();
			            if(form.isValid()){
			            	if(Ext.isIE8){
			            		var win = formCt.up('window');
			            		if(win){
			            			win.el.mask("上传中");
			            		}
			            	}
			                form.submit({
			                	params:formCt.uploadParam,
			                    url:FileController.uploadFile.url,
			                    waitMsg: '上传中...',
			                    success:formCt.callback,
			                    failure:formCt.callback
			                });
			            }
					}
				}
			}];
				
			if(me.uploadParam){
				for(var o in me.uploadParam){
					formItems.push({
						xtype:'hiddenfield',
						name:o,
						value:me.uploadParam[o]
					});
				}
			}
			
	    	items = [{
				xtype:'form',
				uploadParam:me.uploadParam,
				dock:'left',
				width:50,
				height:32,
				bodyStyle:{
					'background':'transparent'
				},
				layout:'fit',
				callback:function(fm,act) {
                	if(Ext.isIE8){
                		var win = fm.owner.up('window');
	            		if(win){
	            			win.el.unmask();
	            		}
	            	}
                	var data = act.result.data;
                	if(data){
                		var hd = fm.owner.up("pagingtoolbar");
						//hd.getStore().reload();
						var listview = fm.owner.up('filelist').lookupI('imagesList');
						hd.getStore().loadPage(1,{
							params:{
								imgId:data
							},
						    scope: listview,
						    callback: function(reds, o, f){
						    	if(f){
						    		var imgId = o.request.config.params.imgId;
						    		Rx.Relax.add(this.getSelectionModel(),'select',this.getStore().getById(imgId));
							        //this.getSelectionModel().select(this.getStore().getById(imgId));
						    	}
						    }
						});
                	}else{
                		Rx.Msg.error('提示',act.result.alertMsg);
                	}
                },
				items:formItems
	    	}];
	    }
	    
	    
	    items.push({
	    	xtype:'button',
	    	itemId:'tarBtn',
	    	text:'未选择',
	    	href:'javascript:void(0);',
	    	hrefTarget:'_blank',
	    	cls:'r-btn-highlight r-btn-transparent'
	    	//autoEl:{tag:'a',href:'javascript:void(0);'}
	    });
	    
	    var pagebar = Ext.create('Ext.toolbar.Paging',{
	    	itemId:'pagetoolbar',
	    	dock: 'top',
	        store: store,
	        displayInfo: true,
	        displayMsg: '显示 {0} - {1} of {2}',
	        emptyMsg: "没有文件",
	        items:items
       	});
		/*
		 '<tpl for=".">',
	                    '<div class="thumb-wrap" id="{id:stripTags}">',
	                        '<div class="thumb"><img src="{url}" title="{name:htmlEncode}"></div>',
	                        '<span class="x-editable">{fileName:htmlEncode}</span>',
	                    '</div>',
	                '</tpl>',
	                '<div class="x-clear"></div>'
		 */
       	var w=160,h=160;
		Ext.apply(me,{
			layout:'fit',
			dockedItems: [pagebar],
			items:[{
				itemId:'imagesList',
				xtype:'dataview',
				style:'padding:10px;overflow-x:hidden;overflow-y:auto;',
				cls:'images-view',
				store: store,
	            tpl: [
	                '<tpl for=".">',
	                    '<div class="thumb-wrap" style="width:'+(w+12)+'px;height:'+(h+42)+'px;" id="{id:stripTags}">',
	                        '<div class="thumb"><img width="'+w+'" height="'+h+'" src="{src}" title="类型:{contentType}\n大小:{uploadSize} byte\n创建日期:{createTime}"></div>',
	                        '<span class="x-editable" title="{fileName}">{fileName:htmlEncode}</span>',
	                    '</div>',
	                '</tpl>',
	                '<div class="x-clear"></div>'
	            ],
	            multiSelect: multiSelect,
	            trackOver: true,
	            overItemCls: 'x-item-over',
	            itemSelector: 'div.thumb-wrap',
	            emptyText: '没有文件',
	            plugins: [
	                Ext.create('Ext.ux.DataView.DragSelector', {})
	                //Ext.create('Ext.ux.DataView.LabelEditor', {dataIndex: 'id'})
	            ],
	            prepareData: function(data) {
	                Ext.apply(data, {
	                    shortName: Ext.util.Format.ellipsis(data.id, 15),
	                    sizeString: Ext.util.Format.fileSize(data.uploadSize),
	                    dateString: Ext.util.Format.date(data.createTime, "m/d/Y g:i a")
	                });
	                return data;
	            },
	            listeners: {
	                selectionchange: function(dv, nodes ){
	                    //var l = nodes.length,
	                        //s = l !== 1 ? 's' : '';
	                	var tarb = this.up('panel').lookupI('tarBtn',true);
	                	if(nodes.length > 0){
	                    	var node = nodes[0];
	                    	tarb.setText('点击查看  > ' + node.get('fileName'));
	                    	tarb.el.dom.href = node.get('src');
	                	}else{
	                		tarb.setText('未选择');
	                		tarb.el.dom.href = "javascript:void(0);";
	                	}
	                }
	            }
			}]
		});
		this.callParent(arguments);
	}
});
