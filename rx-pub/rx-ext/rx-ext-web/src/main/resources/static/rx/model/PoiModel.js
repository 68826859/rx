Ext.define('Rx.model.PoiModel', {
			requires : [],
			extend : 'Ext.data.Model',
			idProperty : 'sn',
			fields : [{
						comment:'ID',
						name : 'sn',
						type : 'string'
					}, {
						comment:'经度',
						name : 'lon',
						type : 'float'
					}, {
						comment:'纬度',
						name : 'lat',
						type : 'float'
					}, {
						comment:'时间',
						name : 'tm',
						type : 'string'
					}, {
						comment:'精度',
						name : 'ac',
						type : 'float'
					}, {
						comment:'速度',
						name : 'sp',
						type : 'float'
					}, {
						comment:'方向',
						name : 'be',
						type : 'float'
					}, {
						comment:'定位来源',
						name : 'fm',
						type : 'int'
					}, {
						comment:'人物类型',
						name : 'wt',
						type : 'int'
					}, {
						comment:'人物',
						name : 'wi',
						type : 'string'
					}, {
						comment:'事件类型',
						name : 'gt',
						type : 'int'
					}, {
						comment:'事件',
						name : 'gi',
						type : 'string'
					}, {
						comment:'备注',
						name : 'ex',
						type : 'string'
					}]
		});