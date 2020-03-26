package com.rx.pub.file.dto;

import com.rx.pub.file.po.RxFile;

	/**
* 
**/
public class RxFileDto extends RxFile{

	private String typeSuffix;
	
	private String[] contentTypes;

    
    public RxFileDto() {
	super();
	}

	public String getTypeSuffix() {
	return typeSuffix;
	}

	public void setTypeSuffix(String typeSuffix) {
	this.typeSuffix = typeSuffix;
	}

	public String[] getContentTypes() {
	return contentTypes;
	}

	public void setContentTypes(String[] contentTypes) {
	this.contentTypes = contentTypes;
	}
    
    
}
