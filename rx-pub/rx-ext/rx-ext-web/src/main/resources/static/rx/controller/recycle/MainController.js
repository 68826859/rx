Ext.define('Rx.controller.recycle.MainController', {
    extend: 'Rx.controller.BaseViewController',
    alias: 'controller.recycle',
    requires: ['com.rx.pub.crud.controller.RecycleController'],

    restoreBean:function(view,rowIndex,collIndex,item,e,record,row){
        this.getView().setLoading('还原中');
        RecycleController.restoreBean({
            params:{
                id:record.getId()
            },
            callback:this.restoreBack_,
            scope:this
        });
    },
    doDeleteRecord:function(view,rowIndex,collIndex,item,e,record,row){
    	this.getView().setLoading('删除中');
        RecycleController.removeBean({
            params:{
            	id:record.getId()
            },
            callback:this.restoreBack_,
            scope:this
        });
    },
    /**
     * 清空回收站
     */
    removeBeanList:function(){
        //var refs = this.getReferences();
        //var cen = refs.mainCardPanel;
        Ext.Msg.confirm('消息','确定清空回收站？',function(bId){
            if(bId=='ok' || bId =='yes'){
                this.getView().setLoading('清空中');
                RecycleController.removeBeanList({
                    callback:this.restoreBack_,
                    scope:this
                });
            }
        },this);
    },
    restoreBack_:function(o,f,r){
        this.getView().setLoading(false);
        if(f){
            this.getView().getStore().reload();
        }
    }
});