package com.rx.pub.wechat.model.po;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;
import com.rx.base.enm.RxDatePattern;
import com.rx.base.enm.StatusEnum;
import com.rx.base.model.annotation.RxModel;
import com.rx.base.model.annotation.RxModelField;
import com.rx.base.utils.StringUtil;
import com.rx.ext.annotation.ExtClass;
import com.rx.ext.data.Model;

/**
 * 支付信息(PayWechatAccountPub)实体类
 *
 * @author klf
 * @date 2020-01-09 20:09:04
 */
@RxModel(text = "支付信息")
@Table(name = "pay_wechat_account_pub")
@ExtClass(extend = Model.class, alternateClassName = "PayWechatAccountPubPo")
public class PayWechatAccountPubPo implements Serializable {
	private static final long serialVersionUID = -69700780929336409L;

	@Id
	@RxModelField(text = "", isID = true)
	@Column(name = "id")
	private String id;

	@Column(name = "account_code")
	@RxModelField(text = "编码")
	private String accountCode;

	@Column(name = "app_id")
	@RxModelField(text = "")
	private String appId;

	@Column(name = "secret")
	@RxModelField(text = "")
	private String secret;

	@Column(name = "biz_type")
	@RxModelField(text = "支付业务")
	private Integer bizType;

	@Column(name = "account_id")
	@RxModelField(text = "支付主体id")
	private String accountId;

	@Column(name = "status")
	@RxModelField(text = "状态", em = StatusEnum.class)
	private Integer status;

	@Column(name = "create_time")
	@RxModelField(text = "", datePattern = RxDatePattern.ISO8601Long)
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	public PayWechatAccountPubPo() {
	}

	public PayWechatAccountPubPo(String id) {
		if (id == null) {
			id = StringUtil.getUUIDPure();
		}
		this.id = id;
	}

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
