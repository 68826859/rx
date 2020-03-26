package com.rx.base.vo;

import com.rx.base.model.annotation.RxModelField;

public class TreeVo extends ListVo{

	
	@RxModelField(isParentId=true)
    private String parentId;

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
