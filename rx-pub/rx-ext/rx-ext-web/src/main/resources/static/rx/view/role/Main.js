/*
 *角色管理
 */
Ext.define('Rx.view.role.Main', {
    requires:[
       'com.rx.pub.role.vo.PubRoleResourceGrid',
       'com.rx.pub.role.vo.PubRoleGrid',
       'com.rx.pub.role.vo.PubRoleAddVo',
       'com.rx.pub.role.vo.PubRoleUpdateVo',
       'com.rx.pub.role.controller.PubRoleController',
       'com.rx.pub.role.controller.PubRoleResourceController'
    ],
    extend: 'Ext.panel.Panel',
    uses:[],
    toCreate:function(rec){
    	Ext.create("Rx.widget.Window",{
			title:'新建角色',
			layout:'fit',
			width:350,
			height:250,
			items:[{
				autoCreateBtns:true,
				xclass:'PubRoleAddVo'
			}]
		}).showFor(function(fm,res,data){
			if(res.response.status == 200){
				this.lookupI('rolegrid').getStore().reload();
			}else{
				return false;
			}
		},this);
    },
    toUpdate:function(rec){
    	Ext.create("Rx.widget.Window",{
			title:'修改角色',
			layout:'fit',
			width:350,
			height:250,
			items:[{
				initRecord:rec,
				autoCreateBtns:true,
				xclass:'PubRoleUpdateVo'
			}]
		}).showFor(function(fm,res,data){
			if(res.response.status == 200){
				this.lookupI('rolegrid').getStore().reload();
			}else{
				return false;
			}
		},this);
    },
    toDel:function(rec){
    	this.setLoading('删除中');
    	PubRoleController.delPubRole({
    		params:{
    			id:rec.getId()
    		},
    		callback:function(o,f,r){
    			this.setLoading(false);
    			if(f){
    				this.lookupI('rolegrid').getStore().reload();
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
                xclass:'PubRoleGrid',
                forceFit:true,
                itemId:'rolegrid',
                rightId:'角色列表',
                title:'角色',
                tools:[{
				    type:'refresh',
				    tooltip: '刷新角色',
				    rightId:'角色列表',
				    handler: function(event, toolEl, panelHeader) {
				        var gd = this.up('gridpanel');
				        gd.getSelectionModel().deselectAll();
				        gd.getStore().reload();
				    }
				}],
                listeners:{
					'select':function(me0, record, index, eOpts){
						var grid = this.getModule().lookupI('roleresourcegrid'),sto = grid.getStore();
						sto.getProxy().extraParams = {
					    	roleId:record.getId(),
					    	roleName:record.get('roleName')
					    };
					    sto.loadPage(1,{
					    	scope: grid,
						    callback:grid.dataBack
					    });
					}
				},
				dockedItems:[{
			    	xtype:'toolbar',
			    	docked:'top',
			    	items:[{
				    	text:'新建角色',
					    rightId:'新增角色',
				    	handler:function(btn){
				    		this.getModule().toCreate();
				    	}
				    }]
				}],
				columns:[{
			    	text:null,
			        xtype:'actioncolumn',
			        width:20,
			        items: [{
			            icon: Rx.Icons.png_16_27.path,
			            iconCls:'r-column-visibility-over r-column-visibility-selected',
			            altText:'修改',
			            rightHidden:true,
			            rightId:'修改角色',
			            tooltip: '修改',
			            handler: function(grid, rowIndex, colIndex) {
			                var rec = grid.getStore().getAt(rowIndex);
			                grid.getModule().toUpdate(rec);
			            }
			        },{
                        icon: Rx.Icons.png_16_16.path,
                        iconCls:'r-column-visibility-over r-column-visibility-selected',
                        //iconCls:'actioncolumn-margin',
                        //text:'删除',
                        rightId:'删除角色',
                        tooltip: '删除',
                        handler: function(grid, rowIndex, colIndex) {
                            var rec = grid.getStore().getAt(rowIndex);
                            this.record = rec;
                            Ext.Msg.confirm('消息','确定删除名称为'+rec.get('roleName')+'的角色吗？',function(bId){
                                if(bId=='ok' || bId =='yes'){
                                    this.getModule().toDel(this.record);
                                }
                            },this);
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
                xclass:'PubRoleResourceGrid',
                forceFit:true,
                itemId:'roleresourcegrid',
                emptyText:'没有数据',
                columnLines:true,
                rightId:'角色的资源列表',
                loadMask:true,
                selType: 'checkboxmodel',
                title:'权限',
                features: [{
			        ftype: 'grouping',
			        groupHeaderTpl: '{groupValue} ({rows.length})',
			        hideGroupedHeader: true,
			        startCollapsed: true
			        //,id: 'featuresGroupName'
                }],
                dataBack:function(rds,o,f) {
					this.params = o.request.initialConfig.params;
					this.setTitle(this.params.roleName +' > '+'权限');
				},
                dockedItems:[{
			    	xtype:'toolbar',
			    	docked:'top',
			    	items:[{
				    	text:'添加权限到角色',
				    	rightId:'新增角色的资源',
				    	handler:function(){
				    		var g = this.up('gridpanel');
				    		if(g.params){
                        		var win = Ext.create('Rx.view.role.ResourceSelectorWindow',{singleCheck:false});
                        		win.showFor(this.valueBack,this);
				    		}else{
				    			Rx.Msg.alert('提示','先在左边选取一个角色.');
				    		}
                        },
                        valueBack:function(roles,b){
                        	if(roles && roles.length > 0)
                        	{
                        		var o,ids = [];
                        		for(o in roles){
                        			ids.push(roles[o].get('code'));
                        		}
                        		var grid = this.up('gridpanel');
                        		grid.setLoading('加入中...');
                        		PubRoleResourceController.roleAddResource({
        			    			params:{roleId:grid.params.roleId,resIds:ids,delOrAdd:(b?1:0)},
        							callback:function(o,f,r){
        								this.setLoading(false);
        								if(f){
        									this.getStore().reload();
        								}
        							},
        							scope:grid
        						});
                        		
                        	}
                        }
				    },{
				    	text:'移出角色权限',
				    	rightId:'删除角色的资源',
				    	handler:function(){
                        	var grid = this.up('gridpanel');
                        	var rs = grid.getSelectionModel().getSelection();
                        	if(rs.length > 0){
                        		var ids = [];
                        		for(var o in rs){
                        			ids.push(rs[o].getId());
                        		}
                        		grid.refIds = ids;
                                Ext.Msg.confirm('消息','确定从角色中移出所选权限?',function(bId){
            			    		if(bId=='ok' || bId =='yes'){
            			    			this.setLoading('移除中...');
            			    			PubRoleResourceController.roleDelResource({
            				    			params:{roleId:this.params.roleId,refIds:this.refIds},
            								callback:function(o,f,r){
            									this.setLoading(false);
                								if(f){
                									this.getStore().reload();
                								}
            								},
            								scope:this
            							});
            			    		}
            			    	},grid);
                        	}else{
                        		Rx.Msg.alert('提示','未选取任何权限.');
                        	}
                        }
				    }]
				}]
            }]
        });
        me.callParent(arguments);
    }
});
