package com.rx.pub.wechat.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rx.base.enm.StatusEnum;
import com.rx.base.page.PageExcute;
import com.rx.base.page.Pager;
import com.rx.pub.mybatis.impls.MybatisBaseService;
import com.rx.pub.wechat.mapper.PayWechatAccountMapper;
import com.rx.pub.wechat.model.dto.PayWechatAccountDto;
import com.rx.pub.wechat.model.po.PayWechatAccountPo;
import com.rx.pub.wechat.model.seo.PayWechatAccountSearchDto;
import com.rx.pub.wechat.service.PayWechatAccountService;

/**
 * 商户主体(PayWechatAccount)Service实现类
 *
 * @author klf
 * @since 2020-01-09 20:08:48
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PayWechatAccountServiceImpl extends MybatisBaseService<PayWechatAccountPo>
		implements PayWechatAccountService {

	private static final Logger log = LoggerFactory.getLogger(PayWechatAccountServiceImpl.class);

	@Autowired
	private PayWechatAccountMapper payWechatAccountMapper;
	public Map<String, PayWechatAccountPo> accountMap = new HashMap<String, PayWechatAccountPo>();
	
	@PostConstruct
	public void init(){
		PayWechatAccountPo account = new PayWechatAccountPo();
		account.setStatus(StatusEnum.启用.value());
		List<PayWechatAccountPo> list = payWechatAccountMapper.select(account);
		for (PayWechatAccountPo po : list) {
			accountMap.put(po.getAccountCode(), po);
		}
	}

	@Override
	public Pager<PayWechatAccountDto> searchPage(PayWechatAccountSearchDto dto) {
		return this.getPageExcuter().selectByPage(new PageExcute<PayWechatAccountDto>() {
			@Override
			public List<PayWechatAccountDto> excute() {
				return payWechatAccountMapper.searchList(dto);
			}
		}, this.getPagerProvider().getPager(PayWechatAccountDto.class));
	}

	public PayWechatAccountPo getAccountByCode(String accountCode){
		if(null == accountMap.get(accountCode)){
			PayWechatAccountPo account = new PayWechatAccountPo();
			account.setAccountCode(accountCode);
			account.setStatus(StatusEnum.启用.value());
			List<PayWechatAccountPo> list = payWechatAccountMapper.select(account);
			if(list != null && list.size() > 0){
				accountMap.put(list.get(0).getAccountCode(), list.get(0));
			}
		}
		return accountMap.get(accountCode);
	}
}