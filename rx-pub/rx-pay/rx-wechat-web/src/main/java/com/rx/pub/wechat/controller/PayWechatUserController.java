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
import com.rx.pub.wechat.model.po.PayWechatUserPo;
import com.rx.pub.wechat.model.seo.PayWechatUserSearchDto;
import com.rx.pub.wechat.service.PayWechatUserService;
import com.rx.pub.wechat.vo.paywechatuser.PayWechatUserAddVo;
import com.rx.pub.wechat.vo.paywechatuser.PayWechatUserSearchVo;
import com.rx.pub.wechat.vo.paywechatuser.PayWechatUserUpdateVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


/**
 * 微信用户信息(PayWechatUser)controller
 *
 * @author klf
 * @since 2020-01-09 20:09:44
 */
@RestController
@RequestMapping("/payWechatUser")
@Api(description = "微信用户信息")
@ExtClass(extend = SpringProvider.class, alternateClassName = "PayWechatUserController")
public class PayWechatUserController {
    
    @Autowired
    private PayWechatUserService payWechatUserService;

    @ApiOperation(value = "新增微信用户信息")
    @PermissionPay(PayPermissionEnum.新增微信用户信息)
    @RequestMapping(value = "/addPayWechatUser", method = RequestMethod.POST)
    @ResponseBody
    public DataResult addPayWechatUser(@Validated PayWechatUserAddVo param){
        PayWechatUserPo record = new PayWechatUserPo(null);
        BeanUtils.copyProperties(param, record);
        payWechatUserService.insertSelective(record);
    	return new DataResult(record);
    }
    
    @ApiOperation(value = "修改微信用户信息")
    @PermissionPay(PayPermissionEnum.修改微信用户信息)
    @RequestMapping(value = "/updatePayWechatUser", method = RequestMethod.POST)
    @ResponseBody
    public DataResult updatePayWechatUser(@Validated PayWechatUserUpdateVo param){
        PayWechatUserPo old = payWechatUserService.selectByPrimaryKey(param.getId());
    	if(old == null) {
    		throw new ValidateException("微信用户信息不存在");
    	}
    	PayWechatUserPo record = new PayWechatUserPo();
        BeanUtils.copyProperties(param, record);
        payWechatUserService.updateByPrimaryKeySelective(record);
        return new DataResult(record);
    }
    
    @ApiOperation(value = "删除微信用户信息")
    @PermissionPay(PayPermissionEnum.删除微信用户信息)
    @RequestMapping(value = "/delPayWechatUser", method = RequestMethod.GET)
    @ResponseBody
    public DataResult delPayWechatUser(String id){
    	payWechatUserService.deleteByPrimaryKey(id);
    	return new DataResult("成功");
    }
    
    @ApiOperation(value = "分页列出微信用户信息")
    @PermissionPay(PayPermissionEnum.微信用户信息列表)
    @RequestMapping(value = "/listPayWechatUserPage", method = RequestMethod.GET)
    @ResponseBody
    public DataResult listPayWechatUserPage(@Validated PayWechatUserSearchVo param){
    	PayWechatUserSearchDto dto = new PayWechatUserSearchDto();
        BeanUtils.copyProperties(param, dto);
        return new DataResult(payWechatUserService.searchPage(dto));
    }
}