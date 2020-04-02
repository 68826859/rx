package com.rx.pub.msgq.vo;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

import com.rx.base.enm.RxDatePattern;
import com.rx.base.model.annotation.RxModelField;
import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtFormAction;
import com.rx.ext.annotation.ExtFormField;
import com.rx.ext.enums.BooleanValue;
import com.rx.ext.enums.FormActionType;
import com.rx.ext.form.field.Text;
import com.rx.ext.form.field.TextArea;
import com.rx.extrx.spring.SpringAction;
import com.rx.extrx.widget.ActionForm;
import com.rx.pub.msgq.controller.MsgqController;

@ExtClass(extend=ActionForm.class,alternateClassName="MsgqUpdateVo")
public class MsgqUpdateVo{

	
	@ExtFormAction
    SpringAction submitAction = new SpringAction(MsgqController.class,"updateMsgq");
	
	@ExtFormAction(type=FormActionType.load)
    SpringAction loadAction = new SpringAction(MsgqController.class,"getMsgq");

	@NotBlank(message = "消息ID不能为空")
    @ExtFormField(label = "消息ID", allowBlank = false, comp = Text.class,editable=BooleanValue.FALSE)
    private String msgId;
	
	@ExtFormField(editable=BooleanValue.FALSE)
	@RxModelField(text = "消息类型")
    private String msgType;
	
	@ExtFormField(editable=BooleanValue.FALSE)
	@RxModelField(text = "独生键")
    private String singleKey;
	
	@RxModelField(text = "创建时间", datePattern = RxDatePattern.ISO8601Long)
	@ExtFormField(datePattern=RxDatePattern.ISO8601Long,editable=BooleanValue.FALSE,readOnly=true,comp=Text.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
	
	@ExtFormField()
	@RxModelField(text = "消息持有者")
    private String holder;
	
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
	
	@ExtFormField(comp=TextArea.class)
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
