package com.rx.pub.wechat.vo.paywechataccount;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
import com.rx.base.enm.RxDatePattern;
import com.rx.base.enm.StatusEnum;
import com.rx.base.model.annotation.RxModelField;
import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtFormAction;
import com.rx.ext.annotation.ExtFormField;
import com.rx.ext.form.field.Hidden;
import com.rx.extrx.spring.SpringAction;
import com.rx.extrx.widget.ActionForm;
import com.rx.pub.wechat.controller.PayWechatAccountController;

import io.swagger.annotations.ApiModelProperty;

/**
 * 修改商户主体(PayWechatAccount)
 *
 * @author klf
 * @since 2020-01-09 20:08:50
 */
@ExtClass(extend = ActionForm.class, alternateClassName = "PayWechatAccountUpdateVo")
public class PayWechatAccountUpdateVo {

	@ExtFormAction
	SpringAction submitAction = new SpringAction(PayWechatAccountController.class, "updatePayWechatAccount");

	@ApiModelProperty(value = "")
	@NotBlank(message = "不能为空")
	@ExtFormField(label = "", allowBlank = false, comp = Hidden.class)
	private String id;

	@ApiModelProperty(value = "编码")
	@ExtFormField(label = "编码*", allowBlank = false)
	@NotBlank(message = "编码不能为空")
	private String accountCode;

	@ApiModelProperty(value = "微信开放平台appid")
	@ExtFormField(label = "微信开放平台appid*", allowBlank = false)
	@NotBlank(message = "微信开放平台appid不能为空")
	private String appId;

	@ApiModelProperty(value = "公众平台商户号")
	@ExtFormField(label = "公众平台商户号*", allowBlank = false)
	@NotBlank(message = "公众平台商户号不能为空")
	private String mchId;

	@ApiModelProperty(value = "商户密钥")
	@ExtFormField(label = "商户密钥*", allowBlank = false)
	@NotBlank(message = "商户密钥不能为空")
	private String apiKey;

	@ApiModelProperty(value = "商户证书")
	@ExtFormField(label = "商户证书*", allowBlank = false)
	@NotBlank(message = "商户证书不能为空")
	private String keyPath;

	@ApiModelProperty(value = "状态")
	@ExtFormField(label = "状态*", em = StatusEnum.class, allowBlank = false)
	@NotNull(message = "状态不能为空")
	private Integer status;

	@ApiModelProperty(value = "")
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@RxModelField(text = "")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExtFormField(label = "*", datePattern = RxDatePattern.ISO8601Long, allowBlank = false)
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