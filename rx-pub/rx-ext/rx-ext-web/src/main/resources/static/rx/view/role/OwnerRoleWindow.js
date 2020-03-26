/*
 * 拥有角色
 * */
Ext.define('Rx.view.role.OwnerRoleWindow',{
	requires:[
		'Ext.selection.CheckboxModel',
		'com.rx.pub.role.vo.PubRoleOwnerGrid',
		'com.rx.pub.role.controller.PubRoleOwnerController'
	],
    extend: 'Rx.widget.Window',
    uses:['Rx.view.role.RoleSelectorWindow'],
    //resizable:false,
    width:600,
    height:0.8,
    minWidth:360,
    minHeight:300,
    layout:'fit',
    //autoScroll:true,
    title:'组织角色',
    singleCheck:false,
    
    ownerId:null,
    ownerName:null,
    ownerType:null,
    initComponent:function(){
    	var me = this;
    	var ownerId = me.ownerId;
    	var ownerType = me.ownerType;
    	var singleCk = this.singleCheck?true:false;
    	Ext.apply(me,{
    		title:this.ownerName + ' > 角色',
			layout:'fit',
			width:600,
			height:550,
			items:[{
				xclass:'PubRoleOwnerGrid',
				ownerId:ownerId,
				ownerType:ownerType,
				rightId:'授权列表',
				showFn:function(){/*
					if(Rx.User.hasRight('组织角色列表')){
						var sto = this.getStore();
					    sto.getProxy().extraParams = {
					    	orgId:this.orgId
					    };
					    sto.loadPage(1);
					}*/
					this.loadWithExtraParams({
						ownerId:this.ownerId,
						ownerType:this.ownerType
					},true);
					return false;
				},
				itemId:'list',
				selType:'checkboxmodel',
				columns: {
				   	defaults:{
				        sortable:false,
				        draggable:false,
				        enableColumnHide:false,
				        menuDisabled:true
				   	},
				   	items:[]
				},
				dockedItems:[{
			    	xtype:'toolbar',
			    	docked:'top',
			    	items:[{
				    	text:'添加角色',
				    	rightId:'授权',
				    	handler:function(){
                        	Ext.create('Rx.view.role.RoleSelectorWindow',{singleCheck:false}).showFor(this.valueBack,this);
                        },
                        valueBack:function(roles){
                        	if(roles && roles.length > 0)
                        	{
                        		var o,ids = [];
                        		for(o in roles){
                        			ids.push(roles[o].getId());
                        		}
                        		this.up('window').addIds(ids);
                        	}
                        }
				    },{
				    	text:'移出角色',
				    	rightId:'解除授权',
				    	handler:function(){
                        	var grid = this.up('gridpanel');
                        	var rs = grid.getSelectionModel().getSelection();
                        	if(rs.length > 0){
                        		var ids = [];
                        		for(var o in rs){
                        			ids.push(rs[o].getId());
                        		}
                        		grid.up("window").deleteIds(ids);
                        	}else{
                        		Rx.Msg.alert('提示','未选中任何角色.');
                        	}
                        }
				    }]
				}]
			}]
    	});
    	me.callParent(arguments);
    },
	addIds:function(ids){
		PubRoleOwnerController.addPubRoleOwner({
			params:{
				ownerId:this.ownerId,
				ownerType:this.ownerType,
				roleIds:ids
			},
			callback:function(o,f,r){
		    	this.setLoading(false);
		    	this.lookupI('list').refreshOnRight(r.responseJson.alertMsg);
		    },
			scope:this
		});
		this.setLoading('添加中');
    },
    refreshBack:function(){
    	this.setLoading(false);
    },
    deleteIds:function(ids){
    	var msg = ids.length > 1?'确定移出这些角色？':'确定移出此角色？';
        this.ids = ids;
        Ext.Msg.confirm('消息',msg,function(bId){
    		if(bId=='ok' || bId =='yes'){
    			PubRoleOwnerController.delPubRoleOwner({
	    			params:{
	    				ownerId:this.ownerId,
	    				ownerType:this.ownerType,
	    				id:this.ids
	    			},
					callback:function(o,f,r){
				    	this.setLoading(false);
				    	this.lookupI('list').refreshOnRight(r.responseJson.alertMsg);
				    },
					scope:this
				});
				this.setLoading('删除中');
    		}
    	},this);
    }
});