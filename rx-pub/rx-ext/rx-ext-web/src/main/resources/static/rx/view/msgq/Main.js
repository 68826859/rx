/*
 *消息队列
 */
Ext.define('Rx.view.msgq.Main', {
    requires:[
       'com.rx.pub.msgq.dto.MsgqGrid',
       'com.rx.pub.msgq.dto.MsgqSearchDto',
       'com.rx.pub.msgq.controller.MsgqController',
       'com.rx.pub.msgq.vo.MsgqUpdateVo',
       'com.rx.pub.msgq.vo.MsgqAddVo'
    ],
    extend: 'Ext.panel.Panel',
    uses:[],
    toCreate:function(rec){
    	Ext.create("Rx.widget.Window",{
			title:'新建队列消息',
			layout:'fit',
			width:0.5,
			height:400,
			items:[{
				autoCreateBtns:true,
				xclass:'MsgqAddVo'
			}]
		}).showFor(function(f){
			if(f){
				this.lookupI('msgqgrid').getStore().reload();
			}else{
				return false;
			}
		},this);
    },
    toUpdate:function(rec){
    	Ext.create("Rx.widget.Window",{
			title:'修改队列消息',
			width:0.5,
			layout:'fit',
			items:[{
				loadParams:{
					msgId:rec.getId()
				},
				autoCreateBtns:true,
				xclass:'MsgqUpdateVo'
			}]
		}).showFor(function(f){
			if(f){
				this.lookupI('msgqgrid').getStore().reload();
			}else{
				return false;
			}
		},this);
    },
    toDel:function(rec){
    	this.setLoading('删除中');
    	MsgqController.delMsgq({
    		params:{
    			id:rec.getId()
    		},
    		callback:function(o,f,r){
    			this.setLoading(false);
    			if(f){
    				this.lookupI('msgqgrid').getStore().reload();
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
                xclass:'MsgqGrid',
                itemId:'msgqgrid',
                //title:'队列消息',
				dockedItems:[{
                    xclass:'MsgqSearchDto',
                    docked:'top',
                    rightId:'队列消息列表',
                    autoCreateBtns:true,
                    itemId:'paramform',
                    autoSearch:true
                },{
			    	xtype:'toolbar',
			    	docked:'top',
			    	items:[{
				    	text:'新增队列消息',
					    rightId:'新增队列消息',
					    icon:Rx.Icons.png_16_9.path,
					    rightHidden:true,
					    //cls:'r-btn-highlight r-btn-transparent',
				    	handler:function(btn){
				    		this.getModule().toCreate();
				    	}
				    }]
				}],
				columns:[{
			    	text:"操作",
			        xtype:'actioncolumn',
			        width:150,
			        items: [{
			            icon: Rx.Icons.png_16_27.path,
			            //iconCls:'r-column-visibility-over r-column-visibility-selected',
			            iconCls:'actioncolumn-margin',
			            text:'修改',
			            tooltip: '修改',
			            rightId:'修改队列消息',
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
                        rightId:'删除队列消息',
					    rightHidden:true,
                        tooltip: '删除',
                        handler: function(grid, rowIndex, colIndex) {
                            var rec = grid.getStore().getAt(rowIndex);
                            this.record = rec;
                            Ext.Msg.confirm('消息','确定删除ID为'+rec.getId()+'的队列消息吗？',function(bId){
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
	
});
