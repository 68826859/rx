Ext.define('Rx.util.Relax',{
	//alternateClassName: 'Rx.RelaxUtil',
	//singleton: true,
	/*放松间隙*/
	constructor: function(){
		this.relaxId = 0;
		this.RelaxArray = [];
		this.relaxQueueId = -1;
		this.RelaxQueue = [];
		this.lazyId = 0;
		this.lazyQueue = [];
		this.noArgs = [];//apply 无参
	},
	/**
	 * 增加一个放松任务
	 * @param target 延迟对象
	 * @param fn     Function或者string
	 * @param ...    传给fn的参数
	 */
	add:function(target,fn){
		var args = Array.prototype.slice.call(arguments,2);
		this.RelaxArray.push({target:target,fn:fn,args:args});
		if(this.relaxId == 0){
			this.relaxId = setTimeout(this._runRelax,0);
		}
	},
	_runRelax:function(){
		var me = Rx.RelaxUtil;//Relax目前只能是单态
		me.relaxId = 0;
    	var len = me.RelaxArray.length;
    	if(len > 0)
    	{
    		var arr = me.RelaxArray;me.RelaxArray = [];
    		for(var i = 0;i<len;i++){
    			me._runTask(arr[i]);
    			arr[i]=null;
    		}
    	}else if(console){
    		console.error('Rx.relax不应该有空转');
    	}
	},
	/**
	 * 
	 * 增加一个排队的放松任务，先进先出
	 * @param target 延迟对象
	 * @param fn     Function或者string
	 * @param ...    传给fn的参数
	 */
    addQueue:function(target,fn){
    	var args = Array.prototype.slice.call(arguments,2);
    	var arr = this.RelaxQueue;
    	arr.push({target:target,fn:fn,args:args});
    	if(arr.length > 1000){//如果过载，清理掉所有任务
    		this.RelaxQueue = [];
    		var len = arr.length;
    		for(var i = 0;i<len;i++){
    			this._runTask(arr[i]);
    			arr[i]=null;
    		}
    	}else{
    		this.turnRelaxOn();
    	}
    },
    turnRelaxOn:function(){
		if(this.relaxQueueId === -1){
			this.relaxQueueId = setInterval(this._runRelaxQueue,1);
		}
    },
    turnRelaxOff:function(){
		if(this.relaxQueueId != -1){
			clearInterval(this.relaxQueueId);this.relaxQueueId = -1;
		}
    },
    /**
     * @private
     */
    _runRelaxQueue:function(){
    	var me = Rx.RelaxUtil;
    	var len = me.RelaxQueue.length;
    	if(len > 0)
    	{
			me._runTask(me.RelaxQueue.shift());//拿出第一个，先进先出
    	}else{
    		me.turnRelaxOff();
    	}
    },
    /**
     * @private
     * 处理一个任务。
     */
    _runTask:function(task){
    	var target = task.target;task.target = null;
		var args = this.noArgs;
		if(task.args){
			args = task.args;
			task.args = null;
		}
		var fn = task.fn;
		if(typeof fn == 'string')
		{
			fn = target[fn];
		}
		task.fn = null;
		if(fn){
			try{
				fn.apply(target,args);
			}catch(e){
				if(console){console.error(e)}
			}
		}
    },
    /**
     * 增加一个懒任务，短时间之内添加相同的(相同与否，以target+fn判断)懒任务，将只执行一次。
     * @param target
     * @param fn
     */
    lazy:function(delay,target,fn){
    	var args = Array.prototype.slice.call(arguments,3);
    	var now = new Date().getTime(),timeout = now + delay;
    	var arr = this.lazyQueue,len = arr.length;
    	var has = false,doTurn = true;
    	if(len > 0){
    		for(var i=0,tmp;i<len;i++){
    			tmp = arr[i];
    			if(tmp.target == target && tmp.fn == fn){
    				has = true;tmp.args = args;
    				if(timeout >= tmp.timeout){
    					tmp.timeout = timeout;
    					return;
    				}
    				tmp.timeout = timeout;
    			}
    			if(tmp.timeout - now < 0){
    				doTurn = false;
    			}else{
    				delay = Math.min(tmp.timeout - now,delay);
    			}
    		}
    	}
    	if(!has){
    		arr.push({target:target,fn:fn,args:args,timeout:timeout});
    	}
    	if(doTurn || this.lazyId == 0){
    		this.turnLazyOn(delay);
    	}
    	/*if(this.turnLazyOn === fn){
    		//
    	}else{
    		this.lazy(0, this,turnLazyOn,lazy);
    	}*/
    	
    },
    turnLazyOn:function(delay){
    	if(this.lazyId != 0){
    		clearTimeout(this.lazyId);
    	}
    	this.lazyId = setTimeout(this._runLazy,delay);
    },
    _runLazy:function(){
    	var me = Rx.RelaxUtil;
    	me.lazyId = 0;
    	var len = me.lazyQueue.length;
    	if(len > 0)
    	{
    		var arr = me.lazyQueue;
    		var now = new Date().getTime();
    		var keeps = [],delay = Number.MAX_VALUE,tmp;
    		for(var i = 0;i<len;i++){
    			tmp = arr[i];
    			if(tmp.timeout > now){
    				keeps.push(tmp);
    				delay = Math.min(tmp.timeout-now,delay);
    			}else{
    				me._runTask(tmp);
    			}
    			arr[i]=null;
    		}
    		me.lazyQueue = keeps;
    		if(keeps.length > 0){
    			me.turnLazyOn(delay);
    		}
    	}
    }
    /*setTimeout:function(delay,fn,scope){
    	var args = Array.prototype.slice.call(arguments,3);
    	var fun = function(){
    		if(typeof fn == 'string')
			{
				fn = scope[fn];
			}
			fn.apply(scope,args);
    	};
    	window.setTimeout(fun,delay);
    },
    setInterval:function(interval,fn,scope){
    	return window.setInterval(function(){},interval);
    },*/
},function(){
	var me = this;
	Rx.RelaxUtil = Rx.Relax = new me();
});