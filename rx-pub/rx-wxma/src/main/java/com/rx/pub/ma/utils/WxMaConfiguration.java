package com.rx.pub.ma.utils;

import java.util.Map;
import com.google.common.collect.Maps;
import com.rx.pub.ma.base.WxMaAccount;
import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.WxMaInMemoryConfig;

/**
 */
public class WxMaConfiguration {


    private WxMaConfiguration() {
    }

    private static Map<String, WxMaService> maServices = Maps.newHashMap();

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
    
    protected static WxMaService getMaService(String appId) {
    	if(WxMaMgr.DefaultAppId.equals(appId)) {
    		return maServices.entrySet().iterator().next().getValue();
    	}else {
    		return maServices.get(appId);
    	}
    }
    
    
    public static void regMaService(WxMaAccount WxMaAccount) {
    	
    	WxMaServiceImpl service = new WxMaServiceImpl();
    	WxMaInMemoryConfig config = new WxMaInMemoryConfig();
        config.setAppid(WxMaAccount.getAppId());
        config.setSecret(WxMaAccount.getAppSecret());
        config.setToken(WxMaAccount.getToken());
        config.setAesKey(WxMaAccount.getAesKey());
        config.setMsgDataFormat("JSON");
        maServices.put(WxMaAccount.getAppId(), service);
    }

}
