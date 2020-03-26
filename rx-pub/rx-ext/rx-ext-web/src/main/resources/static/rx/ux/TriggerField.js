Ext.define('Rx.ux.TriggerField', {
	override:'Ext.form.field.Trigger',
	triggerCls:'x-fa fa-caret-right',
	updateEditable: function(editable, oldEditable){
        var me = this;
        if (!editable && typeof me.onTriggerClick =='function' ) {
            me.inputEl.on('click', me.onTriggerClick, me);
        } else {
            me.inputEl.un('click', me.onTriggerClick, me);
        }
        me.callParent([editable, oldEditable]);
    }
});