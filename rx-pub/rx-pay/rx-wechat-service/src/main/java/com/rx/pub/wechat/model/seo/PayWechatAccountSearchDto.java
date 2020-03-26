package com.rx.pub.wechat.model.seo;

import com.rx.base.model.annotation.RxModel;
import com.rx.ext.annotation.ExtClass;
import com.rx.ext.data.Model;
import com.rx.pub.wechat.model.po.PayWechatAccountPo;

@RxModel(text = "商户主体查询参数")
@ExtClass(extend = Model.class, alternateClassName = "PayWechatAccountSearchDto")
public class PayWechatAccountSearchDto extends PayWechatAccountPo {
	private static final long serialVersionUID = 767600387444075307L;

}