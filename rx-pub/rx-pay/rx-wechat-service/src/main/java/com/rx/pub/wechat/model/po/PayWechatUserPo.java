package com.rx.pub.wechat.model.po;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;
import com.rx.base.enm.RxDatePattern;
import com.rx.base.model.annotation.RxModel;
import com.rx.base.model.annotation.RxModelField;
import com.rx.base.utils.StringUtil;
import com.rx.ext.annotation.ExtClass;
import com.rx.ext.data.Model;

/**
 * 微信用户信息(PayWechatUser)实体类
 *
 * @author klf
 * @date 2020-01-09 20:09:44
 */
@RxModel(text = "微信用户信息")
@Table(name = "pay_wechat_user")
@ExtClass(extend = Model.class, alternateClassName = "PayWechatUserPo")
public class PayWechatUserPo implements Serializable {
	private static final long serialVersionUID = 799030411202368334L;

	@Id
	@RxModelField(text = "", isID = true)
	@Column(name = "id")
	private String id;

	@Column(name = "openid")
	@RxModelField(text = "用户的标识，对当前公众号唯一")
	private String openid;

	@Column(name = "userid")
	@RxModelField(text = "系统用户ID")
	private String userid;

	@Column(name = "nickname")
	@RxModelField(text = "用户的昵称")
	private String nickname;

	@Column(name = "sex")
	@RxModelField(text = "用户的性别，值为1时是男性，值为2时是女性，值为0时是未知")
	private Integer sex;

	@Column(name = "city")
	@RxModelField(text = "用户所在城市")
	private String city;

	@Column(name = "country")
	@RxModelField(text = "用户所在国家")
	private String country;

	@Column(name = "province")
	@RxModelField(text = "用户所在省份")
	private String province;

	@Column(name = "languages")
	@RxModelField(text = "用户的语言，简体中文为zh_CN")
	private String languages;

	@Column(name = "subscribe")
	@RxModelField(text = "用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。")
	private String subscribe;

	@Column(name = "subscribetime")
	@RxModelField(text = "用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间", datePattern = RxDatePattern.ISO8601Long)
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date subscribetime;

	@Column(name = "unionid")
	@RxModelField(text = "只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。")
	private String unionid;

	@Column(name = "remark")
	@RxModelField(text = "公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注")
	private String remark;

	@Column(name = "groupid")
	@RxModelField(text = "用户所在的分组ID（兼容旧的用户分组接口）")
	private String groupid;

	@Column(name = "tagidlist")
	@RxModelField(text = "用户被打上的标签ID列表")
	private String tagidlist;

	@Column(name = "createtime")
	@RxModelField(text = "创建时间", datePattern = RxDatePattern.ISO8601Long)
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date createtime;

	@Column(name = "bindstatus")
	@RxModelField(text = "绑定状态 0-未绑定 1-已绑定 2-已解绑")
	private Integer bindstatus;

	@Column(name = "unbindtime")
	@RxModelField(text = "解绑时间", datePattern = RxDatePattern.ISO8601Long)
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date unbindtime;

	@Column(name = "headimgurl")
	@RxModelField(text = "微信用户头像")
	private String headimgurl;

	@Column(name = "opd")
	@RxModelField(text = "临时验证码")
	private String opd;

	@Column(name = "account_code")
	@RxModelField(text = "商户编码")
	private String accountCode;

	public PayWechatUserPo() {
	}

	public PayWechatUserPo(String id) {
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