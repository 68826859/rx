package com.rx.ext.chart.series;

import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtConfig;

@ExtClass(alias="series.stackedcartesian")
public class StackedCartesian extends Cartesian {

	
	@ExtConfig
	public Boolean fullStack;
	
	@ExtConfig
	public Boolean fullStackTotal;
	
	@ExtConfig
	public Boolean splitStacks;
	
	@ExtConfig
	public Boolean stacked;
}
