package com.rx.pub.msgq.enm;

import com.rx.base.Showable;
import com.rx.base.model.annotation.RxModelField;

public enum MsgStatusEnum implements Showable<Integer> {
	就绪(0),
    发送中(1),;
    @RxModelField(isID = true)
    private Integer code;

    MsgStatusEnum(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return this.code;
    }

    @Override
    public String display() {
        return this.name();
    }

    @Override
    public Integer value() {
        return this.code;
    }
}
