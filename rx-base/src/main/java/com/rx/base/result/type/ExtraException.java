package com.rx.base.result.type;

import com.rx.base.result.DataResult;
import com.rx.base.result.AlertTypeEnum;

/**
 * 未定义异常
 * 
 */
public class ExtraException extends DataResult {

	private static final long serialVersionUID = 1L;

	private final static int DEFAULT_CODE = 999;

	private final static String DEFAULT_MSG = "请求失败,请稍后再试";

	public ExtraException() {
		super(DEFAULT_MSG, AlertTypeEnum.无需关闭的错误3);
		this.setCode(DEFAULT_CODE);
	}

	public ExtraException(String alertMsg) {
		super(alertMsg, AlertTypeEnum.无需关闭的错误3);
		this.setCode(DEFAULT_CODE);
	}

	public ExtraException(String alertMsg, Exception e) {
		super(alertMsg, e);
		this.setAlertTypeEnum(AlertTypeEnum.无需关闭的错误3);
	}

}
