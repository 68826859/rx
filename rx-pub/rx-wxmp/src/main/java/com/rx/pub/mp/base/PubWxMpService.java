package com.rx.pub.mp.base;


import java.util.Map;
import me.chanjar.weixin.mp.api.WxMpService;

/**
 */
public interface PubWxMpService {


    /**
     * 微信公众号获取AccessToken
     */
    Map<String, Object> oauth2getAccessToken(String code) throws Exception;


    /**
     *微信公众号获取 用户信息
     */
    Map<String, Object> getUserInfo(String code) throws Exception;

    
    /**
     * 获取js授权apiTicket
     * @return
     */
	String getJsapiTicket() throws Exception;
	
	
	
	/**
     * 获取appId
     * @return
     */
	String getAppId() throws Exception;
	
	
	WxMpService getWxMpService();
	
	
	/**
	 * 发送模板消息
	 * @param template
	 */
	void sendMpTemplateMessage(WxMpTemplateMsg msg) throws Exception;

}
