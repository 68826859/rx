package com.rx.ext.chart.model;

import com.rx.ext.annotation.ExtClass;
import com.rx.base.model.annotation.RxModel;
import com.rx.ext.data.Model;
import com.rx.base.model.annotation.RxModelField;


/**

 */
@RxModel(text = "图表模型")
@ExtClass(extend = Model.class, alternateClassName = "ChartFloatModel")
public class ChartFloatModel{

    @RxModelField(text = "名")
    private String name;
    
    @RxModelField(text = "值")
    private Float value;
    
    @RxModelField(text = "值1")
    private Float value1;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float getValue() {
		return value;
	}

	public void setValue(Float value) {
		this.value = value;
	}

	public Float getValue1() {
		return value1;
	}

	public void setValue1(Float value1) {
		this.value1 = value1;
	}


}