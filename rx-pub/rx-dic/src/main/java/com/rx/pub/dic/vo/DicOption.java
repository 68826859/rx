package com.rx.pub.dic.vo;

import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtGridColumn;
import com.rx.ext.data.Model;
import com.rx.base.model.annotation.RxModelField;

import javax.persistence.Column;
import java.io.Serializable;

@ExtClass(extend=Model.class)
public class DicOption implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@RxModelField(isID = true)
	@ExtGridColumn(text="值")
    private String value;
	
	@RxModelField(text = "名称", isDisplay = true)
	@ExtGridColumn()
    private String name;

	@Column(name = "seq")
    private Integer seq;
    
	public String getName() {
	return name;
	}
	public void setName(String name) {
	this.name = name;
	}
	public String getValue() {
	return value;
	}
	public void setValue(String value) {
	this.value = value;
	}
    
	public Integer getSeq() {
	return seq;
	}
	public void setSeq(Integer seq) {
	this.seq = seq;
	}

	}
