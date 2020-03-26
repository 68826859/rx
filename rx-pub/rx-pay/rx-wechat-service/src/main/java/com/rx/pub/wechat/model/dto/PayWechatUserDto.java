package com.rx.pub.wechat.model.dto;

import com.rx.base.model.annotation.RxModel;
import com.rx.ext.annotation.ExtClass;
import com.rx.ext.data.Model;
import com.rx.pub.wechat.model.po.PayWechatUserPo;

/**
 * 微信用户信息(PayWechatUser)DTO
 *
 * @author klf
 * @date 2020-01-09 20:09:46
 */
@RxModel(text = "微信用户信息DTO")
@ExtClass(extend = Model.class, alternateClassName = "PayWechatUserDto")
public class PayWechatUserDto extends PayWechatUserPo {
	private static final long serialVersionUID = -92127644369577787L;

}