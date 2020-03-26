package com.rx.pub.wechat.vo.paywechatuser;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.rx.base.enm.RxDatePattern;
import com.rx.base.model.annotation.RxModelField;
import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtGridColumn;
import com.rx.extrx.spring.SpringProviderStore;
import com.rx.extrx.widget.PagingGrid;
import com.rx.pub.wechat.controller.PayWechatUserController;
import com.rx.pub.wechat.model.po.PayWechatUserPo;

/**
 * 微信用户信息(PayWechatUserGrid)
 *
 * @author klf
 * @since 2020-01-09 20:09:44
 */
@ExtClass(alias = "widget.paywechatusergrid", alternateClassName = { "PayWechatUserGrid" })
public class PayWechatUserGrid extends PagingGrid {
	public PayWechatUserGrid() {
		this.setStore(new SpringProviderStore<>(PayWechatUserPo.class, PayWechatUserController.class,
				"listPayWechatUserPage"));
		this.setColumnClass(PayWechatUserColumn.class);
	}
}

class PayWechatUserColumn {

	@RxModelField(text = "", isID = true)
	@ExtGridColumn
	private String id;

	@RxModelField(text = "用户的标识，对当前公众号唯一")
	@ExtGridColumn
	private String openid;

	@RxModelField(text = "系统用户ID")
	@ExtGridColumn
	private String userid;

	@RxModelField(text = "用户的昵称")
	@ExtGridColumn
	private String nickname;

	@RxModelField(text = "用户的性别，值为1时是男性，值为2时是女性，值为0时是未知")
	@ExtGridColumn
	private Integer sex;

	@RxModelField(text = "用户所在城市")
	@ExtGridColumn
	private String city;

	@RxModelField(text = "用户所在国家")
	@ExtGridColumn
	private String country;

	@RxModelField(text = "用户所在省份")
	@ExtGridColumn
	private String province;

	@RxModelField(text = "用户的语言，简体中文为zh_CN")
	@ExtGridColumn
	private String languages;

	@RxModelField(text = "用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。")
	@ExtGridColumn
	private String subscribe;

	@RxModelField(text = "用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间", datePattern = RxDatePattern.ISO8601Long)
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@ExtGridColumn(width = 150)
	private Date subscribetime;

	@RxModelField(text = "只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。")
	@ExtGridColumn
	private String unionid;

	@RxModelField(text = "公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注")
	@ExtGridColumn
	private String remark;

	@RxModelField(text = "用户所在的分组ID（兼容旧的用户分组接口）")
	@ExtGridColumn
	private String groupid;

	@RxModelField(text = "用户被打上的标签ID列表")
	@ExtGridColumn
	private String tagidlist;

	@RxModelField(text = "创建时间", datePattern = RxDatePattern.ISO8601Long)
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@ExtGridColumn(width = 150)
	private Date createtime;

	@RxModelField(text = "绑定状态 0-未绑定 1-已绑定 2-已解绑")
	@ExtGridColumn
	private Integer bindstatus;

	@RxModelField(text = "解绑时间", datePattern = RxDatePattern.ISO8601Long)
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@ExtGridColumn(width = 150)
	private Date unbindtime;

	@RxModelField(text = "微信用户头像")
	@ExtGridColumn
	private String headimgurl;

	@RxModelField(text = "临时验证码")
	@ExtGridColumn
	private String opd;

	@RxModelField(text = "商户编码")
	@ExtGridColumn
	private String accountCode;
}