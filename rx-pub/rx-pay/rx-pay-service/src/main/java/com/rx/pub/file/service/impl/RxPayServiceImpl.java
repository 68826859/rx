package com.rx.pub.file.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.omg.CORBA.SystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.rx.base.result.type.BusinessException;
import com.rx.base.utils.StringUtil;
import com.rx.pub.file.utils.StrategyContext;
import com.rx.pub.mybatis.impls.MybatisBaseService;
import com.rx.pub.pay.dto.PayRequestParam;
import com.rx.pub.pay.enm.PayType;
import com.rx.pub.pay.servface.RxPayService;

@Service
public class RxPayServiceImpl extends MybatisBaseService<PayRequestParam> implements RxPayService {
	private static Logger logger = LoggerFactory.getLogger(RxPayServiceImpl.class);

	@Override
	public Map<String, Object> getPayRetMap(PayRequestParam payRequestParam) throws BusinessException, SystemException {
		Map<String, Object> retMap = assembleRetMap(payRequestParam);
        return retMap;
	}

	
	private Map<String, Object> assembleRetMap(PayRequestParam payRequestParam) {
//        Map<String, Object> paramsToPass = new HashMap<>();
//        if (!StringUtil.isNull(payRequestParam.getRetUrl())) {
//            paramsToPass.put("retUrl", payRequestParam.getRetUrl());
//        }
//        //todo: 2018-12-4 修改 定单表 获取定单总金额
//        String payCode;
//        if (payRequestParam.getOrderCode().length() > 32) {
//            payCode = payRequestParam.getOrderID();
//        } else {
//            payCode = payRequestParam.getOrderCode();
//        }
//        Assert.hasText(payCode, "空订单");
//        Integer price = payMapMapper.getOrderTotalPrice(payCode, payRequestParam.getPayCode());//order_code 业务订单编号   id
//        Integer bizType = payMapMapper.getOrderBizType(payCode, payRequestParam.getPayCode());
//        Assert.notNull(price, "没有此订单");
//        payRequestParam.setToPay(new BigDecimal(price));
//        paramsToPass.put("toPay", payRequestParam.getToPay());
//        paramsToPass.put("payCode", payRequestParam.getPayCode());
//        paramsToPass.put("sellerCode", payRequestParam.getSellerCode());
//        paramsToPass.put("sellerName", payRequestParam.getSellerName());
//        paramsToPass.put("orderCode", payRequestParam.getOrderCode());
//        paramsToPass.put("invalidTime", payRequestParam.getInvalidTime());
//        paramsToPass.put("orderCreateTime", payRequestParam.getCreateTime());
//        paramsToPass.put("openId", payRequestParam.getOpenId());
//        paramsToPass.put("bizType", bizType);
//        PayType payType = dealPayType(payRequestParam.getOnLineStyle(), payRequestParam.getBrowseType());
//        StrategyContext context = new StrategyContext();
//        String payRequsetMsg = context.generatePayParams(payType, paramsToPass);
//        if (payRequsetMsg == null) {
//            return null;
//        }
//        if (logger.isDebugEnabled()) {
//            logger.debug("订单code为{}的支付请求参数生成信息：{}", new Object[]{payRequestParam.getOrderCode(), payRequsetMsg});
//        }
//        savePayRecord(payRequsetMsg, payType, payRequestParam);
        Map<String, Object> retMap = new HashMap<>();
//        retMap.put("payData", payRequsetMsg);
        retMap.put("payment", payRequestParam.getOnLineStyle());
        retMap.put("orderID", payRequestParam.getOrderID());
        retMap.put("errorCode", "0710");
        retMap.put("message", "请去第三方平台支付~~");
        return retMap;
    }
}
