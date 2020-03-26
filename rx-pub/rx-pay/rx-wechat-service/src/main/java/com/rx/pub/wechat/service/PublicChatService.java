package com.rx.pub.wechat.service;

import java.util.Map;

import com.rx.pub.wechat.enm.PublicChatMsgTemplate;

import me.chanjar.weixin.mp.api.WxMpService;

/**
 */
public interface PublicChatService {

	/**
	 * 微信公众号获取AccessToken
	 */
	Map<String, Object> oauth2getAccessToken(String accountCode, String code, Integer bizType) throws Exception;

	/**
	 * 微信公众号获取 用户信息
	 */
	Map<String, Object> getUserInfo(String accountCode, String code, Integer bizType) throws Exception;

	/**
	 * 获取js授权apiTicket
	 * 
	 * @return
	 */
	String getJsapiTicket(String accountCode, Integer bizType) throws Exception;

	/**
	 * 获取appId
	 * 
	 * @return
	 */
	String getAppId(String accountCode, Integer bizType) throws Exception;

	WxMpService getWxMpService(String accountCode, Integer bizType);

	/**
	 * 发送模板消息
	 * 
	 * @param template
	 * @param openid
	 * @param urlData
	 * @param wxMpTemplateData
	 */
	void sendTemplateMsg(PublicChatMsgTemplate template, String openid, Map<String, String> urlData,
			Map<String, String> wxMpTemplateData);

}
