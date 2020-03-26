package com.rx.base.result.type;

import com.rx.base.result.DataResult;
import com.rx.base.result.AlertTypeEnum;

/**
 * 权限异常
 * 
 */
public class ForbiddenException extends DataResult {
	private static final long serialVersionUID = 1L;

	private final static int DEFAULT_CODE = 403;

	private final static String DEFAULT_MSG = "权限异常";

	public ForbiddenException() {
		super(DEFAULT_MSG, AlertTypeEnum.无需关闭的错误3);
		this.setCode(DEFAULT_CODE);
	}

	public ForbiddenException(String msg) {
		super(msg, AlertTypeEnum.无需关闭的错误3);
		this.setCode(DEFAULT_CODE);
	}

}
