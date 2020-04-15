package com.rx.pub.msgq.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.alibaba.fastjson.annotation.JSONField;
import com.rx.base.enm.RxDatePattern;
import com.rx.base.model.annotation.RxModelField;
import com.rx.base.utils.StringUtil;
import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtGridColumn;
import com.rx.ext.data.Model;

@Table(name = "pub_msgq")
@ExtClass(extend = Model.class, alternateClassName = "MsgqPo")
public class MsgqPo{
	
	public MsgqPo() {
		
	}
    public MsgqPo(String msgId) {
		super();
		if(msgId == null) {
			msgId = StringUtil.getUUID();//SerialNumberUtil.genSerialId("O");
		}
		this.msgId = msgId;
	}
	
    @Id
    @RxModelField(text = "消息ID", isID = true, isDisplay = true)
    @Column(name = "msg_id")
    @ExtGridColumn(width=270)
    private String msgId;
    

    @RxModelField(text = "消息类型")
    @Column(name = "msg_type")
    @ExtGridColumn(width=300)
    private String msgType;

    @RxModelField(text = "消息持有者")
    @Column(name = "holder")
    @ExtGridColumn(width=270)
    private String holder;
 
    @RxModelField(text = "独生键")
    @Column(name = "single_key")
    @ExtGridColumn(width=230)
    private String singleKey;
    
    @RxModelField(text = "分组键")
    @Column(name = "group_key")
    @ExtGridColumn(width=230)
    private String groupKey;
    
    @RxModelField(text = "创建时间", datePattern = RxDatePattern.ISO8601Long)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_time")
    @ExtGridColumn()
    private Date createTime;
    
    @RxModelField(text = "消息体")
    @Column(name = "msg_content")
    @ExtGridColumn()
    private String msgContent;

    
    @RxModelField(text = "开始时间", datePattern = RxDatePattern.ISO8601Long)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "begin_time")
    @ExtGridColumn()
    private Date beginTime;
    
    @RxModelField(text = "结束时间", datePattern = RxDatePattern.ISO8601Long)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "end_time")
    @ExtGridColumn()
    private Date endTime;
    
    @RxModelField(text = "错误消息")
    @Column(name = "error_msg")
    @ExtGridColumn()
    private String errorMsg;
    
    
    @Transient
    private boolean cover;

	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	public String getMsgType() {
		return msgType;
	}
	public MsgqPo setMsgType(String msgType) {
		this.msgType = msgType;
		return this;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public MsgqPo setCreateTime(Date createTime) {
		this.createTime = createTime;
		return this;
	}
	public String getMsgContent() {
		return msgContent;
	}
	public MsgqPo setMsgContent(String msgContent) {
		this.msgContent = msgContent;
		return this;
	}
	public String getHolder() {
		return holder;
	}
	public MsgqPo setHolder(String holder) {
		this.holder = holder;
		return this;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public MsgqPo setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
		return this;
	}
	public Date getEndTime() {
		return endTime;
	}
	public MsgqPo setEndTime(Date endTime) {
		this.endTime = endTime;
		return this;
	}
	public String getSingleKey() {
		return singleKey;
	}
	public MsgqPo setSingleKey(String singleKey) {
		this.singleKey = singleKey;
		return this;
	}
	public boolean isCover() {
		return cover;
	}
	public MsgqPo setCover(boolean cover) {
		this.cover = cover;
		return this;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public MsgqPo setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
		return this;
	}
	public String getGroupKey() {
		return groupKey;
	}
	public MsgqPo setGroupKey(String groupKey) {
		this.groupKey = groupKey;
		return this;
	}
    
    
}