package com.rx.pub.msgq.service;

import java.util.List;

import com.rx.base.page.Pager;
import com.rx.base.service.BaseService;
import com.rx.pub.msgq.dto.MsgqSearchDto;
import com.rx.pub.msgq.po.MsgqPo;

public interface MsgqService extends BaseService<MsgqPo> {
	
	Pager<MsgqPo> listMsgqPage(MsgqSearchDto msgqSearchDto);
	
	int insertMsgs(List<MsgqPo> list);
	MsgqPo selectOneAvailableSingleMsg(String singleKey);
}
