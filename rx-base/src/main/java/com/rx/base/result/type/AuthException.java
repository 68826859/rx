package com.rx.base.result.type;

import com.rx.base.result.DataResult;
import com.rx.base.result.AlertTypeEnum;

/**
 * 实名认证异常
 * 
 */
public class AuthException extends DataResult {
	private static final long serialVersionUID = 1L;

	private final static int DEFAULT_CODE = 398;
	
	private final static String DEFAULT_MSG = "实名认证异常，请绑定邮箱";

	public AuthException() {
		super(DEFAULT_MSG, AlertTypeEnum.无需关闭的错误3);
		this.setCode(DEFAULT_CODE);
	}
	
	public AuthException(String msg, Object data) {
		this(msg);
		this.setData(data);
	}
	
	public AuthException(String msg) {
		super(msg, AlertTypeEnum.无需关闭的错误3);
		this.setCode(DEFAULT_CODE);
	}
}
