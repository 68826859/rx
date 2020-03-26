package com.rx.pub.wechat.enm;
public enum PublicChatMsgTemplate {
    有订单("", "");
    private String templateId;
    private String templateUrl;
    
    PublicChatMsgTemplate(String templateId, String templateUrl) {
        this.templateId = templateId;
        this.templateUrl = templateUrl;
    }
    
	public String getTemplateId() {
		return templateId;
	}

	public String getTemplateUrl() {
		return templateUrl;
	}
}
