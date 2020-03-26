Ext.define('Rx.map.AMap', {
    override:'Rx.map.Map',
    enableScrollWheelZoom:true,//是否允许滚轴放大缩小
    showScaleControl:true,//显示左上角比例尺
    showLeftNavigationControl:true,//显示左上角缩放平移控件
    showRightNavigationControl:false,//显示右上角缩放平移控件
    showOverviewMapControl:true,//显示鹰眼
    showCopyrightControl:true,//显示版权信息
    showGeolocationControl:true,//显示定位控件
    
    showMyDisplayControl:false,//是否显示右上角显示种植模式，土壤信息，气象信息
    
    initMap:function(el){
    	return new AMap.Map(el.dom.id);
    },
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
	showMarker:function(lon,lat,label,icon,offsetX,offsetY){
		var map = this.getMap();
		var marker = new AMap.Marker({
			zIndex:3,
            icon: icon,
            position: [lon,lat],
            offset: new AMap.Pixel(offsetX,offsetY)
        })
        map.add(marker);
		map.setFitView();
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
	if(!window.AMap){
		window.initMap = function(){
			Rx.map.Map.prototype.classReady = true;
		}
		Ext.Loader.loadScriptss(['https://webapi.amap.com/maps?v=1.4.11&key=c04adfb480b7f82bdb321bd97897b9b3&callback=initMap'],function(){
            Rx.map.Map.prototype.classReady = true;
		});
	}
});