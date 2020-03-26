package com.rx.pub.wechat.vo.paywechataccountpub;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.rx.base.enm.RxDatePattern;
import com.rx.base.enm.StatusEnum;
import com.rx.base.model.annotation.RxModelField;
import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtGridColumn;
import com.rx.extrx.spring.SpringProviderStore;
import com.rx.extrx.widget.PagingGrid;
import com.rx.pub.wechat.controller.PayWechatAccountPubController;
import com.rx.pub.wechat.model.po.PayWechatAccountPubPo;

/**
 * 支付信息(PayWechatAccountPubGrid)
 *
 * @author klf
 * @since 2020-01-09 20:09:04
 */
@ExtClass(alias = "widget.paywechataccountpubgrid", alternateClassName = { "PayWechatAccountPubGrid" })
public class PayWechatAccountPubGrid extends PagingGrid {
	public PayWechatAccountPubGrid() {
		this.setStore(new SpringProviderStore<>(PayWechatAccountPubPo.class, PayWechatAccountPubController.class,
				"listPayWechatAccountPubPage"));
		this.setColumnClass(PayWechatAccountPubColumn.class);
	}
}

class PayWechatAccountPubColumn {

	@RxModelField(text = "", isID = true)
	@ExtGridColumn
	private String id;

	@RxModelField(text = "编码")
	@ExtGridColumn
	private String accountCode;

	@RxModelField(text = "")
	@ExtGridColumn
	private String appId;

	@RxModelField(text = "")
	@ExtGridColumn
	private String secret;

	@RxModelField(text = "支付业务")
	@ExtGridColumn
	private Integer bizType;

	@RxModelField(text = "支付主体id")
	@ExtGridColumn
	private String accountId;

	@RxModelField(text = "状态")
	@ExtGridColumn(em = StatusEnum.class)
	private Integer status;

	@RxModelField(text = "", datePattern = RxDatePattern.ISO8601Long)
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@ExtGridColumn(width = 150)
	private Date createTime;
}