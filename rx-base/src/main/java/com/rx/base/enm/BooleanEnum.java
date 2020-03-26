package com.rx.base.enm;

import com.rx.base.Showable;
import com.rx.base.model.annotation.RxModelField;

/**
 * @author Ray
 * @date 2019/2/25
 */
public enum BooleanEnum implements Showable<Integer>  {

    是(1),
    否(0);

    @RxModelField(isID = true)
    private Integer code;

    BooleanEnum(Integer code) {
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

    public static BooleanEnum findByValue(Integer value, BooleanEnum defaultE) {
    	return  EnumUtil.valueOf(BooleanEnum.class,value,defaultE);
    }
}
