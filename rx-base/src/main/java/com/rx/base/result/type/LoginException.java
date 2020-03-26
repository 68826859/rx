package com.rx.base.result.type;

import com.rx.base.result.DataResult;
import com.rx.base.result.AlertTypeEnum;

/**
 * 登录异常
 * 
 */
public class LoginException extends DataResult {

	private static final long serialVersionUID = 1L;

	private final static int DEFAULT_CODE = 399;

	private final static String DEFAULT_MSG = "登录异常";

	public LoginException() {
		super(DEFAULT_MSG, AlertTypeEnum.无需关闭的错误3);
		this.setCode(DEFAULT_CODE);
	}

	public LoginException(String alertMsg) {
		super(alertMsg, AlertTypeEnum.无需关闭的错误3);
		this.setCode(DEFAULT_CODE);
	}

	public LoginException(String alertMsg, AlertTypeEnum alertType) {
		super(alertMsg, alertType);
		this.setCode(DEFAULT_CODE);
	}
}
