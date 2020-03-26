Ext.define('Rx.widget.RenderColumn', {
	requires:[],
    uses:[],
    extend: 'Ext.grid.column.Column',
    alias:'widget.rendercolumn',
    
    statics:{
    	imgUrlRender:function(value, metaData, record, rowIndex, colIndex, store, view){
	    	if(value){
	    		return '<img src='+value+'>';
	    	}
	    	return value;
		},
		booleanRender:function(value, metaData, record, rowIndex, colIndex, store, view){
	    	if(value===true){
	    		return '是';
	    	}else if(value===false){
	    		return '否';
	    	}
	    	return value;
		}
    },
    
    renderFn:null,
    renderer:function(value, metaData, record, rowIndex, colIndex, store, view){
    	if(metaData.column.renderFn){
    		var fn = window.eval(metaData.column.renderFn);
			return fn.apply(this,arguments);
    	}
    	return value;
	}
});
