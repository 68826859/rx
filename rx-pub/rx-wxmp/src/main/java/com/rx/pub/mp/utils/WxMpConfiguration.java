package com.rx.pub.mp.utils;

import java.util.Map;
import com.google.common.collect.Maps;
import com.rx.pub.mp.base.WxMpAccount;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;

/**
 */
public class WxMpConfiguration {


    private WxMpConfiguration() {
    }

    private static Map<String, WxMpService> mpServices = Maps.newHashMap();

    /*
    private static Map<String, WxPayService> payTypeServices = Maps.newHashMap();


    public static WxPayService getPayService(String node,Integer bizType) {

    	AppAccounts appAccounts = AppAccounts.valueOf(AppAccounts.class, StringUtils.upperCase(node));
    	
    	Assert.notNull(appAccounts, String.format("未找到对应节点[%s]，请核实！", node));
    	
    	BizType bizTypeE = null;
    	if(bizType != null)
    	{
    		bizTypeE = EnumUtil.valueOf(BizType.class,bizType , null);
    	}
    	
    	PublicChatAccounts publicChatAccount = PublicChatAccounts.getPublicChatAccount(appAccounts,bizTypeE);
    	
        WxPayService wxPayService = payTypeServices.get(publicChatAccount.name());

        Assert.notNull(wxPayService, String.format("未找到对应类型[%s]节点[%s]的配置，请核实！", node));
        return wxPayService;
    }*/
    
    protected static WxMpService getMpService(String appId) {
    	if(WxMpMgr.DefaultAppId.equals(appId)) {
    		return mpServices.entrySet().iterator().next().getValue();
    	}else {
    		return mpServices.get(appId);
    	}
    }
    
    
    public static void regMpService(WxMpAccount wxMpAccount) {
    	
    	WxMpServiceImpl service = new WxMpServiceImpl();
    	WxMpInMemoryConfigStorage configStorage = new WxMpInMemoryConfigStorage();
        configStorage.setAppId(wxMpAccount.getAppId());
        configStorage.setSecret(wxMpAccount.getAppSecret());
        configStorage.setToken(wxMpAccount.getToken());
        configStorage.setAesKey(wxMpAccount.getAesKey());
        //configStorage.set("JSON");
        service.setWxMpConfigStorage(configStorage);
        mpServices.put(wxMpAccount.getAppId(), service);
    }

}
