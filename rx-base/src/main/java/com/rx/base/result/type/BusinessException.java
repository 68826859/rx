package com.rx.base.result.type;

import com.rx.base.result.DataResult;
import com.rx.base.result.AlertTypeEnum;

/**
 * 业务异常
 * 
 */
public class BusinessException extends DataResult {

	private static final long serialVersionUID = 1L;

	private final static int DEFAULT_CODE = 599;

	private final static String DEFAULT_MSG = "业务异常";

	public BusinessException() {
		super(DEFAULT_MSG, AlertTypeEnum.无需关闭的错误3);
		this.setCode(DEFAULT_CODE);
	}

	public BusinessException(String msg) {
		super(msg, AlertTypeEnum.无需关闭的错误3);
		this.setCode(DEFAULT_CODE);
	}

	public BusinessException(String alertMsg,AlertTypeEnum alertType) {
		super(alertMsg, alertType);
		this.setCode(DEFAULT_CODE);
	}

	// 分页获取异常调用，打印异常,获取token失败
	public BusinessException(String msg, Exception e) {
		super(msg, AlertTypeEnum.无需关闭的错误3);
		this.setCode(DEFAULT_CODE);
	}

}
