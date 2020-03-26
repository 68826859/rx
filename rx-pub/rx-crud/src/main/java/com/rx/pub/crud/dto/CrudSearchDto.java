package com.rx.pub.crud.dto;

import java.util.Date;
import com.rx.pub.crud.po.PubCrud;


public class CrudSearchDto extends PubCrud{

	private static final long serialVersionUID = 1L;


	private Date beginTime;
	
	
	private Date endTime;
    
    
    
	public Date getBeginTime() {
	return beginTime;
	}
	public CrudSearchDto setBeginTime(Date beginTime) {
	this.beginTime = beginTime;return this;
	}
	public Date getEndTime() {
	return endTime;
	}
	public CrudSearchDto setEndTime(Date endTime) {
	this.endTime = endTime;return this;
	}
	
	
}
