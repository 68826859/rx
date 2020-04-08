package com.rx.pub.mp.utils;

import java.util.Map;
import com.google.common.collect.Maps;
import com.rx.pub.mp.base.PubWxMpService;

import me.chanjar.weixin.mp.api.WxMpService;

/**
 */
public class WxMpMgr {


    private WxMpMgr() {
    }
   
    
    
    protected static String DefaultAppId = "default-appId"; 
    
    private static Map<String, PubWxMpService> mpServices = Maps.newHashMap();
    
    public static PubWxMpService getPubWxMpService() {
        return getPubWxMpService(DefaultAppId);
    }
    
    
    protected static WxMpService getWxMpService(PubWxMpService pubWxMpService) {
        
    	String appId = null;
    	
    	for(Map.Entry<String, PubWxMpService> entry:mpServices.entrySet()){
    		if(entry.getValue().equals(pubWxMpService)) {
    			appId = entry.getKey();
    			break;
    		}
    	}
    	
    	return WxMpConfiguration.getMpService(appId);
    }
    
    public static PubWxMpService getPubWxMpService(String appId) {
    	
    	PubWxMpService service = mpServices.get(appId);
    	
    	if(service == null) {
    		
    		service = new PubWxMpServiceImpl();
    		
    		mpServices.put(appId, service);
    		
    	}
    	
    	
        return mpServices.get(appId);
    }

}
