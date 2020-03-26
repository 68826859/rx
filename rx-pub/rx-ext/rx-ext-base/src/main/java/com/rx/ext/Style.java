package com.rx.ext;

import com.rx.ext.annotation.ExtConfig;

public class Style extends Config{
	
	@ExtConfig(key="float")
	public String float_;
	
	@ExtConfig()
	public String margin;
	
	@ExtConfig()
	public String padding;
	}
