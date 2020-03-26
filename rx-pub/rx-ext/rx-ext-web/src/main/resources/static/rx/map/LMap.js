Ext.define('Rx.map.LMap', {
    extend: 'Ext.Panel',
    requires:['Rx.widget.Message','Rx.util.Relax'],
    //layout: 'border',
	//cls:'',
    alias:'widget.lmap',
    map:null,
    isReady:false,
    copyrightContent:'<a href="#" style="font-size:12px;background:#dbdbff;">XX版权所有</a>',
    enableScrollWheelZoom:true,//是否允许滚轴放大缩小
    showScaleControl:true,//显示左上角比例尺
    showLeftNavigationControl:true,//显示左上角缩放平移控件
    showRightNavigationControl:false,//显示右上角缩放平移控件
    showOverviewMapControl:true,//显示鹰眼
    showCopyrightControl:true,//显示版权信息
    showGeolocationControl:true,//显示定位控件
    
    showMyDisplayControl:false,//是否显示右上角显示种植模式，土壤信息，气象信息
    
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
	
	getDisplayTypeOption:function(){
		var res = [];
		if(Ext.getCmp('typeOption1').getValue()){
			res.push(1);
		}
		if(Ext.getCmp('typeOption2').getValue()){
			res.push(2);
		}
		if(Ext.getCmp('typeOption3').getValue()){
			res.push(3);
		}
		return res;
	},
	
	doPointFn:null,
	doPointScope:null,
	oldCursor:null,
	doPoint:function(fn,scope){
		var map = this.getMap();
		if(fn){
			map.setDefaultCursor("crosshair");
			this.doPointFn = fn;
			this.doPointScope = scope;
		}else{
			map.setDefaultCursor(this.oldCursor);
			this.doPointFn = null;
			this.doPointScope = null;
		}
	},
	doPointEnd:function(point){
		if(this.doPointFn){
			Rx.Relax.add(this.doPointScope,this.doPointFn,point);
			this.getMap().setDefaultCursor(this.oldCursor);
			this.doPointFn = null;
			this.doPointScope = null;
		}
	},
	
	getMap:function(){
		if(!this.map){
			Rx.Msg.alert('提示','地图未就绪.');
		}
		return this.map;
	},
	
	getWKTlayer:function(wkt){
		return omnivore.wkt.parse(wkt);
	},
	/*
	onBoxReady:function(){
		this.callParent(arguments);
		this.showFn();
	},
	*/
	showFn:function(){
		if(!this.isReady) {
            Rx.Relax.lazy(500, this, 'showFn');
            return false;
        }
        var map = this.map = L.map(this.getComponent('mapdiv').el.dom.id).setView([33.92,107.95],5);

        //var tileLayer = L.tileLayer.wms('http://114.115.220.124:6080/arcgis/services/xj/MapServer/WMSServer');
    	//tileLayer.addTo(map);
        
        
        
        
        
        L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
		    attribution: 'xxx'
		}).addTo(map);
        
        
        
        /*
        L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw', {
            maxZoom: 18,
            attribution: 'xxx'
            id: 'mapbox.streets'
        }).addTo(map);
        */
			

		//var map = this.map = new LMap.Map(this.getComponent('mapdiv').el.dom.id);
		//map.centerAndZoom(new LMap.Point(116.403765, 39.914850), 5);//可以初始化定位到某个地方



        this.mapReady(map);
        return false;




		if(this.enableScrollWheelZoom){
			map.enableScrollWheelZoom();
		}
		
		if(this.showScaleControl){
			var top_left_control = new LMap.ScaleControl({anchor: BMAP_ANCHOR_TOP_LEFT});// 左上角，添加比例尺
			map.addControl(top_left_control);
		}
		if(this.showLeftNavigationControl){
			var top_left_navigation = new LMap.NavigationControl();  //左上角，添加默认缩放平移控件   
			map.addControl(top_left_navigation);
		}
		if(this.showRightNavigationControl){
			var top_right_navigation = new LMap.NavigationControl({anchor: BMAP_ANCHOR_TOP_RIGHT, type: BMAP_NAVIGATION_CONTROL_SMALL}); //右上角，仅包含平移和缩放按钮
			/*缩放控件type有四种类型:
			BMAP_NAVIGATION_CONTROL_SMALL：仅包含平移和缩放按钮；BMAP_NAVIGATION_CONTROL_PAN:仅包含平移按钮；BMAP_NAVIGATION_CONTROL_ZOOM：仅包含缩放按钮*/        
			map.addControl(top_right_navigation);
		}
		if(this.OverviewMapControl){
			var overViewOpen = new LMap.OverviewMapControl({isOpen:true, anchor: BMAP_ANCHOR_BOTTOM_RIGHT});
			map.addControl(overViewOpen);
		}
		if(this.showCopyrightControl){
			var cr = new LMap.CopyrightControl({anchor: BMAP_ANCHOR_BOTTOM_RIGHT});   //设置版权控件位置
			map.addControl(cr); //添加版权控件
			var bs = map.getBounds();   //返回地图可视区域
			cr.addCopyright({id:1, content:this.copyrightContent, bounds: bs});
		}
		
		/*
		var navigationControl = new LMap.NavigationControl({
		    // 靠左上角位置
		    anchor: BMAP_ANCHOR_TOP_LEFT,
		    // LARGE类型
		    type: BMAP_NAVIGATION_CONTROL_LARGE,
		    // 启用显示定位
		    enableGeolocation: true
		});
		map.addControl(navigationControl);// 添加定位控件
		*/
		if(this.showGeolocationControl){
			var geolocationControl = new LMap.GeolocationControl();
			geolocationControl.addEventListener("locationSuccess", function(e){
		    	// 定位成功事件
		    	var address = '';
		    	address += e.addressComponent.province;
		    	address += e.addressComponent.city;
		    	address += e.addressComponent.district;
		    	address += e.addressComponent.street;
		    	address += e.addressComponent.streetNumber;
		    	//alert("当前定位地址为：" + address);
		    });
		    geolocationControl.addEventListener("locationError",function(e){
		    	// 定位失败事件
		    	alert(e.message);
		    });
		    map.addControl(geolocationControl);
		}
		
		var me = this;
		map.getCt = function(){return me;};
		map.addEventListener("click",function(e){
			e.target.getCt().doPointEnd(e.point);
			//alert(e.point.lng + "," + e.point.lat);
		});
		this.oldCursor = map.getDefaultCursor();
		this.mapReady(map);
		
		
		
		/*
	map.centerAndZoom(new LMap.Point(116.404, 39.915), 13);
	map.enableScrollWheelZoom();
  map.addOverlay(new LMap.Marker(new LMap.Point(116.027143, 39.772348)));
  //map.addOverlay(new LMap.Marker(new LMap.Point(116.832025, 40.126349)));
	var b = new LMap.Bounds(new LMap.Point(116.027143, 39.772348),new LMap.Point(116.832025, 40.126349));
	try {	
		LMapLib.AreaRestriction.setBounds(map, b);
	} catch (e) {}
		
		return false;
		*/
		
		
		
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
		var bdary = new LMap.Boundary(); 
		var me = this;
		var map = this.getMap();
		bdary.get(text,function(rs){//获取行政区域
			var count = rs.boundaries.length;//行政区域的点有多少个
          	var pointArray=[];
          	var pointArr = [];
			var ply;
			for (var i = 0; i < count; i++) {
				ply = new LMap.Polygon(rs.boundaries[i], {strokeWeight: 2, strokeColor: "#ff0000"}); //建立多边形覆盖物
				pointArray = pointArray.concat(ply.getPath());
				//map.addOverlay(ply); 
			}
			map.setViewport(pointArray);    //调整视野 
			//var b = new LMap.Bounds(new LMap.Point(116.027143, 39.772348),new LMap.Point(116.832025, 40.126349));
			/*
			try {
				var bds = ply.getBounds();
				map.addOverlay(new LMap.Marker(bds.getSouthWest()));
  				map.addOverlay(new LMap.Marker(bds.getNorthEast()));
				LMapLib.AreaRestriction.setBounds(map,new LMap.Bounds(bds.getSouthWest(),bds.getNorthEast()));
			} catch (e) {
				//alert(e);
			}
			*/
		});
	},
	showMarker:function(lon,lat,label,icon){
		var map = this.getMap();
		var pt = new LMap.Point(lon,lat);
		var marker;
		if(icon){
			var myIcon = new LMap.Icon(icon, new LMap.Size(25,25));
			var marker = new LMap.Marker(pt,{icon:myIcon});  // 创建标注
		}else{
			marker = new LMap.Marker(pt);
		}
		map.addOverlay(marker);
		if(label){
			var lb = new LMap.Label(label,{offset:new LMap.Size(25,-10)});
			marker.setLabel(lb);
		}
		return marker;
	},
	showBounds:function(){
		var bds = [new LMap.Point(116.027143, 39.772348),new LMap.Point(116.832025, 40.126349)];
		this.getMap().setViewport(bds);
	},
	showInfoWindow:function(lan,lat,text){
		var map = this.getMap();
		var point = new LMap.Point(lan,lat);
		//var marker = new LMap.Marker(point);  // 创建标注
		//map.addOverlay(marker);              // 将标注添加到地图中
		map.centerAndZoom(point, 15);
		var opts = {
		  width : 200,     // 信息窗口宽度
		  height: 100,     // 信息窗口高度
		  title : "海底捞王府井店111" , // 信息窗口标题
		  enableMessage:true,//设置允许信息窗发送短息
		  message:"亲耐滴"
		}
		var infoWindow = new LMap.InfoWindow("地址：北京市东城区王府井大街88号乐天银泰百货八层", opts);  // 创建信息窗口对象 
		       
		map.openInfoWindow(infoWindow,point); //开启信息窗口
	},
	getBoundary:function(place){
		var map = this.getMap();
		var bdary = new LMap.Boundary();
		//map.clearOverlays();//清除地图覆盖物     
		bdary.get(place, function(rs){//获取行政区域
			var count = rs.boundaries.length; //行政区域的点有多少个
			if (count === 0) {
				Rx.Msg.alert('提示','未能获取当前输入行政区域');
				return ;
			}
          	var pointArray = [];
			for (var i = 0; i < count; i++) {
				var ply = new LMap.Polygon(rs.boundaries[i], {strokeWeight: 2, strokeColor: "#ff0000"}); //建立多边形覆盖物
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
	//var mapEl = document.getElementById('bmap');
	if(!window.L){
		window.initMap = function(){
			Rx.map.LMap.prototype.isReady = true;
		}

		Ext.Loader.loadScriptss(['leaflet/leaflet-src.js',
			'leaflet/plugins/wmts/leaflet-tilelayer-wmts.js',
			'leaflet/plugins/mapbox/leaflet-omnivore.min.js',
			'leaflet/plugins/markercluster/dist/leaflet.markercluster-src.js'
			],function(){
            Rx.map.LMap.prototype.isReady = true;
		});
		/*
        Ext.Loader.loadScript({url:'leaflet/leaflet-src.js',onLoad:function(){
                Rx.view.LMap.prototype.isReady = true;
			}});
        */
		/*
		var st = document.createElement("script");
		st.setAttribute("id", "bmap");
		st.setAttribute("type", "text/javascript");
		st.setAttribute("src", "http://api.map.baidu.com/api?v=2.0&ak=mzd5OAsrYaef6p20cG2az8XqLq9gzQdI&callback=initMap");
		document.getElementsByTagName("head")[0].appendChild(st);
		
		*/
	}
});