package com.rx.pub.file.vo;

public class UploadParam {
	
	
	private Integer fileAccess;
	private String filePrefix;
	private String typeSuffix;
	private String[] contentTypes;
	private String fileValidateMsg;
	private Integer width;
	private Integer height;
	private String reuseKey;//重复使用缓存的key
	boolean fullPath;
	
	public Integer getFileAccess() {
		return fileAccess;
	}
	public void setFileAccess(Integer fileAccess) {
		this.fileAccess = fileAccess;
	}
	public String getFilePrefix() {
		return filePrefix;
	}
	public void setFilePrefix(String filePrefix) {
		this.filePrefix = filePrefix;
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
	public String getFileValidateMsg() {
		return fileValidateMsg;
	}
	public void setFileValidateMsg(String fileValidateMsg) {
		this.fileValidateMsg = fileValidateMsg;
	}
	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	public boolean isFullPath() {
		return fullPath;
	}
	public void setFullPath(boolean fullPath) {
		this.fullPath = fullPath;
	}
	public String getReuseKey() {
		return reuseKey;
	}
	public void setReuseKey(String reuseKey) {
		this.reuseKey = reuseKey;
	}
}
