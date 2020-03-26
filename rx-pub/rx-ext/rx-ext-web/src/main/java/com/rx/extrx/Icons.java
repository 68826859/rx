package com.rx.extrx;

import org.springframework.web.context.ContextLoader;

import com.rx.ext.annotation.ExtClass;
import com.rx.spring.utils.SpringContextHelper;
import com.rx.base.model.annotation.RxModelField;

@ExtClass(alternateClassName="Rx.Icons")
public enum Icons{
	png_16_1("resources/images/icons/1.png"),
	png_16_2("resources/images/icons/2.png"),
	png_16_3("resources/images/icons/3.png"),
	png_16_4("resources/images/icons/4.png"),
	png_16_5("resources/images/icons/5.png"),
	png_16_6("resources/images/icons/6.png"),
	png_16_7("resources/images/icons/7.png"),
	png_16_8("resources/images/icons/8.png"),
	png_16_9("resources/images/icons/9.png"),
	png_16_10("resources/images/icons/10.png"),
	png_16_11("resources/images/icons/11.png"),
	png_16_12("resources/images/icons/12.png"),
	png_16_13("resources/images/icons/13.png"),
	png_16_14("resources/images/icons/14.png"),
	png_16_15("resources/images/icons/15.png"),
	png_16_16("resources/images/icons/16.png"),
	png_16_17("resources/images/icons/17.png"),
	png_16_18("resources/images/icons/18.png"),
	png_16_19("resources/images/icons/19.png"),
	png_16_20("resources/images/icons/20.png"),
	png_16_21("resources/images/icons/21.png"),
	png_16_22("resources/images/icons/22.png"),
	png_16_23("resources/images/icons/23.png"),
	png_16_24("resources/images/icons/24.png"),
	png_16_25("resources/images/icons/25.png"),
	png_16_26("resources/images/icons/26.png"),
	png_16_27("resources/images/icons/27.png"),
	png_16_28("resources/images/icons/28.png"),
	png_16_29("resources/images/icons/29.png"),
	png_16_30("resources/images/icons/30.png"),
	png_16_31("resources/images/icons/31.png"),
	png_16_32("resources/images/icons/32.png"),
	png_16_33("resources/images/icons/33.png"),
	png_16_34("resources/images/icons/34.png"),
	png_16_35("resources/images/icons/35.png"),
	png_16_36("resources/images/icons/36.png"),
	png_16_37("resources/images/icons/37.png"),
	png_16_38("resources/images/icons/38.png"),
	png_16_39("resources/images/icons/39.png"),
	png_16_40("resources/images/icons/40.png"),
	png_16_41("resources/images/icons/41.png"),
	png_16_42("resources/images/icons/42.png"),
	png_16_43("resources/images/icons/43.png"),
	png_16_44("resources/images/icons/44.png"),
	png_16_45("resources/images/icons/45.png"),
	png_16_46("resources/images/icons/46.png"),
	png_16_47("resources/images/icons/47.png"),
	png_16_48("resources/images/icons/48.png");
	
	@RxModelField(isDisplay=true)
	private String path;
	
	Icons(String path){
		this.setPath(path);
	}
	public String getPath() {
		return path;
	}
	private String getServletContext(){
		
		//SpringContextHelper.springContext
		if(ContextLoader.getCurrentWebApplicationContext() != null && ContextLoader.getCurrentWebApplicationContext().getServletContext() != null){
				return ContextLoader.getCurrentWebApplicationContext().getServletContext().getContextPath();
		}
		return "";
	} 
	public void setPath(String path) {
		this.path = getServletContext() + path;
	}
}
