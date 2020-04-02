package com.rx.pub.msgq.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.rx.base.enm.RxDatePattern;
import com.rx.base.model.annotation.RxModelField;
import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtFormField;
import com.rx.extrx.widget.ParamForm;

@ExtClass(extend=ParamForm.class, alternateClassName="MsgqSearchDto")
public class MsgqSearchDto{
    
	@ExtFormField()
	@RxModelField(text = "消息ID")
    private String msgId;

	@ExtFormField()
	@RxModelField(text = "消息类型")
    private String msgType;

	@ExtFormField()
	@RxModelField(text = "消息持有者")
    private String holder;

	@ExtFormField()
	@RxModelField(text = "独生键")
    private String singleKey;
	
	@RxModelField(text = "创建时间", datePattern = RxDatePattern.ISO8601Short)
	@ExtFormField(datePattern=RxDatePattern.ISO8601Short)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTime;
	
	@ExtFormField()
	@RxModelField(text = "消息体")
    private String msgContent;
	
	@RxModelField(text = "开始时间", datePattern = RxDatePattern.ISO8601Short)
	@ExtFormField(datePattern=RxDatePattern.ISO8601Short)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginTime;
	
	@RxModelField(text = "结束时间", datePattern = RxDatePattern.ISO8601Short)
	@ExtFormField(datePattern=RxDatePattern.ISO8601Short)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;
	
	@ExtFormField()
	@RxModelField(text = "错误信息")
    private String errorMsg;

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getHolder() {
		return holder;
	}

	public void setHolder(String holder) {
		this.holder = holder;
	}

	public String getSingleKey() {
		return singleKey;
	}

	public void setSingleKey(String singleKey) {
		this.singleKey = singleKey;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
}
