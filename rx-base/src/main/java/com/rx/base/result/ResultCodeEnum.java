package com.rx.base.result;

import com.rx.base.Showable;
import com.rx.base.model.annotation.RxModelField;

public enum ResultCodeEnum implements Showable<Integer> {
	成功(200),
	未注册(394),
	未登录(399),
	验证异常(499),
	业务异常(599),
	权限异常(403),
	系统异常(999),
    ;
	
	//200 成功 394未注册 395实名认证不通过 396实名认证审核中 397系统未初始化 398实名认证异常 399未登录 499验证异常 599业务异常 403权限异常 999系统异常
	
    @RxModelField(isID = true)
    private Integer code;
    ResultCodeEnum(Integer code){
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