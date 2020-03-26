package com.rx.pub.wechat.vo.paywechataccount;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.rx.base.enm.RxDatePattern;
import com.rx.base.enm.StatusEnum;
import com.rx.base.model.annotation.RxModelField;
import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtGridColumn;
import com.rx.extrx.spring.SpringProviderStore;
import com.rx.extrx.widget.PagingGrid;
import com.rx.pub.wechat.controller.PayWechatAccountController;
import com.rx.pub.wechat.model.po.PayWechatAccountPo;

/**
 * 商户主体(PayWechatAccountGrid)
 *
 * @author klf
 * @since 2020-01-09 20:08:50
 */
@ExtClass(alias = "widget.paywechataccountgrid", alternateClassName = { "PayWechatAccountGrid" })
public class PayWechatAccountGrid extends PagingGrid {
	public PayWechatAccountGrid() {
		this.setStore(new SpringProviderStore<>(PayWechatAccountPo.class, PayWechatAccountController.class,
				"listPayWechatAccountPage"));
		this.setColumnClass(PayWechatAccountColumn.class);
	}
}

class PayWechatAccountColumn {

	@RxModelField(text = "", isID = true)
	@ExtGridColumn
	private String id;

	@RxModelField(text = "编码")
	@ExtGridColumn
	private String accountCode;

	@RxModelField(text = "微信开放平台appid")
	@ExtGridColumn
	private String appId;

	@RxModelField(text = "公众平台商户号")
	@ExtGridColumn
	private String mchId;

	@RxModelField(text = "商户密钥")
	@ExtGridColumn
	private String apiKey;

	@RxModelField(text = "商户证书")
	@ExtGridColumn
	private String keyPath;

	@RxModelField(text = "状态")
	@ExtGridColumn(em = StatusEnum.class)
	private Integer status;

	@RxModelField(text = "", datePattern = RxDatePattern.ISO8601Long)
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@ExtGridColumn(width = 150)
	private Date createTime;
}