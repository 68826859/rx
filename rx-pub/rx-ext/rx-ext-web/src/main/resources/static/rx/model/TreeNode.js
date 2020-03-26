Ext.define('Rx.model.TreeNode',{
	requires:[],
    extend: 'Ext.data.TreeModel',
    idProperty: 'id'
},function(){
	
	/*
	if(!Ext.Date.patterns){
		Ext.Date.patterns = {
		    ISO8601Long:"Y-m-d H:i:s",
		    ISO8601Short:"Y-m-d",
		    ShortDate: "n/j/Y",
		    LongDate: "l, F d, Y",
		    FullDateTime: "l, F d, Y g:i:s A",
		    MonthDay: "F d",
		    ShortTime: "g:i A",
		    LongTime: "g:i:s A",
		    SortableDateTime: "Y-m-d\\TH:i:s",
		    UniversalSortableDateTime: "Y-m-d H:i:sO",
		    YearMonth: "F, Y"
		};
	}
	*/
	
	this.$onExtended.unshift({fn:function(cls,data){
		data.statics = data.statics || {};
		
		/*
		var clms = [];
		var fds = data.statics.columns || [],fd=null,i=0,len=fds.length,it;
		for(;i<len;i++){
			fd = fds[i];
			it = {
				text:fd.text,
				dataIndex:fd.dataIndex,
				hidden:fd.hidden,
				sortable:fd.sortable,
				draggable:fd.draggable,
				enableColumnHide:fd.enableColumnHide,
				menuDisabled:fd.menuDisabled,
				width:fd.width,
				minWidth:fd.minWidth
			};
			
			
			if(fd.em){
				it.renderer=function(v){
					var p = arguments.callee.enumitems;
					for(var x in p){
						if(p[x].valueField == v){
							return p[x].displayField;
						}
					}
					return v;
				};
				it.renderer.enumitems = fd.em.items;
			}
			
			if(fd.type == 'date'){
				if(fd.datePattern){
					it.xtype = 'datecolumn';
					it.format = Ext.Date.patterns[fd.datePattern];
					it.defaultRenderer = function(value){
						if(!isNaN(value)){
							value = new Date(parseInt(value));
						}
				        return Ext.util.Format.date(value, this.format);
				    };
				}
			}
			clms.push(it);
		}
		data.statics.columns = clms;
		*/
		
		var ffs = [];
		fds = data.statics.formfields || [],fd=null,i=0,len=fds.length;
		for(;i<len;i++){
			fd = fds[i];
			it = Ext.apply({
				itemId:fd.name
			},fd);
			if(typeof fd.labelSeparator != 'undefined'){
				it.labelSeparator = fd.labelSeparator;
			}
			if(fd.xtype=='field'){
				it.xtype = 'textfield';
			}/*else if(fd.xtype == 'enumcombo' && fd.em){
				Ext.apply(it,{
					xtype:'combo',
					editable:false,
					displayField:'displayField',
					valueField: 'valueField',
					store:{
						fields:['displayField', 'valueField'],
					    data:fd.em.items
					}
				});
			}else if(fd.xtype == 'datefield' && fd.datePattern){
				it.format = Ext.Date.patterns[fd.datePattern];
			}*/
			ffs.push(it);
		}
		data.statics.formfields = ffs;
	},scope:this});
});