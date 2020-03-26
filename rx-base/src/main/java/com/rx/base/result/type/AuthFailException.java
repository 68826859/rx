package com.rx.base.result.type;

import com.rx.base.result.DataResult;
import com.rx.base.result.AlertTypeEnum;

/**
 * 实名认证失败
 * 
 */
public class AuthFailException extends DataResult {
	private static final long serialVersionUID = 1L;

	private final static int DEFAULT_CODE = 395;
	
	private final static String DEFAULT_MSG = "实名认证审核不通过，请修改信息";

	public AuthFailException() {
		super(DEFAULT_MSG,AlertTypeEnum.无需关闭的错误3);
		this.setCode(DEFAULT_CODE);
	}
	public AuthFailException(String msg) {
		super(msg,AlertTypeEnum.无需关闭的错误3);
		this.setCode(DEFAULT_CODE);
	}
	public AuthFailException(Object obj) {
		super(DEFAULT_MSG,AlertTypeEnum.无需关闭的错误3);
		this.setCode(DEFAULT_CODE);
		this.setData(obj);
	}
	}
