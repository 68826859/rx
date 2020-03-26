package com.rx.pub.wechat.service;

import com.rx.base.page.Pager;
import com.rx.base.service.BaseService;
import com.rx.pub.wechat.model.dto.PayWechatAccountDto;
import com.rx.pub.wechat.model.po.PayWechatAccountPo;
import com.rx.pub.wechat.model.seo.PayWechatAccountSearchDto;

/**
 * 商户主体(PayWechatAccount)Service
 *
 * @author klf
 * @since 2020-01-09 20:08:47
 */
public interface PayWechatAccountService extends BaseService<PayWechatAccountPo> {

    /**
     * 分页查询
     */
    Pager<PayWechatAccountDto> searchPage(PayWechatAccountSearchDto dto);

    
    public PayWechatAccountPo getAccountByCode(String accountCode);
}