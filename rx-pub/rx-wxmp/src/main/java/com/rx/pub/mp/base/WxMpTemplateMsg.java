package com.rx.pub.mp.base;

import java.io.Serializable;
import java.util.Map;

/**
 */
public interface WxMpTemplateMsg extends Serializable {


    /**
     * 获取模板ID
     */
    String getTemplateId();
    
    String getOpenId();
    
    String getTemplateUrl();
    
    Map<String, String> getTemplateData();
    Map<String, String> getTemplateDataColor();
    
    String getMiniAppId();
    
    String getMiniPagePath();
    
    boolean getMiniUsePath();

}
