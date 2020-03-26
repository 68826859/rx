package com.rx.ext.chart.config;

import com.rx.ext.Config;
import com.rx.ext.annotation.ExtConfig;

public class InnerPadding extends Config {

	
	@ExtConfig
	public Integer top;
	
	@ExtConfig
	public Integer left;
	
	@ExtConfig
	public Integer right;
	
	@ExtConfig
	public Integer bottom;
}
