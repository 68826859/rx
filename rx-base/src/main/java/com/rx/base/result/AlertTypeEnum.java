package com.rx.base.result;

import com.rx.base.Showable;
import com.rx.base.model.annotation.RxModelField;

public enum AlertTypeEnum implements Showable<Integer> {
	不弹框0(0),
	无需关闭的提示1(1),
	需要关闭的提示2(2),
	无需关闭的错误3(3),
	需要关闭的错误4(4),
	弹出确认警告5(5),
    ;
    @RxModelField(isID = true)
    private Integer code;
    AlertTypeEnum(Integer code){
        this.code = code;
    }
    public Integer getCode(){
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