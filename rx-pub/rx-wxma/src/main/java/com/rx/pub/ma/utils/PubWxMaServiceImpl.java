package com.rx.pub.ma.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSON;
import com.rx.pub.ma.base.PubWxMaService;
import com.rx.pub.ma.base.WxMaTemplateMsg;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaMsgServiceImpl;
import cn.binarywang.wx.miniapp.api.impl.WxMaTemplateServiceImpl;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateData;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateMessage;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import cn.binarywang.wx.miniapp.bean.template.WxMaTemplateListResult;
import me.chanjar.weixin.common.error.WxErrorException;

/**
 * 
 */

public class PubWxMaServiceImpl implements PubWxMaService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public WxMaService getWxMaService() {
		return WxMaMgr.getWxMaService(this);
	}

	@Override
	public Map<String, Object> jsCode2SessionInfo(String code) {
		try {

            Assert.hasText(code, "code not is null");

            WxMaService wxService = getWxMaService();

            WxMaJscode2SessionResult result = wxService.jsCode2SessionInfo(code);
            logger.info("WxMaJscode2SessionResult="+JSON.toJSONString(result));
            return JSON.parseObject(JSON.toJSONString(result), Map.class);

        } catch (WxErrorException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalArgumentException(e.getMessage());
        }
	}

	@Override
	public Map<String, Object> getUserInfo(String sessionKey, String encryptedData, String ivStr) {
		try {
            Assert.hasText(sessionKey, "sessionKey not is null");
            Assert.hasText(encryptedData, "encryptedData not is null");
            Assert.hasText(ivStr, "ivStr not is null");
            WxMaService wxService = getWxMaService();
            WxMaUserInfo userInfo = wxService.getUserService().getUserInfo(sessionKey, encryptedData, ivStr);
            logger.info("userInfo="+JSON.toJSONString(userInfo));
            return JSON.parseObject(JSON.toJSONString(userInfo), Map.class);
        } catch (IllegalStateException i) {
            logger.debug(i.getMessage(), i);
            return JSON.parseObject(JSON.toJSONString(new WxMaUserInfo()), Map.class);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new IllegalArgumentException(e.getMessage());
        }
	}

	@Override
	public Map<String, Object> getPhoneNoInfo(String sessionKey, String encryptedData, String ivStr) {
		try {
            Assert.hasText(sessionKey, "sessionKey not is null");
            Assert.hasText(encryptedData, "encryptedData not is null");
            Assert.hasText(ivStr, "ivStr not is null");
            WxMaService wxService = getWxMaService();

            WxMaPhoneNumberInfo phoneNoInfo = wxService.getUserService().getPhoneNoInfo(sessionKey, encryptedData, ivStr);
            logger.info("phoneNoInfo="+JSON.toJSONString(phoneNoInfo));
            return JSON.parseObject(JSON.toJSONString(phoneNoInfo), Map.class);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new IllegalArgumentException(e.getMessage());
        }
	}

	@Override
	public void sendMiniMessages(WxMaTemplateMsg templateType, String formId, String openid, String[] keywords) {
		try {
            Assert.notNull(templateType, "templateType not is null");
            Assert.hasText(openid, "openid not is null");
            Assert.notEmpty(keywords, "keywords not is null");

            WxMaService wxService = getWxMaService();

            String templateId = null;
            WxMaTemplateListResult list = new WxMaTemplateServiceImpl(wxService).findTemplateList(0, 20);
            for (WxMaTemplateListResult.TemplateInfo info : list.getList()) {
                //if (templateType.getTitle().equalsIgnoreCase(info.getTitle())) {
                    templateId = info.getTemplateId();
                //}
            }
            Assert.notNull(templateId, "未找到消息模板,请确认在小程序里配置了模板");


            int i = 1;
            List<WxMaTemplateData> templateData = new ArrayList<>(keywords.length);


            for (String keyword : keywords) {
                templateData.add(new WxMaTemplateData("keyword" + i++, keyword));
            }

            WxMaTemplateMessage wxMaTemplateMessage =
                    WxMaTemplateMessage.builder()
                            .formId(formId)
                            .templateId(templateId)
                            .toUser(openid)
                            .data(templateData)
                            .build();
            String info = wxMaTemplateMessage.toJson();
            logger.debug("发送小程序消息==>{}", info);

            new WxMaMsgServiceImpl(wxService).sendTemplateMsg(wxMaTemplateMessage);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
	}
	

	

}
