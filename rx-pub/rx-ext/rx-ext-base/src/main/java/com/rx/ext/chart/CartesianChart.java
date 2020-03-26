package com.rx.ext.chart;

import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtConfig;
import com.rx.ext.chart.config.InnerPadding;

/**
 * 笛卡尔坐标图表
 * @author Ray
 *
 */
@ExtClass(alias="widget.cartesian")
public class CartesianChart extends AbstractChart {

	
	@ExtConfig
	public Boolean flipXY;
	
	
	@ExtConfig
	public InnerPadding innerPadding;
}
