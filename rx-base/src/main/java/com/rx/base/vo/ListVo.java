package com.rx.base.vo;

import com.rx.base.model.annotation.RxModelField;

public class ListVo {
	
	public ListVo() {
		
	}
	
	public ListVo(String key, String display) {
		super();
		this.key = key;
		this.display = display;
	}

	@RxModelField(isID=true)
    private String key;

	@RxModelField(isDisplay=true)
    private String display;

    public String getKey() {
        return key;
    }

    public ListVo setKey(String key) {
        this.key = key;return this;
    }

    public String getDisplay() {
        return display;
    }

    public ListVo setDisplay(String display) {
        this.display = display;return this;
    }
}
