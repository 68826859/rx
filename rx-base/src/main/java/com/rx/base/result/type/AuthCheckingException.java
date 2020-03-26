package com.rx.base.result.type;

import com.rx.base.result.DataResult;
import com.rx.base.result.AlertTypeEnum;

/**
 * 实名认证审核中
 * 
 */
public class AuthCheckingException extends DataResult {
	private static final long serialVersionUID = 1L;

	private final static int DEFAULT_CODE = 396;
	private final static String DEFAULT_MSG = "实名认证审核中，请耐心等待";

	public AuthCheckingException() {
		super(DEFAULT_MSG,AlertTypeEnum.无需关闭的错误3);
		this.setCode(DEFAULT_CODE);
	}
	public AuthCheckingException(String msg) {
		super(msg,AlertTypeEnum.无需关闭的错误3);
		this.setCode(DEFAULT_CODE);
	}
	public AuthCheckingException(Object obj) {
		super(DEFAULT_MSG,AlertTypeEnum.无需关闭的错误3);
		this.setCode(DEFAULT_CODE);
		this.setData(obj);
	}
	}
