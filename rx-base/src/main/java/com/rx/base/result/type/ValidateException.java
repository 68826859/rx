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

	
	public static void notNull(Object object, String message) {
		if (object == null) {
			throw new ValidateException(message);
		}
	}
	public static void isNull(Object object, String message) {
		if (object != null) {
			throw new ValidateException(message);
		}
	}
	public static void isTrue(boolean expression, String message) {
		if (!expression) {
			throw new ValidateException(message);
		}
	}
	public static void hasText(String text, String message) {
		if (!hasText(text)) {
			throw new ValidateException(message);
		}
	}
	private static boolean hasText(String str) {
		return (str != null && !str.isEmpty() && containsText(str));
	}

	private static boolean containsText(CharSequence str) {
		int strLen = str.length();
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return true;
			}
		}
		return false;
	}
	
	public ValidateException() {
		super(DEFAULT_MSG, AlertTypeEnum.无需关闭的错误3);
		this.setCode(DEFAULT_CODE);
	}

	public ValidateException(String msg) {
		super(msg, AlertTypeEnum.无需关闭的错误3);
		this.setCode(DEFAULT_CODE);
	}

}
