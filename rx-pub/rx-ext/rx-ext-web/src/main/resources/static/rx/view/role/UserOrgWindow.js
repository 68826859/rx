/*
 * 用户组织
 * */
Ext.define('Rx.view.role.UserOrgWindow',{
	requires:[
		'Ext.selection.CheckboxModel',
		'com.rx.pub.organization.dto.UserOrgGrid',
		'com.rx.pub.organization.controller.SysUserOrgRefController',
		'com.rx.pub.organization.dto.UserOrgAdd'
	],
    extend: 'Rx.widget.Window',
    uses:['Rx.view.role.RoleSelectorWindow'],
    width:650,
    height:0.8,
    minWidth:360,
    minHeight:300,
    layout:'fit',
    title:'用户组织',
    singleCheck:false,
    userName:null,
	items:[{
		xclass:'UserOrgGrid',
		//rightId:'用户组织列表',
		frame:true,
		showFn:function(){
			/*if(Rx.User.hasRight('用户组织列表')){
				var un = this.up('window').userName;
				var sto = this.getStore();
			    sto.getProxy().extraParams = {
			    	userName:un
			    };
			    sto.loadPage(1);
			}*/
			this.loadWithExtraParams({
		    	userName:this.up('window').userName
		    },true);
			return false;
		},
		itemId:'list',
		//columnLines: true,
		selType:'checkboxmodel',
		columns: {
		   	items:[{
	        text:'移除',
	        xtype:'actioncolumn',
	        width:50,
	        items:[{
                icon: Rx.Icons.png_16_16.path,
                //rightId:'删除用户的组织',
                tooltip: '移除用户的组织',
                getClass:function(v,mt,rec,row,cel,sto){
                	var rec = sto.getAt(row);
                	if(rec.get('relation') == 0){
                		return 'r-hidden';
                	}
                	return null;
                },
                handler: function(grid, rowIndex, colIndex) {
                    var rec = grid.getStore().getAt(rowIndex);
                    this.up('window').deleteRec(rec);
                }
	        }]
		}]}
	}],
    initComponent:function(){
    	var me = this;
    	var singleCk = this.singleCheck?true:false;
    	Ext.apply(me,{
    		title:me.userName + ' > 组织',
		    dockedItems:[{
		    	xtype:'toolbar',
		    	docked:'top',
		    	items:[{
			    	text:'添加用户组织',
			    	//rightId:'新增用户的组织',
			    	handler:function(){
		            	Ext.create("Rx.widget.Window",{
							title:"添加用户组织",
							layout:'fit',
							width:500,
							height:400,
							//maximizable:true,
							items:[{
								initValues:{userName:this.up('window').userName},
								autoCreateBtns:true,
								xclass:'UserOrgAdd'
							}]
						}).showFor(function(fm,res,data){
							if(res.response.status == 200){
								this.up('window').lookupI('list').refreshOnRight('添加用户组织成功.');;
							}else{
								return false;
							}
						},this);
		            }
			    },{
			    	text:'移出用户组织',
			    	hidden:true,
			    	//rightId:'删除用户的组织',
			    	handler:function(){
		            	var win = this.up('window');
		            	var rs = win.getComponent(0).getSelectionModel().getSelection();
		            	if(rs.length > 0){
		            		var ids = [];
		            		for(var o in rs){
		            			ids.push(rs[o].get('roleId'));
		            		}
		            		win.deleteIds(ids);
		            	}else{
		            		Rx.Msg.alert('提示','未选中任何组织.');
		            	}
		            }
			    }]
			}]
    	});
    	me.callParent(arguments);
    },
    deleteRec:function(rec){
        this.delRec = rec;
        Ext.Msg.confirm('消息','确定移除该项？',function(bId){
    		if(bId=='ok' || bId =='yes'){
    			SysUserOrgRefController.delUserOrg({
	    			params:this.delRec.getData(),
					callback:function(){
				    	this.setLoading(false);
				    	this.lookupI('list').refreshOnRight('移除用户组织成功');
				    },
					scope:this
				});
				this.setLoading('删除中');
    		}
    	},this);
    }
});