package com.rx.pub.pay.controller;

import java.util.Map;

import org.omg.CORBA.SystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.rx.base.result.DataResult;
import com.rx.base.result.type.BusinessException;
import com.rx.ext.annotation.ExtClass;
import com.rx.extrx.spring.SpringProvider;
import com.rx.pub.pay.dto.PayRequestParam;
import com.rx.pub.pay.servface.RxPayService;

@RestController
@RequestMapping("/app/payRequest")
@ExtClass(extend = SpringProvider.class, alternateClassName = "FileController")
public class PayRequestController {
	private static Logger logger = LoggerFactory.getLogger(PayRequestController.class);
	@Autowired
	private RxPayService rxPayService;

	/**
	 * 组装支付请求报文
	 * 
	 * @param payRequestParam
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 */
	@ResponseBody
	@RequestMapping(value = "/getPayParams", method = RequestMethod.POST)
	public DataResult getPayParams(PayRequestParam payRequestParam) throws BusinessException, SystemException {
		try {
			logger.info("orderId---->" + payRequestParam.getOrderID());
			Map<String, Object> retMap = rxPayService.getPayRetMap(payRequestParam);
			if (null == retMap) {
				throw new Exception("系统异常，请稍候再试！");
			}
			return new DataResult(retMap);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			return new DataResult(ex.getMessage(), JSON.toJSONString(payRequestParam));
		}
	}
}
