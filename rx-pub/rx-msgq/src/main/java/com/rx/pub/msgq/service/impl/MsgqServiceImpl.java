package com.rx.pub.msgq.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.rx.base.page.PageExcute;
import com.rx.base.page.Pager;
import com.rx.pub.msgq.dto.MsgqSearchDto;
import com.rx.pub.msgq.mapper.MsgqMapper;
import com.rx.pub.msgq.po.MsgqPo;
import com.rx.pub.msgq.service.MsgqService;
import com.rx.pub.mybatis.impls.MybatisBaseService;
import com.rx.web.utils.HttpServletHelper;

@Service
public class MsgqServiceImpl extends MybatisBaseService<MsgqPo> implements MsgqService {
    @Autowired
    private MsgqMapper msgqMapper;

    @Override
    public Pager<MsgqPo> listMsgqPage(MsgqSearchDto msgqSearchDto) {
        return this.getPageExcuter().selectByPage(new PageExcute<MsgqPo>() {
            @Override
            public List<MsgqPo> excute() {
                return msgqMapper.listMsgPage(msgqSearchDto);
            }
        },HttpServletHelper.getPager(MsgqPo.class));
    }
    
	@Override
	public int insertMsgs(List<MsgqPo> list) {
		return msgqMapper.insertBatch(list);
	}
	
	@Override
	public MsgqPo selectOneAvailableSingleMsg(String singleKey) {
		return msgqMapper.selectOneAvailableSingleMsg(singleKey);
	}
}
