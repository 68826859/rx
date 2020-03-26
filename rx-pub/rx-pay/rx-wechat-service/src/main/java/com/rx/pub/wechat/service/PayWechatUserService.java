package com.rx.pub.wechat.service;

import javax.servlet.http.HttpServletRequest;

import com.rx.base.page.Pager;
import com.rx.base.service.BaseService;
import com.rx.pub.wechat.model.dto.PayWechatUserDto;
import com.rx.pub.wechat.model.po.PayWechatUserPo;
import com.rx.pub.wechat.model.seo.PayWechatUserSearchDto;

/**
 * 微信用户信息(PayWechatUser)Service
 *
 * @author klf
 * @since 2020-01-09 20:09:44
 */
public interface PayWechatUserService extends BaseService<PayWechatUserPo> {

	public PayWechatUserDto getUserInfo(HttpServletRequest request, String accessToken, String openID);

	/**
	 * 分页查询
	 */
	Pager<PayWechatUserDto> searchPage(PayWechatUserSearchDto dto);

	public PayWechatUserDto getByOpenid(String openid, String accountCode);

	public PayWechatUserDto getByUnionId(String unionId, String accountCode);

	public PayWechatUserDto getByUserId(String userId, String accountCode);

}