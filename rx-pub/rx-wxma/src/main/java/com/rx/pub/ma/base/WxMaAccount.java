package com.rx.pub.ma.base;

import java.io.Serializable;

/**
 */
public interface WxMaAccount extends Serializable {


    /**
     */
    String getAppId();
    
    String getAppSecret();
    
    String getToken();
    
    String getAesKey();

}
