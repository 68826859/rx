package com.rx.pub.wechat.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.rx.pub.wechat.enm.PublicChatMsgTemplate;
import com.rx.pub.wechat.model.po.PayWechatAccountPubPo;
import com.rx.pub.wechat.service.PayWechatAccountPubService;
import com.rx.pub.wechat.service.PublicChatService;

import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

/**
 * 
 */
@Component
public class PublicChatServiceImpl implements PublicChatService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private PayWechatAccountPubService payWechatAccountPubService;

	
	private PayWechatAccountPubPo getAccounts(String accountCode, Integer bizType) {
		PayWechatAccountPubPo payWechatAccount = payWechatAccountPubService.getAccountPubByCode(accountCode, bizType);
		return payWechatAccount;
	}
	
	
	@Override
	public String getJsapiTicket(String accountCode, Integer bizType)  throws Exception{
		WxMpService wxService = getWxMpService(accountCode, bizType);
		return wxService.getJsapiTicket();
	}
	
	@Override
	public Map<String, Object> getUserInfo(String accountCode, String code, Integer bizType) throws Exception  {
		WxMpService wxService = getWxMpService(accountCode, bizType);
		
		WxMpOAuth2AccessToken token = wxService.oauth2getAccessToken(code);
        WxMpUser userInfo = wxService.oauth2getUserInfo(token,  "zh_CN");
        
        String resultStr = JSON.toJSONString(userInfo);
        logger.info("WxMpOAuth2userInfo="+resultStr);
        
        return JSON.parseObject(resultStr, Map.class);

	}

	@Override
	public Map<String, Object> oauth2getAccessToken(String code, String accountCode, Integer bizType) throws Exception {
		WxMpService wxService = getWxMpService(accountCode, bizType);
		
        WxMpOAuth2AccessToken result = wxService.oauth2getAccessToken(accountCode);
        
        String resultStr = JSON.toJSONString(result);
        logger.info("WxMpOAuth2AccessToken="+resultStr);
        
        return JSON.parseObject(resultStr, Map.class);
        
		/*
		String uri = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + miniChatAccounts.getAppid()
				+ "&secret=" + miniChatAccounts.getSecret() + "&code=" + code + "&grant_type=authorization_code";
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(URI.create(uri));
		HttpResponse response = client.execute(get);
		if (response.getStatusLine().getStatusCode() == 200) {
			HttpEntity entity = response.getEntity();
			BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
			StringBuilder sb = new StringBuilder();
			for (String temp = reader.readLine(); temp != null; temp = reader.readLine()) {
				sb.append(temp);
			}
			//JSONObject object = JSONObject.parseObject(sb.toString().trim());
			return JSON.parseObject(sb.toString(), Map.class);
		}
		
		*/
	}

	@Override
	public String getAppId(String acountCode, Integer bizType) throws Exception {
		return getAccounts(acountCode, bizType).getAppId();
	}

	@Override
	public WxMpService getWxMpService(String accountCode, Integer bizType) {
		PayWechatAccountPubPo publicChatAccount = getAccounts(accountCode, bizType);
		WxMpService wxService = payWechatAccountPubService.getMpService(publicChatAccount.getId());
		return wxService;
	}


	@Override
	public void sendTemplateMsg(PublicChatMsgTemplate template, String openid, Map<String, String> urlData,
			Map<String, String> wxMpTemplateData) {
		// TODO Auto-generated method stub
		
	}

}
