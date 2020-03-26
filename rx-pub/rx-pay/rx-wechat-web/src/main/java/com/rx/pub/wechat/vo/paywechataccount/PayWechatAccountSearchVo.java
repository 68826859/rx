package com.rx.pub.wechat.vo.paywechataccount;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
import com.rx.base.enm.RxDatePattern;
import com.rx.base.enm.StatusEnum;
import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtFormField;
import com.rx.extrx.widget.ParamForm;

import io.swagger.annotations.ApiModelProperty;

@ExtClass(extend = ParamForm.class, alternateClassName = "PayWechatAccountSearchVo")
public class PayWechatAccountSearchVo {

	@ApiModelProperty(value = "")
	@ExtFormField(label = "")
	private String id;

	@ApiModelProperty(value = "编码")
	@ExtFormField(label = "编码")
	private String accountCode;

	@ApiModelProperty(value = "微信开放平台appid")
	@ExtFormField(label = "微信开放平台appid")
	private String appId;

	@ApiModelProperty(value = "公众平台商户号")
	@ExtFormField(label = "公众平台商户号")
	private String mchId;

	@ApiModelProperty(value = "商户密钥")
	@ExtFormField(label = "商户密钥")
	private String apiKey;

	@ApiModelProperty(value = "商户证书")
	@ExtFormField(label = "商户证书")
	private String keyPath;

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

	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getKeyPath() {
		return keyPath;
	}

	public void setKeyPath(String keyPath) {
		this.keyPath = keyPath;
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