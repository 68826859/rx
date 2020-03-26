package com.rx.pub.wechat.model.seo;

import com.rx.base.model.annotation.RxModel;
import com.rx.ext.annotation.ExtClass;
import com.rx.ext.data.Model;
import com.rx.pub.wechat.model.po.PayWechatUserPo;

@RxModel(text = "微信用户信息查询参数")
@ExtClass(extend = Model.class, alternateClassName = "PayWechatUserSearchDto")
public class PayWechatUserSearchDto extends PayWechatUserPo {
	private static final long serialVersionUID = 379735613695755603L;

}