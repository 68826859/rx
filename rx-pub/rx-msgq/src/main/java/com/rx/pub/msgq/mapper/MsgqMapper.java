package com.rx.pub.msgq.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.rx.pub.msgq.dto.MsgqSearchDto;
import com.rx.pub.msgq.po.MsgqPo;
import com.rx.pub.mybatis.impls.MyBatisMapper;

public interface MsgqMapper extends MyBatisMapper<MsgqPo> {
	List<MsgqPo> listMsg(@Param("param") MsgqPo param);
	
	int insertBatch(List<MsgqPo> list);
	
	int updateHoldOneMsg(@Param("holder") String holder,@Param("msgType") String msgType);
	
	MsgqPo selectOneHoldMsg(@Param("holder") String holder,@Param("msgType") String msgType);
	
	MsgqPo selectOneAvailableSingleMsg(@Param("singleKey") String singleKey);
	
	List<MsgqPo> listMsgPage(@Param(value = "param") MsgqSearchDto sqlSearchDto);
}