package com.rx.pub.ma.utils;

import java.util.Map;
import com.google.common.collect.Maps;
import com.rx.pub.ma.base.PubWxMaService;
import cn.binarywang.wx.miniapp.api.WxMaService;

/**
 */
public class WxMaMgr {


    private WxMaMgr() {
    }
   
    
    
    protected static String DefaultAppId = "default-appId"; 
    
    private static Map<String, PubWxMaService> maServices = Maps.newHashMap();
    
    public static PubWxMaService getPubWxMaService() {
        return getPubWxMaService(DefaultAppId);
    }
    
    
    protected static WxMaService getWxMaService(PubWxMaService pubWxMaService) {
        
    	String appId = null;
    	
    	for(Map.Entry<String, PubWxMaService> entry:maServices.entrySet()){
    		if(entry.getValue().equals(pubWxMaService)) {
    			appId = entry.getKey();
    			break;
    		}
    	}
    	
    	return WxMaConfiguration.getMaService(appId);
    }
    
    public static PubWxMaService getPubWxMaService(String appId) {
    	
    	PubWxMaService service = maServices.get(appId);
    	
    	if(service == null) {
    		
    		service = new PubWxMaServiceImpl();
    		
    		maServices.put(appId, service);
    		
    	}
    	
    	
        return maServices.get(appId);
    }

}
