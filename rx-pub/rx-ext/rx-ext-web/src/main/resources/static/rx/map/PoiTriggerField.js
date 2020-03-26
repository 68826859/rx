Ext.define('Rx.map.PoiTriggerField', {
    extend: 'Ext.form.field.Text',
    alias:'widget.poitriggerfield',
    requires:[],
    uses:['Rx.map.Map','Rx.widget.Window'],
    triggers:{
		bar: {
            cls:Rx.IconCls.map_marker.code,
            handler: function(field,triger,e) {
                Ext.create('Rx.widget.Window',{
                	title:'选择位置-地图双击确定位置',
                	sizeTarget:'viewport',
                	maximizable:true,
                	layout:'fit',
                	value:field.getValue(),
                	items:[{
                		xtype:'rxmap',
						itemId:'map',
						mapReady:function(map){
							
							var va = this.up('window').value;
							if(va){
								var lnglat = va.split(',');
								this.showMarker(lnglat[0],lnglat[1],'','leaflet/images/marker-icon.png',-12,-41);
							}
							map.on('dblclick', function(ev){
							  var ct = ev.target.getCt();
							  ct.up('window').returnValue(ev.lnglat);
							});
						}
                	}]
                }).showFor(function(va){
                	this.setValue(va.lng+','+va.lat);
                },field);
            }
        }
	},
    initComponent:function(){
    	var me = this;
    	Ext.apply(me,{
    		
    	});
    	me.callParent(arguments);
    },
    /*
    setValue: function(value, doSelect) {
    	var me = this;
    	
    	if (!me.store.loaded) {
            me.value = value;
            me.setHiddenValue(me.value);
            return me;
        }
    	me.callParent(arguments);
    },
    */
    destroy:function(){
    	var me = this;
    	me.callParent(arguments);
    }
});