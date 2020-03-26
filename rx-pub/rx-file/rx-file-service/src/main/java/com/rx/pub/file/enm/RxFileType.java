package com.rx.pub.file.enm;

import com.rx.base.Showable;

public enum RxFileType implements Showable<String> {
	
	png("image/png","image"),
	jpe("image/jpeg","image"),
	jpeg("image/jpeg","image"),
	jpg("image/jpeg","image"),
	gif("image/gif","image"),
	
	PNG("application/x-png","application"),
	JPG("application/x-jpg","application");
	
	public String contentType;
	
	public String typeSuffix;
	
	
	
	RxFileType(String contentType,String typeSuffix){
	this.contentType = contentType;
	this.typeSuffix = typeSuffix;
	}
	public String contentType() {
	return contentType;
	}
	@Override
	public String display() {
	return this.name();
	}
	@Override
	public String value() {
	return contentType;
	}

	

}
