Ext.define('Rx.ux.FormFieldVTypes', {
	override:'Ext.form.field.VTypes',
	mobilephoneText: '请输入正确的手机号码',
	mobilephoneMask: /[0-9]/i,
	mobilephone:function(value){
		return /^1\d{10}$/.test(value);
	},
	
	idcardText: '请输入正确的身份证号码',
	idcardMask: /[A-Za-z0-9]/i,
	idcard:function(value){
		return /^[1-9]\d{5}(18|19|20|(3\d))\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/.test(value);
	}
	
});