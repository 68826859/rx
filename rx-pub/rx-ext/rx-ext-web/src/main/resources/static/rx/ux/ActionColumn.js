Ext.define('Rx.ux.ActionColumn', {
	requires:['Ext.grid.column.Action'],
	override:'Ext.grid.column.Action',
	actionIconCls:'x-action-col',
	defaultRenderer: function(v, cellValues, record, rowIdx, colIdx, store, view) {
        var me = this,actionIdReIndex,
            scope = me.origScope || me,
            items = me.items,
            len = items.length,
            i, item, ret, disabled, tooltip, altText, icon, glyph, tabIndex, ariaRole;
            
        ret = Ext.isFunction(me.origRenderer) ? me.origRenderer.apply(scope, arguments) || '' : '';
 
        cellValues.tdCls += ' ' + Ext.baseCSSPrefix + 'action-col-cell';
        
        for (i = 0; i < len; i++) {
            item = items[i];actionIdReIndex = Ext.baseCSSPrefix + 'action-col-' + String(i);
            icon = item.icon;
            glyph = item.glyph;
 
            disabled = item.disabled || (item.isDisabled ? Ext.callback(item.isDisabled, item.scope || me.origScope, [view, rowIdx, colIdx, item, record], 0, me) : false);
            tooltip  = item.tooltip  || (item.getTip     ? Ext.callback(item.getTip, item.scope || me.origScope, arguments, 0, me) : null);
            altText  =                   item.getAltText ? Ext.callback(item.getAltText, item.scope || me.origScope, arguments, 0, me) : item.altText || me.altText;
            if (!item.hasActionConfiguration) {
                item.stopSelection = me.stopSelection;
                item.disable = Ext.Function.bind(me.disableAction, me, [i], 0);
                item.enable = Ext.Function.bind(me.enableAction, me, [i], 0);
                item.hasActionConfiguration = true;
            }
            if (glyph) {
                glyph = Ext.Glyph.fly(glyph);
            }
            tabIndex = (item !== me && item.tabIndex !== undefined) ? item.tabIndex : me.itemTabIndex;
            ariaRole = (item !== me && item.ariaRole !== undefined) ? item.ariaRole : me.itemAriaRole;
            
            var noRight = false;
            if(item.rightId && !Rx.User.hasRight(item.rightId)){
            	if(item.rightHidden){
            		continue;
            	}
            	disabled = true;
            	//altText = "无权访问" + altText;
            	noRight = true;
            }
            if(noRight){
        		ret += '<div style="display:inline-block;line-height:1;" title="无权访问 '+tooltip+'">';
        		actionIdReIndex ='x-item-disabled';
        	}
        	
        	if(item.text){
        		ret+='<div class="'+ me.actionIconCls + ' ' + actionIdReIndex +'" style="cursor:pointer;margin-left:4px;display:inline-block;border:1px solid #ddd;padding:2px;">';
        		/*
        		if(icon){
        			ret+='<span class="'+actionIdReIndex+'" style="display:inline-block;margin-left:-4px;">';
        		}
        		*/
        	}
        	
            ret += '<' + (icon ? 'img' : 'div') + 
                (typeof tabIndex === 'number' ? ' tabIndex="' + tabIndex + '"' : '') +
                (ariaRole ? ' role="' + ariaRole + '"' : ' role="presentation"') +
                (icon ? (' style="margin-left:5px;width:16px;height:16px;" alt="' + altText + '" src="' + item.icon + '"') : '') +
                ' class="' + (item.text?' ':(me.actionIconCls + ' x-action-col-icon ')) + actionIdReIndex + ' ' +
                (disabled ? me.disabledCls + ' ' : ' ') +
                (item.hidden ? Ext.baseCSSPrefix + 'hidden-display ' : '') +
                (item.getClass ? Ext.callback(item.getClass, item.scope || me.origScope, arguments, undefined, me) : (item.iconCls || me.iconCls || '')) + '"' +
                (tooltip ? ' data-qtip="' + tooltip + '"' : '') + (icon ? '/>' : glyph ? (' style="font-family:' + glyph.fontFamily + '">' + glyph.character + '</div>') : '></div>');
           
            if(item.text){
            	/*
            	if(icon){
            		ret += '</span>';
            	}
            	*/
            	ret += '<span style="display:inline-block;" class="'+ actionIdReIndex +' x-btn-inner x-btn-inner-plain-toolbar-small">'+item.text+'</span></div>';
            }
            
           if(noRight){
        		ret += '</div>';
        	}
        }
        return ret;
    }
});
