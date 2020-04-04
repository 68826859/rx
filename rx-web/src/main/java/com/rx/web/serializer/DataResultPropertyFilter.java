package com.rx.web.serializer;

import com.alibaba.fastjson.serializer.PropertyFilter;
import com.rx.base.result.DataResult;

public class DataResultPropertyFilter implements PropertyFilter{

	@Override
	public boolean apply(Object object, String name, Object value) {
		
		if(object instanceof DataResult) {
			if(name.equalsIgnoreCase("code") 
					|| name.equalsIgnoreCase("version")
							|| name.equalsIgnoreCase("alertType")
									|| name.equalsIgnoreCase("alertMsg")
											|| name.equalsIgnoreCase("data")) {
				
			}else {
				return false;
			}
		}
		return true;
	}
}
