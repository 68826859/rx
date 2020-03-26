package com.rx.pub.file.dto;
	/**
* 
**/
public class UploadDto {


	private String value;
    
    private String src;

    private String fileId;

    public UploadDto() {
	super();
	}
    
    public UploadDto(String value, String src) {
	super();
	this.value = value;
	this.src = src;
	}
	public String getValue() {
	return value;
	}
	public UploadDto setValue(String value) {
	this.value = value;return this;
	}
	public String getSrc() {
	return src;
	}
	public UploadDto setSrc(String src) {
	this.src = src;return this;
	}
	public String getFileId() {
	return fileId;
	}
	public UploadDto setFileId(String fileId) {
	this.fileId = fileId;
	return this;
	}
}
