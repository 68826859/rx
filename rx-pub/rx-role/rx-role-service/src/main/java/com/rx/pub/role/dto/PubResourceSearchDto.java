package com.rx.pub.role.dto;

import com.rx.base.model.annotation.RxModel;
import com.rx.base.model.annotation.RxModelField;
import com.rx.ext.annotation.ExtClass;
import com.rx.ext.data.Model;
import com.rx.pub.role.po.PubResourcePo;



@RxModel(text = "角色资源查询参数")
@ExtClass(extend = Model.class, alternateClassName = "PubResourceSearchDto")
public class PubResourceSearchDto extends PubResourcePo {
    private static final long serialVersionUID = -40524349813110238L;
    
    @RxModelField(text = "查询多个主键数据")
    private String[] andIdsIn;

    @RxModelField(text = "查询排除多个主键数据")
    private String[] andIdsNotIn;
    
     public String[] getAndIdsIn() {
        return andIdsIn;
    }

    public void setAndIdsIn(String[] andIdsIn) {
        this.andIdsIn = andIdsIn;
    }

    public String[] getAndIdsNotIn() {
        return andIdsNotIn;
    }

    public void setAndIdsNotIn(String[] andIdsNotIn) {
        this.andIdsNotIn = andIdsNotIn;
    }

}