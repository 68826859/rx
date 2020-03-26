package com.rx.pub.pay.servface;

import java.util.Map;

import org.omg.CORBA.SystemException;

import com.rx.base.result.type.BusinessException;
import com.rx.base.service.BaseService;
import com.rx.pub.pay.dto.PayRequestParam;

/**
 *
 **/
public interface RxPayService extends BaseService<PayRequestParam> {

	public Map<String, Object> getPayRetMap(PayRequestParam payRequestParam) throws BusinessException, SystemException;

}
