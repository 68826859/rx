package com.rx.base.user;

import java.io.Serializable;


public interface RxSessionable extends Serializable{
	
    /**
     * 获得唯一标识
     * @return
     */
    public String getId();

    /**
      * 获得用户的显示名称
     * @return
     */
    //public String getName();
}
