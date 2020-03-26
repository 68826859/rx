/*
 *字典管理
 */
Ext.define('Rx.view.dic.Main', {
    requires:[
       'com.rx.pub.dic.dto.DicDirGrid',
       'com.rx.pub.dic.dto.DicGrid',
       'com.rx.pub.dic.dto.DicAdd',
       'com.rx.pub.dic.dto.DicUpdate',
       'com.rx.pub.dic.controller.PubDictionaryController'
    ],
    extend: 'Ext.panel.Panel',
    uses:[],
    toCreate:function(rec){
    	Ext.create("Rx.widget.Window",{
			title:'新增项',
			layout:'fit',
			width:350,
			height:250,
			items:[{
				initValues:{
					parentId:rec.getId()
				},
				autoCreateBtns:true,
				xclass:'DicAdd'
			}]
		}).showFor(function(fm,res,data){
			if(res.response.status == 200){
				this.lookupI('dics').refreshOnRight('新增字典项成功.');
			}else{
				return false;
			}
		},this);
    },
    toUpdate:function(rec){
    	Ext.create("Rx.widget.Window",{
			title:'修改项',
			layout:'fit',
			width:350,
			height:250,
			items:[{
				initRecord:rec,
				autoCreateBtns:true,
				xclass:'DicUpdate'
			}]
		}).showFor(function(fm,res,data){
			if(res.response.status == 200){
				this.lookupI('dics').refreshOnRight('修改字典项成功.');
			}else{
				return false;
			}
		},this);
    },
    toDel:function(rec){
    	this.setLoading('移除中');
    	PubDictionaryController.del({
    		params:{
    			id:rec.getId()
    		},
    		callback:function(o,f,r){
    			this.setLoading(false);
    			if(f){
    				this.lookupI('dics').refreshOnRight('移除字典项成功.');
    			}
    		},
    		scope:this
    	});
    },
    toUpdatestair:function(rec,isUp){
    	PubDictionaryController.updatestair({
    		params:{
    			id:rec.getId(),
    			isUp:isUp
    		},
    		callback:function(o,f,r){
    			if(f){
    				this.lookupI('dics').getStore().reload();
    			}
    		},
    		scope:this
    	});
    },
    initComponent:function(){
        var me = this;
        Ext.apply(me,{
            layout: {
                type: 'hbox',
                align: 'stretch'
            },
            items:[{
                flex:2,
                frame:true,
                margin: '10 0 10 10',
                xclass:'DicDirGrid',
                forceFit:true,
                itemId:'list',
                title:'字典目录',
                listeners:{
					'select':function(me0, record, index, eOpts){
						this.getModule().lookupI('dics').loadWithExtraParams({
							parentId:record.getId()
						},true);
					}
				},
				columns:[{
			    	text:null,
			        xtype:'actioncolumn',
			        minWidth:40,
			        items: [{
			            icon: Rx.Icons.png_16_9.path,
			            iconCls:'r-column-visibility-over r-column-visibility-selected',
			            rightId:'新增字典项',
			            tooltip: '新增字典项',
			            handler: function(grid, rowIndex, colIndex) {
			                var rec = grid.getStore().getAt(rowIndex);
			                grid.getModule().toCreate(rec);
			            }
			        }]
				}]
            },{
	            xtype: 'splitter',
	            minHeight:500
	        },{
                flex:3,
                frame:true,
                margin: '10 10 10 0',
                xclass:'DicGrid',
                forceFit:true,
                itemId:'dics',
                emptyText:'没有数据',
                rightId:'查询字典项',
                columnLines:true,
                loadMask:true,
                title:'字典值',
				columns:[{
			    	text:null,
			        xtype:'actioncolumn',
			        minWidth:100,
			        items: [{
		                icon: Rx.Icons.png_16_3.path,
		                tooltip: '上移',
		                rightHidden:true,
		                rightId:'移动字典顺序',
		                getClass:function(v,meta,rec,row,col,store){
                        	return rec.get('staticed')?'r-hidden':'r-column-visibility-over r-column-visibility-selected';
                        },
		                handler: function(grid, rowIndex, colIndex) {
		                	if(rowIndex == 0)return;
		                    var rec = grid.getStore().getAt(rowIndex);
		                    grid.getModule().toUpdatestair(rec,true);
		                }
		            },{
		                icon: Rx.Icons.png_16_5.path,
		                tooltip: '下移',
		                rightHidden:true,
		                rightId:'移动字典顺序',
		                getClass:function(v,meta,rec,row,col,store){
                        	return rec.get('staticed')?'r-hidden':'r-column-visibility-over r-column-visibility-selected';
                        },
		                handler: function(grid, rowIndex, colIndex) {
		                	//if(rowIndex == )return;
		                    var rec = grid.getStore().getAt(rowIndex);
		                    grid.getModule().toUpdatestair(rec,false);
		                }
		            },{
			            icon: Rx.Icons.png_16_27.path,
			            iconCls:'r-column-visibility-over r-column-visibility-selected',
			            rightId:'修改字典项',
			            tooltip: '修改字典项',
			            getClass:function(v,meta,rec,row,col,store){
                        	return rec.get('staticed')?'r-hidden':'r-column-visibility-over r-column-visibility-selected';
                        },
			            handler: function(grid, rowIndex, colIndex) {
			                var rec = grid.getStore().getAt(rowIndex);
			                grid.getModule().toUpdate(rec);
			            }
			        },{
                        icon: Rx.Icons.png_16_16.path,
                        iconCls:'r-column-visibility-over r-column-visibility-selected',
                        rightId:'删除字典项',
                        getClass:function(v,meta,rec,row,col,store){
                        	return rec.get('staticed')?'r-hidden':'r-column-visibility-over r-column-visibility-selected';
                        },
                        tooltip: '删除字典项',
                        handler: function(grid, rowIndex, colIndex) {
                            var rec = grid.getStore().getAt(rowIndex);
                            this.record = rec;
                            Ext.Msg.confirm('消息','确定删除名称为'+rec.get('name')+'的项吗？',function(bId){
                                if(bId=='ok' || bId =='yes'){
                                    this.getModule().toDel(this.record);
                                }
                            },this);
                        }
                    }]
				}]
            }]
        });
        me.callParent(arguments);
    }
});
