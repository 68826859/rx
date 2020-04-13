package com.rx.pub.mp.utils;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSON;
import com.rx.pub.mp.base.PubWxMpService;
import com.rx.pub.mp.base.WxMpTemplateMsg;
import com.rx.pub.mp.utils.WxMpMgr;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage.WxMpTemplateMessageBuilder;

/**
 * 
 */

public class PubWxMpServiceImpl implements PubWxMpService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	
	@Override
    public WxMpService getWxMpService() {
		return WxMpMgr.getWxMpService(this);
    }
	
	
	@Override
	public String getJsapiTicket()  throws Exception{
		return getWxMpService().getJsapiTicket();
	}
	
	
	@Override
	public Map<String, Object> getUserInfo(String code) throws Exception  {
		WxMpService wxService = getWxMpService();
		
		
		
		WxMpOAuth2AccessToken token = wxService.oauth2getAccessToken(code);
		
         WxMpUser userInfo = wxService.oauth2getUserInfo(token,  "zh_CN");
        
        String resultStr = JSON.toJSONString(userInfo);
        logger.info("WxMpOAuth2userInfo="+resultStr);
        
        return JSON.parseObject(resultStr, Map.class);

	}


	@Override
	public Map<String, Object> oauth2getAccessToken(String code) throws Exception {
		WxMpService wxService = getWxMpService();
        WxMpOAuth2AccessToken result = wxService.oauth2getAccessToken(code);
        
        String resultStr = JSON.toJSONString(result);
        logger.info("WxMpOAuth2AccessToken="+resultStr);
        
        return JSON.parseObject(resultStr, Map.class);
	}



	@Override
	public String getAppId() throws Exception {
		return null;
		//return getAccounts(bizType).getAppid();
	}
	
	
	@Override
	public void sendMpTemplateMessage(WxMpTemplateMsg msg) {
		WxMpTemplateMessageBuilder builder = WxMpTemplateMessage.builder().toUser(msg.getOpenId()) // 推送用户
				.templateId(msg.getTemplateId()); // 发送的消息模板ID
		if (msg.getTemplateUrl() != null) {
			String urlString = msg.getTemplateUrl();

			/*
			if (urlData != null) {
				for (Map.Entry<String, String> entry : urlData.entrySet()) {
					urlString = urlString.replace(entry.getKey(), entry.getValue());
				}
			}
			*/
			builder = builder.url(urlString);

			// builder = builder.url(template.getTemplateUrl().replace("DetailsId",
			// String.valueOf(detailsId)).replace("openId", openid)); //点击跳转路径
		}
		WxMpTemplateMessage templateMessage = builder.build();
		for (Map.Entry<String, String> entry : msg.mpTemplateData().entrySet()) {
			templateMessage.addData(new WxMpTemplateData(entry.getKey(), entry.getValue(), "#999"));
		}
		try {
			getWxMpService().getTemplateMsgService().sendTemplateMsg(templateMessage);
		} catch (Exception e) {
			logger.error("模板消息发送失败");
			e.printStackTrace();
		} finally {
			// 处理发送记录
		}
	}

	
	

}
