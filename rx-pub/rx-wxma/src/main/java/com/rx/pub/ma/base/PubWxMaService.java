package com.rx.pub.ma.base;


import java.util.Map;

import cn.binarywang.wx.miniapp.api.WxMaService;

/**
 */
public interface PubWxMaService {


	public WxMaService getWxMaService();
	
    /**
     * 小程序登陆
     */
    Map<String, Object> jsCode2SessionInfo(String code);


    /**
     * 用户信息
     */
    Map<String, Object> getUserInfo(String sessionKey, String encryptedData, String ivStr);

    /**
     * 用户手机信息
     */
    Map<String, Object> getPhoneNoInfo(String sessionKey, String encryptedData, String ivStr);

    /**
     * 发送小程序消息
     *
     * @param formId 小程序表单Id 或 小程支付id
     */
    void sendMiniMessages(WxMaTemplateMsg templateType, String formId, String openid, String[] keywords);
    
    


}
