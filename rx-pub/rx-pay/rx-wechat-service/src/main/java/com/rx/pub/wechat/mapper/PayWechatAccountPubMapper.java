package com.rx.pub.wechat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.rx.pub.mybatis.impls.MyBatisMapper;
import com.rx.pub.wechat.model.dto.PayWechatAccountPubDto;
import com.rx.pub.wechat.model.po.PayWechatAccountPubPo;
import com.rx.pub.wechat.model.seo.PayWechatAccountPubSearchDto;

/**
 * 支付信息(PayWechatAccountPub)Mapper
 *
 * @author klf
 * @since 2020-01-09 20:09:04
 */
public interface PayWechatAccountPubMapper extends MyBatisMapper<PayWechatAccountPubPo> {

	List<PayWechatAccountPubDto> searchList(@Param("param") PayWechatAccountPubSearchDto dto);

}