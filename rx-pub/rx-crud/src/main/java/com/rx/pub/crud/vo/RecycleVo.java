package com.rx.pub.crud.vo;

import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtGridColumn;
import com.rx.ext.data.Model;
import com.rx.base.model.annotation.RxModelField;
import com.rx.base.enm.RxDatePattern;

import java.util.Date;

@ExtClass(extend=Model.class)
public class RecycleVo{

    @RxModelField(text = "回收ID")
	@ExtGridColumn(hidden=true)
    private String id;

    @RxModelField(text = "数据类型")
	@ExtGridColumn()
    private String dataClass;

    @RxModelField(text = "数据ID")
    @ExtGridColumn(width=200)
    private String dataId;

    @RxModelField(text = "数据")
	@ExtGridColumn(flex=3)
    private String data;
    
    @RxModelField(text = "操作人")
	@ExtGridColumn()
    private String creater;

    @RxModelField(text = "回收时间", datePattern = RxDatePattern.ISO8601Long)
	@ExtGridColumn()
    private Date createTime;
}