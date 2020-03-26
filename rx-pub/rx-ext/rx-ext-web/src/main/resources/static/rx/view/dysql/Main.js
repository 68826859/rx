/*
 *动态sql
 */
Ext.define('Rx.view.dysql.Main', {
    requires:[
       'com.rx.pub.dysql.dto.DySqlGrid',
       'Rx.view.dysql.DySqlForm',
       'Rx.widget.ParamForm',
       'Rx.widget.PagingGrid',
       'Rx.view.dysql.StatisticsForm',
       'Rx.Chart'
        // 'Rx.widget.EnumColumn',
        // 'Rx.widget.FkColumn'
    ],
    extend: 'Ext.panel.Panel',
    uses:[],
    toCreate:function(rec){
    	Ext.create("Rx.widget.Window",{
			title:'新建报表',
			layout:'fit',
			items:[{
				xtype:'dysqlform'
			}]
		}).showFor(function(f){
			if(f){
				this.lookupI('dysqlgrid').getStore().reload();
			}else{
				return false;
			}
		},this);
    },
    toCreateChart:function(){
    	Ext.create("Rx.widget.Window",{
			title:'新建统计',
			layout:'fit',
			items:[{
				xtype:'statisticsform'
			}]
		}).showFor(function(f){
			if(f){
				this.lookupI('dysqlgrid').getStore().reload();
			}else{
				return false;
			}
		},this);
    },
    toUpdate:function(rec){
    	var dst = rec.get('dySqlType');
    	Ext.create("Rx.widget.Window",{
			title:dst?'修改统计':'修改报表',
			layout:'fit',
			items:[{
				recordId:rec.getId(),
				xtype:dst?'statisticsform':'dysqlform'
			}]
		}).showFor(function(f){
			if(f){
				this.lookupI('dysqlgrid').getStore().reload();
			}else{
				return false;
			}
		},this);
    },
    toDel:function(rec){
    	this.setLoading('删除中');
    	DySqlController.delDysql({
    		params:{
    			dySqlId:rec.getId()
    		},
    		callback:function(o,f,r){
    			this.setLoading(false);
    			if(f){
    				this.lookupI('dysqlgrid').getStore().reload();
    			}
    		},
    		scope:this
    	});
    },
    createReport:function(rec){
    	this.setLoading('正在获取报表数据...');

    	DySqlController.getSearchSlots({
    		params:{
    			dySqlId:rec.getId()
    		},
    		callback:function(o,f,r){
    			var dySqlId = o.params.dySqlId;
    			this.setLoading(false);
    			if(f){
    				var res = r.responseJson.data;
    				var cd = res.columnDefine;
    				var fds = [];
    				for(var o in cd){
    					fds.push(cd[o].dataIndex);
    				}
    				var mid = 'CombineDySql-'+dySqlId;
                    Ext.define(mid,{
                        extend: 'Ext.data.Model',
                        fields: fds
                    });
                    var store = Ext.create('Ext.data.Store', {
                        model: mid,
                        pageSize: 25,
                        autoLoad:false,
                        proxy: {
                            type: 'ajax',
                            url: DySqlController.combineSearch.url+"?dySqlId="+dySqlId,
                            reader: {
                                type: 'json',
                                rootProperty: 'data.pageData',
                                totalProperty: 'data.totalRows'
                            }
                        }
                    });
                   var gd = {
						xtype:'paginggrid',
						columns:cd,
		    	    	store: store
                   };
                    var pfs = res.searchSlots;
                    var itms = [];
                    if(pfs && pfs.length > 0){
                    	for(var i = 0;i<pfs.length;i++){
                    		var pf = pfs[i];
		    				if(pf.fieldTypeName == 'Refer'){
		    					itms.push({
		    						xtype:'fkcombox',
		    						className:pf.referModel,
		    						name:pf.name,
		    						fieldLabel:pf.text,
		    						pageSize:25,
					    			emptyText:'选择引用表'
		    					});
		    				}else if(pf.fieldTypeName == 'Enum'){
		    					itms.push({
		    						editable:false,
		    						name:pf.name,
		    						fieldLabel:pf.text,
					    			xtype:'enumcombo',
					    			className:pf.referModel
		    					});
		    				}else if(pf.fieldTypeName == 'Dict'){
		    					itms.push({
		    						name:pf.name,
		    						fieldLabel:pf.text,
		    						emptyText:'选择字典值',
					    			xtype:'diccombox',
					    			parentId:pf.parentId
		    					});
		    				}else if(pf.fieldTypeName == 'Date'){
		    					var fm = rec.get('jsFormat') || 'Y-m-d';
		    					itms.push({
                                    xtype: 'combobox',
                                    fieldLabel: pf.text,
                                    name:pf.name,
                                    allowBlank: true,
                                    emptyText:'请选择...',
                                    editable: false,
                                    store: {
                                        fields: ["index", "text"],
                                        autoLoad:true,
                                        proxy:{
                                            type:'memory'
                                        },
                                        data: pf.options
                                    },
                                    valueField:'index',
                                    displayField:'text'
                                });
		    				}else if(pf.fieldTypeName == 'Integer'){
		    					itms.push({
		    						name:pf.name,
		    						fieldLabel:pf.text,
					    			xtype:'numberfield'
		    					});
		    				}else if(pf.fieldTypeName == 'String'){
		    					itms.push({
		    						name:pf.name,
		    						fieldLabel:pf.text,
					    			xtype:'textfield'
		    					});
		    				}
                    	}
                    }
                    if(itms.length > 0){
                        gd.dockedItems = [{
                            autoCreateBtns:true,
                            autoSearch:true,
                            xtype:'paramform',
                            dySqlId:dySqlId,
                            searchBack:function(records,o,f){
                            	var bar = this.up('paginggrid').lookupI('pagingtoolbar',true);
                            	if(f && records.length > 0){
						    		var data = o.getResponse().responseJson.data;
						    		var pa = o.getRequest().getParams();
						    		pa = pa || {};
						    		pa.dySqlId = this.dySqlId;
						    		var qs = Ext.Object.toQueryString(pa);
						    		var path = DySqlController.exportCombSearch.url+"?"+qs;
						    		var str = '<a target="_blank" href="'+path+'"><img style="margin:0 0 -4px 5px;" alt="导出报表" src="'+Rx.Icons16.page_white_excel.path+'"/></a>';
						    		bar.displayMsg = str + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;显示  {0} - {1} 共 {2}";
						    		bar.updateInfo();
						    	}else{
						    		bar.displayMsg = "显示  {0} - {1} 共 {2}";
						    	}
                            },
                            items:itms
                        }];
                    }else{
                        gd.store.setAutoLoad(true);
                    }
    				Ext.create("Rx.widget.Window",{
						title:'报表   ' + rec.get("dySqlName"),
						layout:'fit',
						items:[gd]
					}).show();
    			}
    		},
    		scope:this
    	});
    },
    createStatistics:function(rec){
    	this.setLoading('正在获取统计数据...');
    	DySqlController.getSearchSlots({
    		params:{
    			dySqlId:rec.getId()
    		},
    		callback:function(o,f,r){
    			var dySqlId = o.params.dySqlId;
    			this.setLoading(false);
    			if(f){
    				var res = r.responseJson.data;
    				var cd = res.columnDefine;
    				var fds = [];
    				for(var o in cd){
    					cd[o].minWidth = 0;
    					fds.push(cd[o].dataIndex);
    				}
    				var mid = 'CombineDySql-'+dySqlId;
                    Ext.define(mid,{
                        extend: 'Ext.data.Model',
                        fields: fds
                    });
                    var store = Ext.create('Ext.data.Store', {
                        model: mid,
                        pageSize: 25,
                        autoLoad:false,
                        proxy: {
                            type: 'ajax',
                            url: DySqlController.combineChart.url+"?dySqlId="+dySqlId,
                            reader: {
                                type: 'json',
                                rootProperty: 'data'
                                //totalProperty: 'data.totalRows'
                            }
                        }
                    });
                   var gd = {
						xtype:'gridpanel',
						flex:1,
						columns:cd,
						frame:true,
						collapsible:true,
						collapseMode:'mini',
						header:false,
						region:'west',
						split:true,
						forceFit:true,
						itemId:'grid',
		    	    	store: store
                   };
                    var pfs = res.searchSlots;
                    var itms = [];
                    if(pfs && pfs.length > 0){
                    	for(var i = 0;i<pfs.length;i++){
                    		var pf = pfs[i];
		    				if(pf.fieldTypeName == 'Refer'){
		    					itms.push({
		    						xtype:'fkcombox',
		    						className:pf.referModel,
		    						name:pf.name,
		    						fieldLabel:pf.text,
		    						pageSize:25,
					    			emptyText:'选择引用表'
		    					});
		    				}else if(pf.fieldTypeName == 'Enum'){
		    					itms.push({
		    						editable:false,
		    						name:pf.name,
		    						fieldLabel:pf.text,
					    			xtype:'enumcombo',
					    			className:pf.referModel
		    					});
		    				}else if(pf.fieldTypeName == 'Dict'){
		    					itms.push({
		    						name:pf.name,
		    						fieldLabel:pf.text,
		    						emptyText:'选择字典值',
					    			xtype:'diccombox',
					    			parentId:pf.parentId
		    					});
		    				}else if(pf.fieldTypeName == 'Date'){
		    					var fm = rec.get('jsFormat') || 'Y-m-d';
		    					itms.push({
                                    xtype: 'combobox',
                                    fieldLabel: pf.text,
                                    name:pf.name,
                                    allowBlank: true,
                                    emptyText:'请选择...',
                                    editable: false,
                                    store: {
                                        fields: ["index", "text"],
                                        autoLoad:true,
                                        proxy:{
                                            type:'memory'
                                        },
                                        data: pf.options
                                    },
                                    valueField:'index',
                                    displayField:'text'
                                });
		    				}else if(pf.fieldTypeName == 'Integer'){
		    					itms.push({
		    						name:pf.name,
		    						fieldLabel:pf.text,
					    			xtype:'numberfield'
		    					});
		    				}else if(pf.fieldTypeName == 'String'){
		    					itms.push({
		    						name:pf.name,
		    						fieldLabel:pf.text,
					    			xtype:'textfield'
		    					});
		    				}
                    	}
                    }
                    if(itms.length > 0){
                        gd.dockedItems = [{
                            autoCreateBtns:true,
                            autoSearch:true,
                            xtype:'paramform',
                            dySqlId:dySqlId,
                            /*searchBack:function(records,o,f){
                            	var bar = this.up('paginggrid').lookupI('pagingtoolbar',true);
                            	if(f && records.length > 0){
						    		var data = o.getResponse().responseJson.data;
						    		var pa = o.getRequest().getParams();
						    		pa = pa || {};
						    		pa.dySqlId = this.dySqlId;
						    		var qs = Ext.Object.toQueryString(pa);
						    		var path = DySqlController.exportCombSearch.url+"?"+qs;
						    		var str = '<a target="_blank" href="'+path+'"><img style="margin:0 0 -4px 5px;" alt="导出报表" src="'+Rx.Icons16.page_white_excel.path+'"/></a>';
						    		bar.displayMsg = str + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;显示  {0} - {1} 共 {2}";
						    		bar.updateInfo();
						    	}else{
						    		bar.displayMsg = "显示  {0} - {1} 共 {2}";
						    	}
                            },*/
                            items:itms
                        }];
                    }else{
                        gd.store.setAutoLoad(true);
                    }
    				var win = Ext.create("Rx.widget.Window",{
						title:'统计   ' + rec.get("dySqlName"),
						layout:{
							type:'border',
							align:'stretch'
						},
						padding:'10 10 10 10',
						items:[gd]
					});
					win.response = res;
					win.record = rec;
					win.show();
					
					Rx.Chart.onReady(function(){
						var gd = this.getItem('grid'),sto = gd.getStore();
						var rec = this.record;
						var serie,series = [];
						var res = this.response;
						var cd = res.columnDefine,xf=cd[0].dataIndex,yfo,ct;
	    				for(var o in cd){
	    					yfo = cd[o];
	    					ct = Ext.decode(yfo.submitConfig).chartType;
	    					if(yfo.dataIndex == xf){continue;}
	    					serie = null;
	    					for(var i in series){
	    						if(series[i].type == ct){
	    							serie = series[i];
	    							break;
	    						}
	    					}
	    					if(serie){
	    						serie.yField.push(yfo.dataIndex);
	    						serie.label.field.push(yfo.dataIndex);
	    						serie.title.push(yfo.text);
	    					}else{
	    						series.push({
						            type: ct,
						            stacked:false,
						            xField: xf,
						            yField: [yfo.dataIndex],
						            title:[yfo.text],
						            style: {
						                minGapWidth:20
						            },
						            highlight: {
						                strokeStyle: 'black',
						                fillStyle: 'blue',
						                color:'#FFF'
						            },
						            label: {
						                field: [yfo.dataIndex],
						                color:'#333',
						                display: 'insideEnd'
						            }
		    					});
	    					}
	    				}
	    				
	    				var tit = rec.get('dySqlName');
						this.add({
							flex:2,
							region:'center',
							frame : true,
							layout : 'fit',
							itemId:'chartCt',
							items : {
								xtype : 'cartesian',
								//reference : 'chart',
								//id:'chart2',
								//itemId:'chart',
								width : '100%',
								//height : 800,
								title:tit,
								legend: {
						            type: 'sprite',
						            docked: 'bottom',
						            marker: {
						                type: 'square'
						            },
						            border: {
						                radius: 0
						            }
						        },
								interactions : {
									type : 'panzoom',
									zoomOnPanGesture : true
								},
								animation : {
									//duration : 200
								},
								plugins: {
							        ptype: 'chartitemevents',
							        moveEvents: true
							    },
								store : sto,
								insetPadding : 40,
								innerPadding : {
									left : 40,
									right : 40
								},
								sprites :[],
								axes : [{
											type : 'numeric',
											position : 'left',
											grid:true,
											minimum : 0,
											increment:5,
											labelInSpan:true,
											maxZoom:1
											//renderer : 'onAxisLabelRender'
										}, {
											type : 'category',
											position : 'bottom',
											grid : true,
											label : {
												rotate : {
													degrees : -45
												}
											}
										}],
								series : series,
								listeners : {}
							}
						});
					},win);
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
                flex:1,
                frame:true,
                margin: '10 10 10 10',
                xclass:'DySqlGrid',
                forceFit:true,
                /*
                features: [{
			        ftype: 'grouping',
			        groupHeaderTpl: '{groupValue} ({rows.length})',
			        hideGroupedHeader: true,
			        //collapsible:true,
			        startCollapsed:false
			        //,id: 'featuresGroupName'
			    }],*/
                itemId:'dysqlgrid',
                //title:'报表',
				dockedItems:[{
			    	xtype:'toolbar',
			    	docked:'top',
			    	items:[{
				    	text:'新建报表',
					    rightId:'新建报表',
					    icon:Rx.Icons.png_16_9.path,
					    rightHidden:true,
					    //cls:'r-btn-highlight r-btn-transparent',
				    	handler:function(btn){
				    		this.getModule().toCreate();
				    	}
				    },{
				    	text:'新建统计',
					    rightId:'新建统计',
					    icon:Rx.Icons.png_16_9.path,
					    rightHidden:true,
					    //cls:'r-btn-highlight r-btn-transparent',
				    	handler:function(btn){
				    		this.getModule().toCreateChart();
				    	}
				    }]
				}],
				columns:[/*{ text: '导出报表',width:30,dataIndex:'colSearchJson',
							renderer:function(value,md,rec){
								var path = DySqlController.exportCombSearch.url + "?dySqlId=" + rec.get("dySqlId");
								return '<a target="_blank" href="'+path+'"><img style="margin:0 0 -4px 5px;" alt="导出报表" src="'+Rx.Icons16.page_white_excel.path+'"/></a>';
					    	}
					    },*/{
			    	text:"操作",
			        xtype:'actioncolumn',
			        width:20,
			        items: [{
			            icon: Rx.Icons.png_16_6.path,
			            //iconCls:'r-column-visibility-over r-column-visibility-selected',
			            iconCls:'actioncolumn-margin',
			            altText:'查看',
			            tooltip: '查看',
			            handler: function(grid, rowIndex, colIndex) {
			                var rec = grid.getStore().getAt(rowIndex);
			                
			                var dst = rec.get('dySqlType');
			                if(dst){
			                	grid.getModule().createStatistics(rec);
			                }else{
			                	grid.getModule().createReport(rec);
			                }
			            }
			        },{
			            icon: Rx.Icons.png_16_27.path,
			            //iconCls:'r-column-visibility-over r-column-visibility-selected',
			            iconCls:'actioncolumn-margin',
			            altText:'修改',
			            tooltip: '修改',
			            rightId:'修改统计报表',
					    rightHidden:true,
			            handler: function(grid, rowIndex, colIndex) {
			                var rec = grid.getStore().getAt(rowIndex);
			                grid.getModule().toUpdate(rec);
			            }
			        },{
                        icon: Rx.Icons.png_16_16.path,
                        //iconCls:'r-column-visibility-over r-column-visibility-selected',
                        iconCls:'actioncolumn-margin',
                        text:'删除',
                        rightId:'删除统计报表',
					    rightHidden:true,
                        tooltip: '删除',
                        handler: function(grid, rowIndex, colIndex) {
                            var rec = grid.getStore().getAt(rowIndex);
                            this.record = rec;
                            Ext.Msg.confirm('消息','确定删除名称为'+rec.get('dySqlName')+'的报表吗？',function(bId){
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
},function(){
	Ext.util.Format.date = function(value, format) {
        if (!value) {
            return "";
        }
        if (typeof value === 'number') {
            value = new Date(value);
        }
        if (!Ext.isDate(value)) {
            value = new Date(Date.parse(value));
        }
        return Ext.Date.dateFormat(value, format || Ext.Date.defaultFormat);
    };
});
