package com.rx.pub.dic.utils;

import com.rx.base.model.annotation.RxModelField;
import com.rx.ext.annotation.ExtClass;
import com.rx.ext.data.Model;

@ExtClass(extend = Model.class)
public class DictionaryEntity {
	
	@RxModelField(isID = true)
	private String id;
	
	@RxModelField(text = "名称",isDisplay = true)
	private String name;
	
	@RxModelField(text="值")
	private String value;
	
	@RxModelField(text = "所属")
	private String parentId;

	
	@RxModelField(text = "描述")
	private Integer seq;
	
	@RxModelField(text = "是否静态")
	private Boolean staticed;
	
	public DictionaryEntity() {
		
	}

	public DictionaryEntity(String id, String name, String value, String parentId,Integer seq, boolean staticed) {
		this.id = id;
		this.name = name;
		this.value = value;
		this.parentId = parentId;
		this.seq = seq;
		this.staticed = Boolean.valueOf(staticed);
	}

	public String toJS() {
		return null;
		//return "{name:'" + name + "',code:'" + code + "',group:'" + group + "',desc:'" + desc + "'}";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public Boolean getStaticed() {
		return staticed;
	}

	public void setStaticed(Boolean staticed) {
		this.staticed = staticed;
	}


}
