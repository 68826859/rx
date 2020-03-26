package com.rx.pub.wechat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.rx.pub.mybatis.impls.MyBatisMapper;
import com.rx.pub.wechat.model.dto.PayWechatUserDto;
import com.rx.pub.wechat.model.po.PayWechatUserPo;
import com.rx.pub.wechat.model.seo.PayWechatUserSearchDto;

/**
 * 微信用户信息(PayWechatUser)Mapper
 *
 * @author klf
 * @since 2020-01-09 20:09:44
 */
public interface PayWechatUserMapper extends MyBatisMapper<PayWechatUserPo> {

	List<PayWechatUserDto> searchList(@Param("param") PayWechatUserSearchDto dto);

	public PayWechatUserDto getByOpenid(String openid, String accountCode);

	public PayWechatUserDto getByUnionId(String unionId, String accountCode);

	public PayWechatUserDto getByUserId(String userId, String accountCode);
}