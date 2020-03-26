/*
 * 
 * */
Ext.define('Rx.view.dysql.ConditionForm',{
	requires:[
		'Rx.component.EnumComboBox',
		'Rx.widget.DicComboBox',
		'Rx.widget.FkComBox',
		'com.rx.pub.dysql.vo.DySqlFieldVo',
		'com.rx.pub.dysql.controller.DySqlController'
	],
    extend: 'Ext.panel.Panel',
    uses:['Rx.view.dysql.FieldSelectorWindow','Rx.view.dysql.TableSelectorWindow'],
    alias:'widget.conditionform',
    logicType:'and',
    
    statics:{
    	dySqlFieldStore:Ext.create('Ext.data.Store',{
    		autoLoad:true,
    		model:'com.rx.pub.dysql.vo.DySqlFieldVo',
    		proxy:{
    			type:'ajax',
    			url:DySqlController.listDySqlField.url,
    			reader: {
		            type: 'json',
		            rootProperty: 'data'
		        }
    		}
    	})
    },
    
    setValues:function(vs){
    	var tf = this.lookupI('toolbar',true).lookupI('textfield');
    	tf.setValues(vs.condition);
    	this.logicType = vs.logicType || 'and';
    	var itms = vs.items;
    	if(itms && itms.length > 0){
    		for(var o in itms){
    			this.addValue(itms[o]);
    		}
    	}
    },
    getValues:function(){
    	var tf = this.lookupI('toolbar',true).lookupI('textfield');
    	var vs = tf.getValues();
    	
    	var its = this.items.items;
    	var itms = [];
    	for(var i=0;i<its.length;i++){
    		itms.push(its[i].getValues());
    	}
    	
    	return {
    		condition:vs,
    		logicType:this.logicType,
    		items:itms
    	};
    },
    
    addValue:function(va){
    	this.add({
			xtype:'textfield',
			editable:false,
			setValues:function(va){
				this.values = va;
				this.setValue(va.condition.text?va.condition.text:va);
			},
			values:va,
			value:(va.condition.text?va.condition.text:va),
			addValue:function(va){
				this.setValues(va);
			},
			getValues:function(){
				return this.values;
			},
			triggers: {
		        foo: {
		            cls: 'x-fa fa-th-list',
		            handler: function(tf,trigger,e) {
		                this.up('conditionform').addAndOr(this.getValues(),this);
		            }
		        },
		        bar: {
		            cls: 'x-fa fa-trash',
		            handler: function(tf,trigger,e){
		                tf.ownerCt.remove(tf);
		            }
		        }
		    }
    	});
    },
    addAndOr:function(va,target){
		var win = Ext.create('Rx.widget.Window',{
			title:'+'+va.logicType,
			layout:'fit',
			items:[{
				itemId:'conditionform',
				xtype:'conditionform'
			}],
			buttons:['->',{
    	    	text:'确定',
    	    	handler:function(btn){
    	    		var win = btn.up('window');
    	    		var fm = win.getComponent('conditionform');
    	    		var vs = fm.getValues();
    	    		if(vs){
    	    			win.returnValue(vs);
    	    		}
    	    	}
    	    },{
    	    	text:'取消',
    	    	handler:function(btn){
    	    		btn.up('window').close();
    	    	}
    	    }]
		});
		target = target || this;
		win.showFor(target.addValue,target);
		if(va){
			win.getComponent('conditionform').setValues(va);
		}
	},
    initComponent:function(){
    	var me = this;
    	Ext.apply(me,{
    		layout:'auto',
    		bodyStyle:'padding-top:5px;padding-left:5px;',
    		dockedItems:{
    			xtype:'toolbar',
    			itemId:'toolbar',
    			dock:'top',
    			items:[{
    					setValues:function(va){
    						this.values = va;
    						this.setValue(va?(va.text?va.text:va):null);
		    			},
		    			getValues:function(){
		    				return this.values;
		    			},
		    			values:null,
		    			xtype:'textfield',
		    			itemId:'textfield',
		    			emptyText:'+条件',
		    			labelWidth:45,
		    			editable:false,
		    			dySqlFieldBack:function(rec){
		    				var itms = [{
		    					xtype:'textfield',
		    					readOnly:true,
		    					editable:false,
		    					fieldLabel:'条件列',
		    					name:'text',
		    					value:rec.get('text')
		    				},{
		    					xtype:'hidden',
		    					name:'name',
		    					value:rec.get('name')
		    				},{
	    						editable:false,
	    						fieldLabel:'运算符',
	    						allowBlank:false,
				    			xtype:'enumcombo',
				    			name:'whereEnum',
				    			className:rec.get('whereEnum')
		    				}];
		    				var fieldTypeName = rec.get('fieldTypeName');
		    				if(fieldTypeName == 'Refer'){
		    					/*
		    					itms.push({
		    						xtype:'combobox',
		    						fieldLabel:'值',
		    						allowBlank:false,
					    			emptyText:'选择引用表',
					    			name:'value',
					    			displayField:'display',
					    			valueField:'key',
					    			store:{
										autoLoad:true,
								    	fields:["key","display","parentId"],
								    	proxy:{
								    		type:'ajax',
								    		url:ExtController.listModel.url,
								    		extraParams:{
								    			className:rec.get('referModel')
								    		},
								    		reader: {
									            type: 'json',
									            rootProperty: 'data'
									        }
								    	}
								    }
		    					});
		    					*/
		    					
		    					itms.push({
		    						xtype:'fkcombox',
		    						className:rec.get('referModel'),
		    						fieldLabel:'值',
		    						allowBlank:false,
		    						pageSize:25,
					    			emptyText:'选择引用表',
					    			name:'value'
		    					});
		    					
		    				}else if(fieldTypeName == 'Enum'){
		    					itms.push({
		    						editable:false,
		    						fieldLabel:'值',
		    						emptyText:'选择枚举值',
					    			xtype:'enumcombo',
					    			allowBlank:false,
					    			name:'value',
					    			className:rec.get('referModel')
		    					});
		    				}else if(fieldTypeName == 'Dict'){
		    					itms.push({
		    						fieldLabel:'值',
		    						allowBlank:false,
		    						emptyText:'选择字典值',
					    			xtype:'diccombox',
					    			name:'value',
					    			parentId:rec.get('parentId')
		    					});
		    				}else if(fieldTypeName == 'Date'){
		    					var fm = rec.get('jsFormat') || 'Y-m-d';
		    					itms.push({
					    			xtype:'datefield',
					    			allowBlank:false,
					    			format:fm,
					    			fieldLabel:'开始时间',
					    			name:'fromTime'
		    					});
		    					itms.push({
					    			xtype:'datefield',
					    			fieldLabel:'结束时间',
					    			format:fm,
					    			allowBlank:false,
					    			name:'toTime'
		    					});
		    					itms.push({
		    						editable:false,
		    						fieldLabel:'分段方式',
		    						emptyText:'分段方式',
					    			xtype:'enumcombo',
					    			allowBlank:true,
					    			name:'dateBlockType',
					    			className:'com.rx.pub.dysql.enm.DateBlockTypeEumn'
		    					});
		    				}else if(fieldTypeName == 'Integer'){
		    					itms.push({
		    						name:'value',
		    						fieldLabel:'值',
		    						allowBlank:false,
					    			xtype:'numberfield'
		    					});
		    				}else if(fieldTypeName == 'String'){
		    					itms.push({
		    						name:'value',
		    						fieldLabel:'值',
		    						allowBlank:false,
					    			xtype:'textfield'
		    					});
		    				}
		    				if(fieldTypeName != 'Date'){
			    				itms.push({
		    						fieldLabel:'是否用户可选',
		    						//allowBlank:false,
		    						//emptyText:'选择字典值',
					    			xtype:'checkboxfield',
					    			inputValue:true,
					    			value:false,
					    			name:'isSlot'
		    					});
		    				}
		    				
		    				var win = Ext.create('Rx.widget.Window',{
		                		layout:'fit',
		                		//width:800,
		                		height:200,
		                		title:'输入条件',
		                		items:[{
		                			itemId:'form',
		                			xtype:'form',
					    	    	frame:true,
					    	    	layout:{
					    	    		type:'hbox',
					    	    		align:'middle'
					    	    	},
					    	    	bodyStyle:'padding:5px',
					    	    	defaults:{
					    	    		flex:1,
					    	    		labelAlign:'top',
					    	    		labelWidth:50
					    	    	},
					    	    	items:itms
		                		}],
		                		buttons:['->',{
					    	    	text:'确定',
					    	    	handler:function(btn){
					    	    		var win = btn.up('window');
					    	    		var fm = win.getComponent('form').getForm();
										if(!fm.isValid())
										{
											return;
										}
										var vs = fm.getValues();
					    	    		win.returnValue(vs);
					    	    	}
					    	    },{
					    	    	text:'取消',
					    	    	handler:function(btn){
					    	    		btn.up('window').close();
					    	    	}
					    	    }]
		                	});
		                	win.showFor(this.setValues,this);
		                	if(this.values){
		                		win.lookupI('form').getForm().setValues(this.values);
		                	}
		    			},
		    			triggers:{
					        foo: {
		            			cls: 'x-fa fa-th-list',
		            			text:'+条件',
					            handler: function(){
					            	if(this.values){
					            		var rec = Rx.view.dysql.ConditionForm.dySqlFieldStore.getById(this.values.name);
					            		this.dySqlFieldBack(rec);
					            		return;
					            	}
				                	var win = Ext.create('Rx.widget.Window',{
				                		layout:'fit',
				                		title:'选择字段',
				                		items:[{
				                			itemId:'list',
							    	    	frame:true,
							    	    	flex:1,
									    	xclass:'DySqlFieldGrid',
							    	    	selType: 'checkboxmodel',
							    	    	selModel:{
										   		mode:'SINGLE'
										   },
										   features: [{
										        ftype: 'grouping',
										        groupHeaderTpl: '{groupValue} ({rows.length})',
										        hideGroupedHeader: true,
										        startCollapsed:true
										    }],
										   columnLines: true
				                		}],
				                		buttons:['->',{
							    	    	text:'确定',
							    	    	handler:function(btn){
							    	    		var win = btn.up('window');
							    	    		var list = win.getComponent('list');
							    	    		var records = list.getSelectionModel().getSelection();
							    	    		if(records.length == 0){
							    	    			Rx.Msg.alert('提示','未选择任何项.');
							    	    		}else{
							    	    			win.returnValue(records[0]);
							    	    		}
							    	    	}
							    	    },{
							    	    	text:'取消',
							    	    	handler:function(btn){
							    	    		btn.up('window').close();
							    	    	}
							    	    }]
				                	});
				                	win.showFor(this.dySqlFieldBack,this);
					            }
					        },
					        bar: {
			            		cls: 'x-fa fa-trash',
			            		handler: function() {
			            			this.setValues(null);
			            		}
					        }
					    }
    				},
    				'-',
    				{
    					text:'+and',
    					handler:function(){
    						this.up('conditionform').addAndOr({logicType:"and"});
    					}
    				},
    				{
    					text:'+or',
    					handler:function(){
    						this.up('conditionform').addAndOr({logicType:"or"});
    					}
    				}
    			]
    		},
    		items:[]
    	});
    	me.callParent(arguments);
    }
});