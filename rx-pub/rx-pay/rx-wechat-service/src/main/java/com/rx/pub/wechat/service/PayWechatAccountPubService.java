package com.rx.pub.wechat.service;

import com.github.binarywang.wxpay.service.WxPayService;
import com.rx.base.page.Pager;
import com.rx.base.service.BaseService;
import com.rx.pub.wechat.model.dto.PayWechatAccountPubDto;
import com.rx.pub.wechat.model.po.PayWechatAccountPubPo;
import com.rx.pub.wechat.model.seo.PayWechatAccountPubSearchDto;

import me.chanjar.weixin.mp.api.WxMpService;

/**
 * 支付信息(PayWechatAccountPub)Service
 *
 * @author klf
 * @since 2020-01-09 20:09:04
 */
public interface PayWechatAccountPubService extends BaseService<PayWechatAccountPubPo> {

	public void init();

	/**
	 * 分页查询
	 */
	Pager<PayWechatAccountPubDto> searchPage(PayWechatAccountPubSearchDto dto);

	public PayWechatAccountPubPo getAccountPubById(String accountPubId);

	public PayWechatAccountPubPo getAccountPubByCode(String accountCode, Integer bizType);

	public WxMpService getMpService(String accountPubId);

	public WxPayService getPayService(String accountId);
}