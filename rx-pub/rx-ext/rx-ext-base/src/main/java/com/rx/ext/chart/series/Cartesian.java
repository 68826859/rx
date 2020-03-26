package com.rx.ext.chart.series;

import java.util.List;

import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtConfig;
	@ExtClass(alias="series.cartesian")
public class Cartesian extends Series {

	
	
	@ExtConfig
	public String xField;
	
	@ExtConfig
	public String yField;
	
	
	@ExtConfig(key="yField")
	public List<String> yFields;
}
