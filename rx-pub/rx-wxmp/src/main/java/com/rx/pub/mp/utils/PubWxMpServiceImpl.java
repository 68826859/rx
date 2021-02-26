package com.rx.pub.mp.utils;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSON;
import com.rx.pub.mp.base.PubWxMpService;
import com.rx.pub.mp.base.WxMpTemplateMsg;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage.MiniProgram;
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
	public void sendMpTemplateMessage(WxMpTemplateMsg msg) throws Exception {
		WxMpTemplateMessageBuilder builder = WxMpTemplateMessage.builder().toUser(msg.getOpenId()) // 推送用户
				.templateId(msg.getTemplateId()); // 发送的消息模板ID
		String url = msg.getTemplateUrl();
		if (url != null) {
			builder = builder.url(url);
		}
		WxMpTemplateMessage templateMessage = builder.build();
		Map<String, String> colors = msg.getTemplateDataColor();
		for (Map.Entry<String, String> entry : msg.getTemplateData().entrySet()) {
			templateMessage.addData(new WxMpTemplateData(entry.getKey(), entry.getValue(), colors==null?null:colors.get(entry.getKey())));
		}
		
		String appId = msg.getMiniAppId();
		if(appId != null) {
			MiniProgram miniProgram = new MiniProgram();
			miniProgram.setAppid(msg.getMiniAppId());
			miniProgram.setPagePath(msg.getMiniPagePath());
			miniProgram.setUsePath(msg.getMiniUsePath());
			templateMessage.setMiniProgram(miniProgram);
		}
		getWxMpService().getTemplateMsgService().sendTemplateMsg(templateMessage);
	}

	
	

}
