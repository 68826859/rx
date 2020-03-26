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

import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.rx.base.enm.StatusEnum;
import com.rx.base.page.PageExcute;
import com.rx.base.page.Pager;
import com.rx.pub.mybatis.impls.MybatisBaseService;
import com.rx.pub.wechat.mapper.PayWechatAccountMapper;
import com.rx.pub.wechat.mapper.PayWechatAccountPubMapper;
import com.rx.pub.wechat.model.dto.PayWechatAccountPubDto;
import com.rx.pub.wechat.model.po.PayWechatAccountPo;
import com.rx.pub.wechat.model.po.PayWechatAccountPubPo;
import com.rx.pub.wechat.model.seo.PayWechatAccountPubSearchDto;
import com.rx.pub.wechat.service.PayWechatAccountPubService;

import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;

/**
 * 支付信息(PayWechatAccountPub)Service实现类
 *
 * @author klf
 * @since 2020-01-09 20:09:04
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PayWechatAccountPubServiceImpl extends MybatisBaseService<PayWechatAccountPubPo>
		implements PayWechatAccountPubService {

	private static final Logger log = LoggerFactory.getLogger(PayWechatAccountPubServiceImpl.class);

	@Autowired
	private PayWechatAccountMapper payWechatAccountMapper;
	@Autowired
	private PayWechatAccountPubMapper payWechatAccountPubMapper;
	
	private Map<String, PayWechatAccountPubPo> accountMap = new HashMap<String, PayWechatAccountPubPo>();
    private Map<String, WxPayService> payTypeServices = new HashMap<String, WxPayService>();
    private Map<String, WxMpService> mpServices = new HashMap<String, WxMpService>();

    @PostConstruct
	public void init(){
		PayWechatAccountPubPo accountParam = new PayWechatAccountPubPo();
		accountParam.setStatus(StatusEnum.启用.value());
		List<PayWechatAccountPubPo> list = payWechatAccountPubMapper.select(accountParam);
		
        for (PayWechatAccountPubPo accounts : list) {
            
        	WxMpServiceImpl service = new WxMpServiceImpl();
        	//WxMpConfigStorage configStorage = new WxMpConfigStorage();
            WxMpDefaultConfigImpl configStorage = new WxMpDefaultConfigImpl();
            configStorage.setAppId(accounts.getAppId());
            configStorage.setSecret(accounts.getSecret());
            configStorage.setToken("");
            configStorage.setAesKey("");
            //configStorage.set("JSON");
            service.setWxMpConfigStorage(configStorage);
            mpServices.put(accounts.getId(), service);

			accountMap.put(accounts.getId(), accounts);
        }

        PayWechatAccountPo appAccountParam = new PayWechatAccountPo();
        appAccountParam.setStatus(StatusEnum.启用.value());
		List<PayWechatAccountPo> appAccountList = payWechatAccountMapper.select(appAccountParam);
        for (PayWechatAccountPo appAccounts : appAccountList) {

            //构建支付账号信息
            WxPayConfig payConfig = new WxPayConfig();
            payConfig.setAppId(appAccounts.getAppId());
            payConfig.setMchId(appAccounts.getMchId());
            payConfig.setMchKey(appAccounts.getApiKey());
            payConfig.setSubAppId(null);
            payConfig.setSubMchId(null);
            payConfig.setKeyPath(appAccounts.getKeyPath());

            // 可以指定是否使用沙箱环境
            payConfig.setUseSandboxEnv(false);

            WxPayService wxPayService = new WxPayServiceImpl();
            wxPayService.setConfig(payConfig);
            payTypeServices.put(appAccounts.getAccountCode(), wxPayService); //app类支付


            //构建公众号账号信息
            for (PayWechatAccountPubPo value : list) {
                if (value.getAccountCode() == appAccounts.getAccountCode()) {
                	payConfig = new WxPayConfig();
                    payConfig.setAppId(value.getAppId());
                    payConfig.setMchId(appAccounts.getMchId());
                    payConfig.setMchKey(appAccounts.getApiKey());
                    payConfig.setSubAppId(null);
                    payConfig.setSubMchId(null);
                    payConfig.setKeyPath(appAccounts.getKeyPath());

                    // 可以指定是否使用沙箱环境
                    payConfig.setUseSandboxEnv(false);
                    wxPayService = new WxPayServiceImpl();
                    wxPayService.setConfig(payConfig);
                    payTypeServices.put(value.getId(), wxPayService); //小程序类支付
                }
            }
        }
	}

	
	@Override
	public Pager<PayWechatAccountPubDto> searchPage(PayWechatAccountPubSearchDto dto) {
		return this.getPageExcuter().selectByPage(new PageExcute<PayWechatAccountPubDto>() {
			@Override
			public List<PayWechatAccountPubDto> excute() {
				return payWechatAccountPubMapper.searchList(dto);
			}
		}, this.getPagerProvider().getPager(PayWechatAccountPubDto.class));
	}

	
	public PayWechatAccountPubPo getAccountPubById(String accountPubId){
		PayWechatAccountPubPo account = accountMap.get(accountPubId);
		if(null == account){
			account = payWechatAccountPubMapper.selectByPrimaryKey(accountPubId);
			if(account != null){
				accountMap.put(account.getId(), account);
			}
		}
		return account;
	}
	
	
	public PayWechatAccountPubPo getAccountPubByCode(String accountCode, Integer bizType){
		PayWechatAccountPubPo account = null;
		for(Map.Entry<String, PayWechatAccountPubPo> po : accountMap.entrySet()){
			if(accountCode.equals(po.getValue().getAccountCode()) && bizType == po.getValue().getBizType()){
				account = po.getValue();
			}
		}
		if(null == account){
			account = new PayWechatAccountPubPo();
			account.setAccountCode(accountCode);
			account.setBizType(bizType);
			account.setStatus(StatusEnum.启用.value());
			List<PayWechatAccountPubPo> list = payWechatAccountPubMapper.select(account);
			for (PayWechatAccountPubPo po : list) {
				account = po;
				accountMap.put(account.getId(), account);
			}
		}
		return account;
	}
	
	
	public WxMpService getMpService(String accountId) {
        return mpServices.get(accountId);
    }
	
	public WxPayService getPayService(String accountId) {
		return payTypeServices.get(accountId);
		
	}
}