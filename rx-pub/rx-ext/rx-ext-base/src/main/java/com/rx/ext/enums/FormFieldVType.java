package com.rx.ext.enums;

public enum FormFieldVType {
	无(""),
	电子邮件("email"),
	链接地址("url"),
	字母下划线("alpha"),
	手机号码("mobilephone"),
	身份证号("idcard"),
	字母数字下划线("alphanum");
	private String vtype;

	FormFieldVType(String vtype) {
		this.vtype = vtype;
	}

	public String getVtype() {
		return this.vtype;
	}
}