package com.rx.pub.file.utils;

import java.util.Map;

import com.rx.pub.pay.enm.PayType;
import com.rx.pub.pay.servface.PayStrategy;
import com.rx.pub.pay.util.StrategyFactory;

/**
 * 支付策略上下文
 * Created by Martin on 2016/7/01.
 */
public class StrategyContext {

    private PayStrategy payStrategy;

    /**
     * 调用对应支付平台组装支付请求报文
     * @param payType
     * @param params
     * @return
     */
    public String generatePayParams(PayType payType, Map<String, Object> params) {
        payStrategy = StrategyFactory.getInstance().creator(payType.value());
        return payStrategy.generatePayParams(payType, params);
    }

}
