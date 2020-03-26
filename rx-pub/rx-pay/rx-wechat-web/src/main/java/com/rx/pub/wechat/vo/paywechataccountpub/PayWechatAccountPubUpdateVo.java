package com.rx.pub.wechat.vo.paywechataccountpub;

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
import com.rx.pub.wechat.controller.PayWechatAccountPubController;

import io.swagger.annotations.ApiModelProperty;

/**
 * 修改支付信息(PayWechatAccountPub)
 *
 * @author klf
 * @since 2020-01-09 20:09:04
 */
@ExtClass(extend = ActionForm.class, alternateClassName = "PayWechatAccountPubUpdateVo")
public class PayWechatAccountPubUpdateVo {

	@ExtFormAction
	SpringAction submitAction = new SpringAction(PayWechatAccountPubController.class, "updatePayWechatAccountPub");

	@ApiModelProperty(value = "")
	@NotBlank(message = "不能为空")
	@ExtFormField(label = "", allowBlank = false, comp = Hidden.class)
	private String id;

	@ApiModelProperty(value = "编码")
	@ExtFormField(label = "编码*", allowBlank = false)
	@NotBlank(message = "编码不能为空")
	private String accountCode;

	@ApiModelProperty(value = "")
	@ExtFormField(label = "*", allowBlank = false)
	@NotBlank(message = "不能为空")
	private String appId;

	@ApiModelProperty(value = "")
	@ExtFormField(label = "*", allowBlank = false)
	@NotBlank(message = "不能为空")
	private String secret;

	@ApiModelProperty(value = "支付业务")
	@ExtFormField(label = "支付业务*", comp = com.rx.ext.form.field.Number.class, allowBlank = false)
	@NotNull(message = "支付业务不能为空")
	private Integer bizType;

	@ApiModelProperty(value = "支付主体id")
	@ExtFormField(label = "支付主体id*", allowBlank = false)
	@NotBlank(message = "支付主体id不能为空")
	private String accountId;

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