package com.rx.ext.draw.sprite;

import com.rx.ext.annotation.ExtClass;
import com.rx.ext.annotation.ExtConfig;
	@ExtClass(alias="sprite.text")
public class Text extends Sprite {

	
	@ExtConfig
	public String text;
	
	@ExtConfig
	public TextAlign textAlign;
	
	@ExtConfig
	public String font;
	
	@ExtConfig
	public Integer x;
	
	
	@ExtConfig
	public Integer y;
	
	@ExtConfig
	public String fontFamily;
	
	public enum TextAlign{
	left, right, center, start, end;
	}
}
