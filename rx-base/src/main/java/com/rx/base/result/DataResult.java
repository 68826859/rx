package com.rx.base.result;

import com.rx.base.model.annotation.RxModel;
import com.rx.base.model.annotation.RxModelField;

@RxModel(text="返回结果基类")
public class DataResult extends RuntimeException {

	private static final long serialVersionUID = 1L;
	transient static final String PRODUCT_VERSION = "1.0";

	
	protected String version = PRODUCT_VERSION;// 版本号

	
	
	@RxModelField(text="状态码",em=ResultCodeEnum.class)
	private int code = 200;// 状态码 //200 成功 394未注册 395实名认证不通过 396实名认证审核中 397系统未初始化 398实名认证异常 399未登录 499验证异常 599业务异常 403权限异常 999系统异常

	
	@RxModelField(text="提示消息类型",em=AlertTypeEnum.class)
	private int alertType = 0;// 消息类型 //0不弹框 1:无需关闭的提示,2:需要关闭的提示,3:无需关闭的错误,4:需要关闭的错误

	
	@RxModelField(text="提示消息")
	private String alertMsg;// 消息

	
	
	@RxModelField(text="业务数据")
	private Object data;// 数据
	
	
	private String redirectUrl;// 重定向地址
	
	private Class<?> dataType;

	public DataResult() {
		super();
	}

	public DataResult(Object data) {
		super();
		this.data = data;
	}

	
	public DataResult(String alertMsg, Object data) {
		super();
		this.data = data;
		this.alertMsg = alertMsg;
	}
	
	

	
	public DataResult(String alertMsg) {
		super();
		this.alertMsg = alertMsg;
	}
	

	/*
	public DataResult(String alertMsg, int alertType) {
		super();
		this.alertType = alertType;
		this.alertMsg = alertMsg;
	}
	*/
	
	public DataResult(String alertMsg, AlertTypeEnum alertType) {
		super();
		this.alertType = alertType.getCode().intValue();
		this.alertMsg = alertMsg;
	}

	public DataResult(String message, Throwable cause) {
		super(message, cause);
		this.alertMsg = message;
	}

	public String getVersion() {
		return version;
	}

	public int getCode() {
		return code;
	}

	public DataResult setCode(int code) {
		this.code = code;
		return this;
	}

	public int getAlertType() {
		return alertType;
	}

	/*
	public DataResult setAlertType(int alertType) {
		this.alertType = alertType;
		return this;
	}
	*/
	
	public DataResult setAlertTypeEnum(AlertTypeEnum alertType) {
		this.alertType = alertType.getCode().intValue();
		return this;
	}
	

	public String getAlertMsg() {
		return alertMsg;
	}

	public DataResult setAlertMsg(String alertMsg) {
		this.alertMsg = alertMsg;
		return this;
	}

	public Object getData() {
		return data;
	}

	public DataResult setData(Object data) {
		this.data = data;
		return this;
	}

	public String toXml() {
		return null;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public DataResult setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
		return this;
	}

	public Class<?> getDataType() {
		return dataType;
	}

	public DataResult setDataType(Class<?> dataType) {
		this.dataType = dataType;
		return this;
	}
}
