package com.rx.ext.chart;

import java.util.List;

import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtConfig;
import com.rx.ext.chart.axis.Axis;
import com.rx.ext.chart.series.Series;
import com.rx.ext.container.Container;
import com.rx.ext.data.Store;

@ExtClass(alias="widget.abstractchart")
public class AbstractChart extends Container {

	
	@ExtConfig
	public Store<?> store;
	
	
	@ExtConfig
	public List<Axis> axes;
	
	
	@ExtConfig
	public List<Series> series;
}
