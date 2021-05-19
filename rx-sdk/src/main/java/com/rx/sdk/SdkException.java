package com.rx.sdk;

import com.rx.base.result.DataResult;

public class SdkException extends DataResult {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;



	public SdkException() {
	}
	
	public SdkException(int code,String message) {
		super(message);
		this.setCode(code);
	}


}
