package com.rx.pub.msgq.enm;

import com.rx.base.model.annotation.RxModelField;
import com.rx.base.user.RxPermissionable;

public enum PermissionEnumMsgq implements RxPermissionable{
	
	队列消息列表("msgq_list","队列消息管理","查询队列消息列表"),
	新增队列消息("msgq_add","队列消息管理","新增队列消息项"),
	修改队列消息("msgq_update","队列消息管理","修改队列消息项"),
	删除队列消息("msgq_del","队列消息管理","删除队列消息项");
	
	
	@RxModelField(isID=true)
	private String code;
	
	@RxModelField()
	private String group;
	
	@RxModelField()
	private String desc;
	
	PermissionEnumMsgq(String code,String group,String desc){
		this.code = code;
		this.group = group;
		this.desc = desc;
	}
	

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return code;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.name();
	}

	@Override
	public String getGroup() {
		// TODO Auto-generated method stub
		return group;
	}

	@Override
	public String getDesc() {
		// TODO Auto-generated method stub
		return desc;
	}

	

}
