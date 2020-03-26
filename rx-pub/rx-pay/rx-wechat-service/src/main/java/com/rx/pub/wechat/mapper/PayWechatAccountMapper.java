package com.rx.pub.wechat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.rx.pub.mybatis.impls.MyBatisMapper;
import com.rx.pub.wechat.model.dto.PayWechatAccountDto;
import com.rx.pub.wechat.model.po.PayWechatAccountPo;
import com.rx.pub.wechat.model.seo.PayWechatAccountSearchDto;

/**
 * 商户主体(PayWechatAccount)Mapper
 *
 * @author klf
 * @since 2020-01-09 20:08:50
 */
public interface PayWechatAccountMapper extends MyBatisMapper<PayWechatAccountPo>{

    List<PayWechatAccountDto> searchList(@Param("param")PayWechatAccountSearchDto dto);

}