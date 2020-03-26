package com.rx.pub.wechat.model.seo;

import com.rx.base.model.annotation.RxModel;
import com.rx.ext.annotation.ExtClass;
import com.rx.ext.data.Model;
import com.rx.pub.wechat.model.po.PayWechatAccountPubPo;

@RxModel(text = "支付信息查询参数")
@ExtClass(extend = Model.class, alternateClassName = "PayWechatAccountPubSearchDto")
public class PayWechatAccountPubSearchDto extends PayWechatAccountPubPo {
	private static final long serialVersionUID = -62587810359351053L;

}