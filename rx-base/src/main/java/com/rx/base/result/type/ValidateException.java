package com.rx.base.result.type;

import com.rx.base.result.DataResult;
import com.rx.base.result.AlertTypeEnum;

/**
 * 验证异常
 * 
 */
public class ValidateException extends DataResult {

	private static final long serialVersionUID = 1L;

	private final static int DEFAULT_CODE = 499;

	private final static String DEFAULT_MSG = "验证异常";

	public ValidateException() {
		super(DEFAULT_MSG, AlertTypeEnum.无需关闭的错误3);
		this.setCode(DEFAULT_CODE);
	}

	public ValidateException(String msg) {
		super(msg, AlertTypeEnum.无需关闭的错误3);
		this.setCode(DEFAULT_CODE);
	}

}
