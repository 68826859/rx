package com.rx.pub.ma.base;

import java.io.Serializable;
import java.util.Map;

/**
 */
public interface WxMaTemplateMsg extends Serializable {


    /**
     * 获取模板ID
     */
    String getTemplateId();
    
    String getOpenId();
    
    String getTemplateUrl();
    
    Map<String, String> mpTemplateData();

}
