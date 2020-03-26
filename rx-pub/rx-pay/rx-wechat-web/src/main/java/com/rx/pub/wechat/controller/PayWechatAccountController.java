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
import com.rx.pub.wechat.model.po.PayWechatAccountPo;
import com.rx.pub.wechat.model.seo.PayWechatAccountSearchDto;
import com.rx.pub.wechat.service.PayWechatAccountService;
import com.rx.pub.wechat.vo.paywechataccount.PayWechatAccountAddVo;
import com.rx.pub.wechat.vo.paywechataccount.PayWechatAccountSearchVo;
import com.rx.pub.wechat.vo.paywechataccount.PayWechatAccountUpdateVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 商户主体(PayWechatAccount)controller
 *
 * @author klf
 * @since 2020-01-09 20:08:49
 */
@RestController
@RequestMapping("/payWechatAccount")
@Api(description = "商户主体")
@ExtClass(extend = SpringProvider.class, alternateClassName = "PayWechatAccountController")
public class PayWechatAccountController {

	@Autowired
	private PayWechatAccountService payWechatAccountService;

	@ApiOperation(value = "新增商户主体")
	@PermissionPay(PayPermissionEnum.新增商户主体)
	@RequestMapping(value = "/addPayWechatAccount", method = RequestMethod.POST)
	@ResponseBody
	public DataResult addPayWechatAccount(@Validated PayWechatAccountAddVo param) {
		PayWechatAccountPo record = new PayWechatAccountPo(null);
		BeanUtils.copyProperties(param, record);
		payWechatAccountService.insertSelective(record);
		return new DataResult(record);
	}

	@ApiOperation(value = "修改商户主体")
	@PermissionPay(PayPermissionEnum.修改商户主体)
	@RequestMapping(value = "/updatePayWechatAccount", method = RequestMethod.POST)
	@ResponseBody
	public DataResult updatePayWechatAccount(@Validated PayWechatAccountUpdateVo param) {
		PayWechatAccountPo old = payWechatAccountService.selectByPrimaryKey(param.getId());
		if (old == null) {
			throw new ValidateException("商户主体不存在");
		}
		PayWechatAccountPo record = new PayWechatAccountPo();
		BeanUtils.copyProperties(param, record);
		payWechatAccountService.updateByPrimaryKeySelective(record);
		return new DataResult(record);
	}

	@ApiOperation(value = "删除商户主体")
	@PermissionPay(PayPermissionEnum.删除商户主体)
	@RequestMapping(value = "/delPayWechatAccount", method = RequestMethod.GET)
	@ResponseBody
	public DataResult delPayWechatAccount(String id) {
		payWechatAccountService.deleteByPrimaryKey(id);
		return new DataResult("成功");
	}

	@ApiOperation(value = "分页列出商户主体")
	@PermissionPay(PayPermissionEnum.商户主体列表)
	@RequestMapping(value = "/listPayWechatAccountPage", method = RequestMethod.GET)
	@ResponseBody
	public DataResult listPayWechatAccountPage(@Validated PayWechatAccountSearchVo param) {
		PayWechatAccountSearchDto dto = new PayWechatAccountSearchDto();
		BeanUtils.copyProperties(param, dto);
		return new DataResult(payWechatAccountService.searchPage(dto));
	}
}