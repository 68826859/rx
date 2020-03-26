package com.rx.pub.wechat.vo.paywechatuser;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
import com.rx.base.enm.RxDatePattern;
import com.rx.base.model.annotation.RxModelField;
import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtFormAction;
import com.rx.ext.annotation.ExtFormField;
import com.rx.extrx.spring.SpringAction;
import com.rx.extrx.widget.ActionForm;
import com.rx.pub.wechat.controller.WeChatController;

import io.swagger.annotations.ApiModelProperty;

/**
 * 新增微信用户信息(PayWechatUser)
 *
 * @author klf
 * @since 2020-01-09 20:09:45
 */
@ExtClass(extend = ActionForm.class, alternateClassName = "PayWechatUserAddVo")
public class PayWechatUserAddVo {

	@ExtFormAction
	SpringAction submitAction = new SpringAction(WeChatController.class, "addPayWechatUser");

	@ApiModelProperty(value = "用户的标识，对当前公众号唯一")
	@ExtFormField(label = "用户的标识，对当前公众号唯一*", allowBlank = false)
	@NotBlank(message = "用户的标识，对当前公众号唯一不能为空")
	private String openid;

	@ApiModelProperty(value = "系统用户ID")
	@ExtFormField(label = "系统用户ID*", allowBlank = false)
	@NotBlank(message = "系统用户ID不能为空")
	private String userid;

	@ApiModelProperty(value = "用户的昵称")
	@ExtFormField(label = "用户的昵称*", allowBlank = false)
	@NotBlank(message = "用户的昵称不能为空")
	private String nickname;

	@ApiModelProperty(value = "用户的性别，值为1时是男性，值为2时是女性，值为0时是未知")
	@ExtFormField(label = "用户的性别，值为1时是男性，值为2时是女性，值为0时是未知*", comp = com.rx.ext.form.field.Number.class, allowBlank = false)
	@NotNull(message = "用户的性别，值为1时是男性，值为2时是女性，值为0时是未知不能为空")
	private Integer sex;

	@ApiModelProperty(value = "用户所在城市")
	@ExtFormField(label = "用户所在城市*", allowBlank = false)
	@NotBlank(message = "用户所在城市不能为空")
	private String city;

	@ApiModelProperty(value = "用户所在国家")
	@ExtFormField(label = "用户所在国家*", allowBlank = false)
	@NotBlank(message = "用户所在国家不能为空")
	private String country;

	@ApiModelProperty(value = "用户所在省份")
	@ExtFormField(label = "用户所在省份*", allowBlank = false)
	@NotBlank(message = "用户所在省份不能为空")
	private String province;

	@ApiModelProperty(value = "用户的语言，简体中文为zh_CN")
	@ExtFormField(label = "用户的语言，简体中文为zh_CN*", allowBlank = false)
	@NotBlank(message = "用户的语言，简体中文为zh_CN不能为空")
	private String languages;

	@ApiModelProperty(value = "用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。")
	@ExtFormField(label = "用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。*", allowBlank = false)
	@NotBlank(message = "用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。不能为空")
	private String subscribe;

	@ApiModelProperty(value = "用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间")
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@RxModelField(text = "用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExtFormField(label = "用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间", datePattern = RxDatePattern.ISO8601Long, allowBlank = false)
	private Date subscribetime;

	@ApiModelProperty(value = "只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。")
	@ExtFormField(label = "只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。*", allowBlank = false)
	@NotBlank(message = "只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。不能为空")
	private String unionid;

	@ApiModelProperty(value = "公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注")
	@ExtFormField(label = "公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注*", allowBlank = false)
	@NotBlank(message = "公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注不能为空")
	private String remark;

	@ApiModelProperty(value = "用户所在的分组ID（兼容旧的用户分组接口）")
	@ExtFormField(label = "用户所在的分组ID（兼容旧的用户分组接口）*", allowBlank = false)
	@NotBlank(message = "用户所在的分组ID（兼容旧的用户分组接口）不能为空")
	private String groupid;

	@ApiModelProperty(value = "用户被打上的标签ID列表")
	@ExtFormField(label = "用户被打上的标签ID列表*", allowBlank = false)
	@NotBlank(message = "用户被打上的标签ID列表不能为空")
	private String tagidlist;

	@ApiModelProperty(value = "创建时间")
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@RxModelField(text = "创建时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExtFormField(label = "创建时间", datePattern = RxDatePattern.ISO8601Long, allowBlank = false)
	private Date createtime;

	@ApiModelProperty(value = "绑定状态 0-未绑定 1-已绑定 2-已解绑")
	@ExtFormField(label = "绑定状态 0-未绑定 1-已绑定 2-已解绑*", comp = com.rx.ext.form.field.Number.class, allowBlank = false)
	@NotNull(message = "绑定状态 0-未绑定 1-已绑定 2-已解绑不能为空")
	private Integer bindstatus;

	@ApiModelProperty(value = "解绑时间")
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@RxModelField(text = "解绑时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExtFormField(label = "解绑时间", datePattern = RxDatePattern.ISO8601Long, allowBlank = false)
	private Date unbindtime;

	@ApiModelProperty(value = "微信用户头像")
	@ExtFormField(label = "微信用户头像*", allowBlank = false)
	@NotBlank(message = "微信用户头像不能为空")
	private String headimgurl;

	@ApiModelProperty(value = "临时验证码")
	@ExtFormField(label = "临时验证码*", allowBlank = false)
	@NotBlank(message = "临时验证码不能为空")
	private String opd;

	@ApiModelProperty(value = "商户编码")
	@ExtFormField(label = "商户编码*", allowBlank = false)
	@NotBlank(message = "商户编码不能为空")
	private String accountCode;

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getLanguages() {
		return languages;
	}

	public void setLanguages(String languages) {
		this.languages = languages;
	}

	public String getSubscribe() {
		return subscribe;
	}

	public void setSubscribe(String subscribe) {
		this.subscribe = subscribe;
	}

	public Date getSubscribetime() {
		return subscribetime;
	}

	public void setSubscribetime(Date subscribetime) {
		this.subscribetime = subscribetime;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getGroupid() {
		return groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

	public String getTagidlist() {
		return tagidlist;
	}

	public void setTagidlist(String tagidlist) {
		this.tagidlist = tagidlist;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Integer getBindstatus() {
		return bindstatus;
	}

	public void setBindstatus(Integer bindstatus) {
		this.bindstatus = bindstatus;
	}

	public Date getUnbindtime() {
		return unbindtime;
	}

	public void setUnbindtime(Date unbindtime) {
		this.unbindtime = unbindtime;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public String getOpd() {
		return opd;
	}

	public void setOpd(String opd) {
		this.opd = opd;
	}

	public String getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

}