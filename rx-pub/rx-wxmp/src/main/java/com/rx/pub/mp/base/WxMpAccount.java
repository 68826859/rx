package com.rx.pub.mp.base;

import java.io.Serializable;

/**
 */
public interface WxMpAccount extends Serializable {


    /**
     */
    String getAppId();
    
    String getAppSecret();
    
    String getToken();
    
    String getAesKey();

}
