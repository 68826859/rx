package com.rx.base.result.type;

import com.rx.base.result.DataResult;
import com.rx.base.result.AlertTypeEnum;

/**
 * 服务器异常（启动未初始化）
 * 
 */
public class ServerException extends DataResult {
	private static final long serialVersionUID = 1L;

	private final static int DEFAULT_CODE = 397;

	private final static String DEFAULT_MSG = "服务器异常";

	public ServerException() {
		super(DEFAULT_MSG, AlertTypeEnum.无需关闭的错误3);
		this.setCode(DEFAULT_CODE);
	}

	public ServerException(String msg) {
		super(msg, AlertTypeEnum.无需关闭的错误3);
		this.setCode(DEFAULT_CODE);
	}
}
