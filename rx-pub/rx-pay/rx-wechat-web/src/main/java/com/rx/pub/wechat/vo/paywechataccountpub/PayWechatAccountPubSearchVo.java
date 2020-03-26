package com.rx.pub.wechat.vo.paywechataccountpub;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
import com.rx.base.enm.RxDatePattern;
import com.rx.base.enm.StatusEnum;
import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtFormField;
import com.rx.extrx.widget.ParamForm;

import io.swagger.annotations.ApiModelProperty;

@ExtClass(extend = ParamForm.class, alternateClassName = "PayWechatAccountPubSearchVo")
public class PayWechatAccountPubSearchVo {

	@ApiModelProperty(value = "")
	@ExtFormField(label = "")
	private String id;

	@ApiModelProperty(value = "编码")
	@ExtFormField(label = "编码")
	private String accountCode;

	@ApiModelProperty(value = "")
	@ExtFormField(label = "")
	private String appId;

	@ApiModelProperty(value = "")
	@ExtFormField(label = "")
	private String secret;

	@ApiModelProperty(value = "支付业务")
	@ExtFormField(label = "支付业务", comp = com.rx.ext.form.field.Number.class)
	private Integer bizType;

	@ApiModelProperty(value = "支付主体id")
	@ExtFormField(label = "支付主体id")
	private String accountId;

	@ApiModelProperty(value = "状态")
	@ExtFormField(label = "状态", em = StatusEnum.class)
	private Integer status;

	@ApiModelProperty(value = "")
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExtFormField(label = "", datePattern = RxDatePattern.ISO8601Long)
	private Date createTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public Integer getBizType() {
		return bizType;
	}

	public void setBizType(Integer bizType) {
		this.bizType = bizType;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}