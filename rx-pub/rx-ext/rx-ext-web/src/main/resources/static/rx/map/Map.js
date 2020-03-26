Ext.define('Rx.map.Map', {
    extend: 'Ext.Panel',
    requires:['Rx.widget.Message','Rx.util.Relax'],
    //layout: 'border',
	//cls:'',
    alias:'widget.rxmap',
    map:null,
    classReady:false,
    copyrightContent:'<a href="#" style="font-size:12px;background:#dbdbff;">XX版权所有</a>',
    enableScrollWheelZoom:true,//是否允许滚轴放大缩小
    showScaleControl:true,//显示左上角比例尺
    showLeftNavigationControl:true,//显示左上角缩放平移控件
    showRightNavigationControl:false,//显示右上角缩放平移控件
    showOverviewMapControl:true,//显示鹰眼
    showCopyrightControl:true,//显示版权信息
    showGeolocationControl:true,//显示定位控件
    showMyDisplayControl:false,
    layout:'absolute',
    constructor:function(config){
    	var me = this;
    	var items = [{
			xtype:'box',
			itemId:'mapdiv',
			x:0,
			y:0,
			width:'100%',
			height:'100%'
		},{
			//xtype:'container',
			hidden:!this.showMyDisplayControl,
			width:120,
			height:26,
			//bodyStyle:'padding-left:5px;',
			style:'right:0;border:1px solid #cccccc;background-color:#358ac8;',
            xtype: 'container',
            defaultType: 'checkboxfield',
            layout:'hbox',
            //fieldLabel:' ',
            items:[{
            	xtype:'button',
            	text:'搜索附近',
            	//style:'color:#333 !important;',
                cls:'r-btn-transparent-linefocus',
            	enableToggle:true,
    			toggleHandler:function(btn,state){
    				if(state){
    					this.up('bmap').doPoint("pointBack",this);
    				}else{
    					this.up('bmap').doPoint(false);
    				}
    			},
    			pointBack:function(point){
    				this.toggle(false,false);
    				var map = this.up('bmap');
    				var areas = null;
    				try{
    					areas = Rx.AreaManager.getNearBy(point,map.getMap());
    				}catch(e){
    					Rx.Msg.error('提示','位置数据有误.');
    					return;
    				}
    				if(areas.length > 0){
    					map.getMap().clearOverlays();
    					var pointArray = [];
        				for(var i=0;i<areas.length;i++){
        					 pointArray = pointArray.concat(new Rx.model.Area(areas[i]).showInMap(map));
        				}
        				//map.getMap().setViewport(pointArray);//调整视野
        			}else{
        				Rx.Msg.alert('提示','附近没有数据.');
        			}
    			}
            },{
            	xtype:'button',
            	text:'清屏',
            	//style:'color:#333 !important;',
                cls:'r-btn-transparent-linefocus',
            	handler:function(){
            		this.up('bmap').getMap().clearOverlays();
            	}
            }]
            /*items: [
                {
                    boxLabel  : '显示种植模式&nbsp;&nbsp;&nbsp;',
                    style:'margin-left:5px',
                    name      : 'typeOption',
                    checked   : true,
                    inputValue: '1',
                    id        : 'typeOption1',
                    listeners:{
                    	change:function(c,nv,ov,e){
                    		//debugger
                    	}
                    }
                }, {
                    boxLabel  : '显示土壤信息&nbsp;&nbsp;&nbsp;',
                    name      : 'typeOption',
                    inputValue: '2',
                    id        : 'typeOption2'
                }, {
                    boxLabel  : '显示气象信息&nbsp;&nbsp;&nbsp;',
                    name      : 'typeOption',
                    inputValue: '3',
                    id        : 'typeOption3'
                }
            ]*/
		}];
    	if(Ext.isArray(config.items)){
    		config.items = items.concat(config.items);
    	}else{
    		config.items = items;
    	}
    	me.callParent(arguments);
	},
	getMap:function(){
		if(!this.map){
			Rx.Msg.alert('提示','地图未就绪.');
		}
		return this.map;
	},
	
	initMap:function(){},
	
	getWKTlayer:function(wkt){
		return omnivore.wkt.parse(wkt);
	},
	showFn:function(){
		var me = this;
		if(!me.classReady) {
            Rx.Relax.lazy(500, me, 'showFn');
            return false;
        }
		var map = me.map = me.initMap(me.getComponent('mapdiv').el);
		map.getCt = function(){return me;};
        me.mapReady(map);
        return false;
	},
	mapReady:Ext.emptyFn,
	getOverlayById:function(id){
		var allOverlay = this.getMap().getOverlays();
		for (var i = 0; i < allOverlay.length - 1; i++){
			if(allOverlay[i].id == id || allOverlay[i].itemId == id){
				return allOverlay[i];
			}
		}
		return null;
	},
	removeOverlay:function(overlay){
		if(typeof overlay == 'string'){
			overlay = this.getOverlayById(overlay);
		}
		if(overlay){
			this.getMap().removeOverlay(overlay);
		}
	},
	toCity:function(text){
		var bdary = new AMap.Boundary(); 
		var me = this;
		var map = this.getMap();
		bdary.get(text,function(rs){//获取行政区域
			var count = rs.boundaries.length;//行政区域的点有多少个
          	var pointArray=[];
          	var pointArr = [];
			var ply;
			for (var i = 0; i < count; i++) {
				ply = new AMap.Polygon(rs.boundaries[i], {strokeWeight: 2, strokeColor: "#ff0000"}); //建立多边形覆盖物
				pointArray = pointArray.concat(ply.getPath());
				//map.addOverlay(ply); 
			}
			map.setViewport(pointArray);    //调整视野 
			//var b = new AMap.Bounds(new AMap.Point(116.027143, 39.772348),new AMap.Point(116.832025, 40.126349));
			/*
			try {
				var bds = ply.getBounds();
				map.addOverlay(new AMap.Marker(bds.getSouthWest()));
  				map.addOverlay(new AMap.Marker(bds.getNorthEast()));
				AMapLib.AreaRestriction.setBounds(map,new AMap.Bounds(bds.getSouthWest(),bds.getNorthEast()));
			} catch (e) {
				//alert(e);
			}
			*/
		});
	},
	showMarker:function(lon,lat,label,icon){
		var map = this.getMap();
		var pt = new AMap.Point(lon,lat);
		var marker;
		if(icon){
			var myIcon = new AMap.Icon(icon, new AMap.Size(25,25));
			var marker = new AMap.Marker(pt,{icon:myIcon});  // 创建标注
		}else{
			marker = new AMap.Marker(pt);
		}
		map.addOverlay(marker);
		if(label){
			var lb = new AMap.Label(label,{offset:new AMap.Size(25,-10)});
			marker.setLabel(lb);
		}
		return marker;
	},
	showBounds:function(){
		var bds = [new AMap.Point(116.027143, 39.772348),new AMap.Point(116.832025, 40.126349)];
		this.getMap().setViewport(bds);
	},
	showInfoWindow:function(lan,lat,text){
		var map = this.getMap();
		var point = new AMap.Point(lan,lat);
		//var marker = new AMap.Marker(point);  // 创建标注
		//map.addOverlay(marker);              // 将标注添加到地图中
		map.centerAndZoom(point, 15);
		var opts = {
		  width : 200,     // 信息窗口宽度
		  height: 100,     // 信息窗口高度
		  title : "海底捞王府井店111" , // 信息窗口标题
		  enableMessage:true,//设置允许信息窗发送短息
		  message:"亲耐滴"
		}
		var infoWindow = new AMap.InfoWindow("地址：北京市东城区王府井大街88号乐天银泰百货八层", opts);  // 创建信息窗口对象 
		       
		map.openInfoWindow(infoWindow,point); //开启信息窗口
	},
	getBoundary:function(place){
		var map = this.getMap();
		var bdary = new AMap.Boundary();
		//map.clearOverlays();//清除地图覆盖物     
		bdary.get(place, function(rs){//获取行政区域
			var count = rs.boundaries.length; //行政区域的点有多少个
			if (count === 0) {
				Rx.Msg.alert('提示','未能获取当前输入行政区域');
				return ;
			}
          	var pointArray = [];
			for (var i = 0; i < count; i++) {
				var ply = new AMap.Polygon(rs.boundaries[i], {strokeWeight: 2, strokeColor: "#ff0000"}); //建立多边形覆盖物
				map.addOverlay(ply);  //添加覆盖物
              ply.addEventListener("click", function(e){
                console.log(e);
                alert(e);
              });
				pointArray = pointArray.concat(ply.getPath());
			}    
			map.setViewport(pointArray);    //调整视野             
		});
	}
},function(mapcls){
	
});