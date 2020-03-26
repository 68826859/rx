/**
 * create by Ray 2018-5-12
 */
Ext.define('Rx.spring.SpringProviderStore', {
	requires:['Rx.util.Relax'],
    uses:[],
    extend: 'Ext.data.Store',
    alias:"store.springproviderstore",
    proxysClass:null,
    proxyName:null,
    constructor: function(config) {
        var me = this;
        //config.proxy = eval(config.proxysClass+"."+config.proxyName);
        me.callParent([config]);
    }
});
