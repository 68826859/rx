package com.rx.pub.wechat.model.dto;

import com.rx.base.model.annotation.RxModel;
import com.rx.ext.annotation.ExtClass;
import com.rx.ext.data.Model;
import com.rx.pub.wechat.model.po.PayWechatAccountPubPo;

/**
 * 支付信息(PayWechatAccountPub)DTO
 *
 * @author klf
 * @date 2020-01-09 20:09:04
 */
@RxModel(text = "支付信息DTO")
@ExtClass(extend = Model.class, alternateClassName = "PayWechatAccountPubDto")
public class PayWechatAccountPubDto extends PayWechatAccountPubPo {
	private static final long serialVersionUID = -39017654878143686L;

}