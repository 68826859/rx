package com.rx.pub.msgq.vo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import com.rx.base.enm.RxDatePattern;
import com.rx.base.model.annotation.RxModelField;
import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtFormAction;
import com.rx.ext.annotation.ExtFormField;
import com.rx.ext.form.field.TextArea;
import com.rx.extrx.spring.SpringAction;
import com.rx.extrx.widget.ActionForm;
import com.rx.pub.msgq.controller.MsgqController;

@ExtClass(extend=ActionForm.class,alternateClassName="MsgqAddVo")
public class MsgqAddVo{

	
	@ExtFormAction
    SpringAction submitAction = new SpringAction(MsgqController.class,"addMsgq");
	
	@ExtFormField(allowBlank = false)
	@RxModelField(text = "消息类型")
    private String msgType;
	
	@ExtFormField()
	@RxModelField(text = "独生键")
    private String singleKey;
	
	@ExtFormField(comp=TextArea.class)
	@RxModelField(text = "消息体")
    private String msgContent;
    
	@RxModelField(text = "开始时间", datePattern = RxDatePattern.ISO8601Long)
    @ExtFormField(datePattern = RxDatePattern.ISO8601Long)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date beginTime;

	@RxModelField(text = "结束时间", datePattern = RxDatePattern.ISO8601Long)
    @ExtFormField(datePattern = RxDatePattern.ISO8601Long)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getSingleKey() {
		return singleKey;
	}

	public void setSingleKey(String singleKey) {
		this.singleKey = singleKey;
	}

	public String getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

}
