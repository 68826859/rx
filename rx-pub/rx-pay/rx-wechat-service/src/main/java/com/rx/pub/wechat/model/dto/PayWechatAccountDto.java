package com.rx.pub.wechat.model.dto;

import com.rx.base.model.annotation.RxModel;
import com.rx.ext.annotation.ExtClass;
import com.rx.ext.data.Model;
import com.rx.pub.wechat.model.po.PayWechatAccountPo;

/**
 * 商户主体(PayWechatAccount)DTO
 *
 * @author klf
 * @date 2020-01-09 20:08:51
 */
@RxModel(text = "商户主体DTO")
@ExtClass(extend = Model.class, alternateClassName = "PayWechatAccountDto")
public class PayWechatAccountDto extends PayWechatAccountPo {
	private static final long serialVersionUID = -15681356067822253L;

}