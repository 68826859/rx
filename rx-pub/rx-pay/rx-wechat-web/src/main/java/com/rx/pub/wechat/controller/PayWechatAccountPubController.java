package com.rx.pub.wechat.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.rx.base.result.DataResult;
import com.rx.base.result.type.ValidateException;
import com.rx.ext.annotation.ExtClass;
import com.rx.extrx.spring.SpringProvider;
import com.rx.pub.file.annotation.PermissionPay;
import com.rx.pub.file.enm.PayPermissionEnum;
import com.rx.pub.wechat.model.po.PayWechatAccountPubPo;
import com.rx.pub.wechat.model.seo.PayWechatAccountPubSearchDto;
import com.rx.pub.wechat.service.PayWechatAccountPubService;
import com.rx.pub.wechat.vo.paywechataccountpub.PayWechatAccountPubAddVo;
import com.rx.pub.wechat.vo.paywechataccountpub.PayWechatAccountPubSearchVo;
import com.rx.pub.wechat.vo.paywechataccountpub.PayWechatAccountPubUpdateVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


/**
 * 支付信息(PayWechatAccountPub)controller
 *
 * @author klf
 * @since 2020-01-09 20:09:04
 */
@RestController
@RequestMapping("/payWechatAccountPub")
@Api(description = "支付信息")
@ExtClass(extend = SpringProvider.class, alternateClassName = "PayWechatAccountPubController")
public class PayWechatAccountPubController {
    
    @Autowired
    private PayWechatAccountPubService payWechatAccountPubService;

    @ApiOperation(value = "新增支付信息")
    @PermissionPay(PayPermissionEnum.新增支付信息)
    @RequestMapping(value = "/addPayWechatAccountPub", method = RequestMethod.POST)
    @ResponseBody
    public DataResult addPayWechatAccountPub(@Validated PayWechatAccountPubAddVo param){
        PayWechatAccountPubPo record = new PayWechatAccountPubPo(null);
        BeanUtils.copyProperties(param, record);
        payWechatAccountPubService.insertSelective(record);
    	return new DataResult(record);
    }
    
    @ApiOperation(value = "修改支付信息")
    @PermissionPay(PayPermissionEnum.修改支付信息)
    @RequestMapping(value = "/updatePayWechatAccountPub", method = RequestMethod.POST)
    @ResponseBody
    public DataResult updatePayWechatAccountPub(@Validated PayWechatAccountPubUpdateVo param){
        PayWechatAccountPubPo old = payWechatAccountPubService.selectByPrimaryKey(param.getId());
    	if(old == null) {
    		throw new ValidateException("支付信息不存在");
    	}
    	PayWechatAccountPubPo record = new PayWechatAccountPubPo();
        BeanUtils.copyProperties(param, record);
        payWechatAccountPubService.updateByPrimaryKeySelective(record);
        return new DataResult(record);
    }
    
    @ApiOperation(value = "删除支付信息")
    @PermissionPay(PayPermissionEnum.删除支付信息)
    @RequestMapping(value = "/delPayWechatAccountPub", method = RequestMethod.GET)
    @ResponseBody
    public DataResult delPayWechatAccountPub(String id){
    	payWechatAccountPubService.deleteByPrimaryKey(id);
    	return new DataResult("成功");
    }
    
    @ApiOperation(value = "分页列出支付信息")
    @PermissionPay(PayPermissionEnum.支付信息列表)
    @RequestMapping(value = "/listPayWechatAccountPubPage", method = RequestMethod.GET)
    @ResponseBody
    public DataResult listPayWechatAccountPubPage(@Validated PayWechatAccountPubSearchVo param){
    	PayWechatAccountPubSearchDto dto = new PayWechatAccountPubSearchDto();
        BeanUtils.copyProperties(param, dto);
        return new DataResult(payWechatAccountPubService.searchPage(dto));
    }
    
    

    @ApiOperation(value = "重置支付配置项")
    @PermissionPay(PayPermissionEnum.重置支付配置项)
    @RequestMapping(value = "/initAccountConfig", method = RequestMethod.GET)
    @ResponseBody
    public DataResult initAccountConfig(@Validated PayWechatAccountPubSearchVo param){
    	payWechatAccountPubService.init();
        return new DataResult();
    }
}