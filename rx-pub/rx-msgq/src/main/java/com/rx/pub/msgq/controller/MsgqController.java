package com.rx.pub.msgq.controller;

import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.rx.base.page.Pager;
import com.rx.base.result.DataResult;
import com.rx.base.result.type.ValidateException;
import com.rx.ext.annotation.ExtClass;
import com.rx.extrx.spring.SpringProvider;
import com.rx.pub.msgq.annotation.PermissionMsgq;
import com.rx.pub.msgq.dto.MsgqSearchDto;
import com.rx.pub.msgq.enm.PermissionEnumMsgq;
import com.rx.pub.msgq.po.MsgqPo;
import com.rx.pub.msgq.service.MsgqService;
import com.rx.pub.msgq.utils.MsgqConsumer;
import com.rx.pub.msgq.vo.MsgqAddVo;
import com.rx.pub.msgq.vo.MsgqUpdateVo;

@RestController
@RequestMapping("/msgq")
@ExtClass(extend = SpringProvider.class, alternateClassName = "MsgqController")
public class MsgqController {
    
	@Autowired
    private MsgqService msgqService;
	
	
	@RequestMapping( value = "/consumer", method = RequestMethod.POST)
    @ResponseBody
    public DataResult postMessage(){
        	MsgqConsumer.consumeMessage();
        	return new DataResult("");
    }
    
    

    @RequestMapping(value = "/listMsgqPage")
    @ResponseBody
    @PermissionMsgq(PermissionEnumMsgq.队列消息列表)
    public DataResult listMsgqPage(MsgqSearchDto msgqSearchDto) throws Exception {
        Pager<MsgqPo> msgq = msgqService.listMsgqPage(msgqSearchDto);
        return new DataResult(msgq);
    }
    
    
    @RequestMapping(value = "/getMsgq")
    @ResponseBody
    @PermissionMsgq(PermissionEnumMsgq.队列消息列表)
    public DataResult getMsgq(@Validated String msgId) throws Exception {
        return new DataResult(msgqService.selectByPrimaryKey(msgId));
    }
    
    @RequestMapping(value = "/delMsgq")
    @ResponseBody
    @PermissionMsgq(PermissionEnumMsgq.删除队列消息)
    public DataResult delMsgq(@Validated String id) throws Exception {
    	if(!StringUtils.hasText(id)) {
    		throw new ValidateException("缺少id");
    	}
        int len = msgqService.deleteByPrimaryKey(id);
        if(len > 0) {
        	return new DataResult("删除成功");
        }else {
        	return new DataResult("删除失败");
        }
    }
    
    @RequestMapping(value = "/updateMsgq")
    @ResponseBody
    @PermissionMsgq(PermissionEnumMsgq.修改队列消息)
    public DataResult updateMsgq(@Validated MsgqUpdateVo updateVo) throws Exception {
    	if(!StringUtils.hasText(updateVo.getMsgId())) {
    		throw new ValidateException("缺少msgId");
    	}
    	MsgqPo po = new MsgqPo();
    	BeanUtils.copyProperties(updateVo, po);
        int len = msgqService.updateByPrimaryKey(po);
        if(len > 0) {
        	return new DataResult("更新成功");
        }else {
        	return new DataResult("更新失败");
        }
    }
    
    @RequestMapping(value = "/addMsgq")
    @ResponseBody
    @PermissionMsgq(PermissionEnumMsgq.新增队列消息)
    public DataResult addMsgq(@Validated MsgqAddVo addVo) throws Exception {
    	MsgqPo po = new MsgqPo(null);
    	BeanUtils.copyProperties(addVo, po);
    	po.setCreateTime(new Date());
        int len = msgqService.insert(po);
        if(len > 0) {
        	return new DataResult("新增成功");
        }else {
        	return new DataResult("新增失败");
        }
    }
    
}
