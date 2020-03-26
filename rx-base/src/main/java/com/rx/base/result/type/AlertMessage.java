package com.rx.base.result.type;

import com.rx.base.result.DataResult;
import com.rx.base.result.AlertTypeEnum;

/**
 * 弹出信息
 * 
 */
public class AlertMessage extends DataResult {
	private static final long serialVersionUID = 1L;

	private final static int DEFAULT_CODE = 200;

	public AlertMessage(String msg) {
		super(msg, AlertTypeEnum.无需关闭的提示1);
		this.setCode(DEFAULT_CODE);
	}
}
