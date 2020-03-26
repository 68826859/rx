package com.rx.pub.wechat.service;

import com.rx.pub.pay.enm.PayType;
import com.rx.pub.pay.util.StrategyFactory;

public class WeChatPubPayService {

	static{
		StrategyFactory.put(PayType.WECHAT_PUBLIC.value(), new WechatPublicPayStrategy());
	}
}
