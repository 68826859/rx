Ext.define('Rx.ux.JsonAjax',{
	requires:['Ext.window.MessageBox'],
	override:'Ext.data.request.Ajax',
	timeout:60000,
	rxUserClass:null,
	setupHeaders:function(xhr, options, data, params){
		var me = this;
		if(Ext.data.request.Ajax.prototype.rxUserClass){
			if(me.defaultHeaders){
				me.defaultHeaders.rxuserclass = Ext.data.request.Ajax.prototype.rxUserClass;
			}else{
				me.defaultHeaders = {rxuserclass:Ext.data.request.Ajax.prototype.rxUserClass};
			}
		}
		return me.callParent(arguments);
	},
    createResponse: function(xhr) {
        var me = this,
            isXdr = me.isXdr,
            headers = {},
            lines = isXdr ? [] : xhr.getAllResponseHeaders().replace(/\r\n/g, '\n').split('\n'),
            count = lines.length,
            line, index, key, response, byteArray;
 
        while (count--) {
            line = lines[count];
            index = line.indexOf(':');
            
            if (index >= 0) {
                key = line.substr(0, index).toLowerCase();
                
                if (line.charAt(index + 1) == ' ') {
                    ++index;
                }
                
                headers[key] = line.substr(index + 1);
            }
        }
        
        response = {
            request: me,
            requestId: me.id,
            status: xhr.status,
            statusText: xhr.statusText,
            getResponseHeader: me._getHeader,
            getAllResponseHeaders: me._getHeaders
        };
 
        if (isXdr) {
            me.processXdrResponse(response, xhr);
        }
 
        if (me.binary) {
            response.responseBytes = me.getByteArray(xhr);
        }
        else {
            response.responseText = xhr.responseText;
            response.responseXML = xhr.responseXML;
            if(xhr.status === 0){
    			response.responseText = '{"code":0,"alertType":4,"alertMsg":"连接不到服务器(服务器端停止或网络异常)"}';
            }
            try{
            	var resText = Ext.String.trim(response.responseText);
            	if(resText == ''){
            		response.responseJson = null;
            	}else{
            		var jsonRes = response.responseJson = Ext.decode(resText);
            		//if(xhr.status == 200){
		        		//var jsonRes = response.responseJson = Ext.decode(resText);
				        var alertType=jsonRes.alertType;
				        var code = jsonRes.code;
				        var status = parseInt(code);
				        var alertMsg = jsonRes.alertMsg;
				        if(status != 0 && status != 200){
				        	response.failture = true;
				        	response.status = status;
				        }
				        if(status == 399){
				        	if(Ext.Ajax.autoRefresh){//已经进入系统
				        		alertType = parseInt(alertType);
				        		alertMsg = decodeURIComponent(alertMsg);
				        		if(alertType != 0){
				        			alert(alertMsg);
				        		}
				        		window.location.reload();
				        		return null;
				        	}else{//尚未进入系统
				        		return response;
				        	}
				        	
				        }else if(status == 398){
				        	//Ext.create('Rx.view.LicenseUploadWindow').show();
				        }else if(status == 397){
				        	//Ext.create('Rx.view.SysInitWindow').show();
				        }
				        if(alertType && alertMsg){
				        	alertType = parseInt(alertType);
				        	alertMsg = decodeURIComponent(alertMsg);
				        	if(alertType == 1){
				    			Rx.Msg.alert('消息',alertMsg);//无需关闭的提示
					    	}else if(alertType == 2){
					    		Ext.Msg.alert('消息',alertMsg);//需要关闭的提示
					    	}else if(alertType == 3){
					    		Rx.Msg.error('错误',alertMsg);//无需关闭的错误
					    	}else if(alertType == 4){
					    		Ext.Msg.error('错误',alertMsg);//需要关闭的错误
					    	}else if(alertType == 5){//警告
					    		
					    		response.confirmed = window.confirm(alertMsg);
					    		//Ext.Msg.confirm('消息','alertMsg',function(bId){
			    		        //    if(bId=='ok' || bId =='yes'){
			    		        //    	
			    		        //    }
			    		        //});
					    	}
				        }
            		//}else{
            			//其他错误
            		//}
            	}
            }catch(e){
            	response.failture = true;
            	console.error('服务器超时');
            	Rx.Msg.error('错误','服务器超时');
            }
        }
 
        return response;
    }
},function(){
	Ext.onInternalReady(function() {
		Ext.Msg.error = function(title,msg){
			Ext.Msg.show({
			    title: title,
			    msg: msg,
			    minWidth: this.minWidth,
			    buttons: Ext.Msg.OK,
			    //multiline: true,
			    fn: Ext.emptyFn,
			    //animateTarget: 'addAddressBtn',
			    icon: Ext.window.MessageBox.INFO
			});
		};
		Ext.Ajax.setTimeout(60000);
	});
});
	/*
	Ext.define('Rx.ProxyAjax',{
		override:'Ext.data.proxy.Ajax',
		doRequest: function(operation, callback, scope) {
	        var writer  = this.getWriter(),
	            request = this.buildRequest(operation);
	        if (operation.allowWrite()) {
	            request = writer.write(request);
	        }
	        Ext.apply(request, {
	        	notDecode     : true,//不自动转换json
	            binary        : this.binary,
	            headers       : this.headers,
	            timeout       : this.timeout,
	            scope         : this,
	            callback      : this.createRequestCallback(request, operation, callback, scope),
	            method        : this.getMethod(request),
	            disableCaching: false
	        });
	        Ext.Ajax.request(request);
	        return request;
	    }
	});
	
	*/
