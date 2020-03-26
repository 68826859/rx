/**
 * create by Ray 2018-5-12
 */
Ext.define('Rx.spring.SpringProviderTreeStore', {
	requires:['Rx.util.Relax'],
    uses:[],
    extend: 'Ext.data.TreeStore',
    proxysClass:null,
    proxyName:null,
    alias:"store.springprovidertreestore",
    constructor: function(config) {
        var me = this;
        me.callParent([config]);
    }
});
