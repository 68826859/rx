package com.rx.base.result.type;

import com.rx.base.result.DataResult;
import com.rx.base.result.AlertTypeEnum;

/**
 * 未注册
 * 
 */
public class RegisterException extends DataResult {
	private static final long serialVersionUID = 1L;

	private final static int DEFAULT_CODE = 394;

	private final static String DEFAULT_MSG = "请先注册";

	public RegisterException() {
		super(DEFAULT_MSG, AlertTypeEnum.无需关闭的错误3);
		this.setCode(DEFAULT_CODE);
	}

	public RegisterException(String msg) {
		super(msg, AlertTypeEnum.无需关闭的错误3);
		this.setCode(DEFAULT_CODE);
	}
}
