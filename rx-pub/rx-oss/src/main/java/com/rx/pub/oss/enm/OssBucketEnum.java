package com.rx.pub.oss.enm;

import com.rx.base.Showable;
import com.rx.base.file.FileAccessEnum;

public enum OssBucketEnum implements Showable<Integer> {

	nnscpublic(0,FileAccessEnum.公共读写,"bjcj-read/","bjcj-read","https://bjcj-read.oss-cn-shenzhen.aliyuncs.com"),
	nnsc(1,FileAccessEnum.私有,"bjcj-pri/","bjcj-pri","https://bjcj-pri.oss-cn-shenzhen.aliyuncs.com"),
	nnstatic(2,FileAccessEnum.公共读,"fyt/","fyt-test-read","https://fyt-test-read.oss-cn-chengdu.aliyuncs.com"),
	;
	
	OssBucketEnum(Integer code, FileAccessEnum access, String prefix, String bucketName, String bucketDomain) {
		this.code = code;
		this.access = access;
		this.prefix = prefix;
		this.bucketName = bucketName;
		this.bucketDomain = bucketDomain;
	}

	private Integer code;	
	
	private FileAccessEnum access;	//访问权限

    private String prefix;	//文件前缀
    
    private String bucketName;	//Bucket名称
    
    private String bucketDomain; //Bucket 域名

	@Override
	public String display() {
		return this.name();
	}

	@Override
	public Integer value() {
		return this.code;
	}
    
	public FileAccessEnum getAccess() {
		return access;
	}

	public String getPrefix() {
		return prefix;
	}

	public String getBucketName() {
		return bucketName;
	}

	public String getBucketDomain() {
		return bucketDomain;
	}
    
	public static OssBucketEnum findByValue(String value, OssBucketEnum defaultE) {
		for(OssBucketEnum e : OssBucketEnum.values()) {
			if(e.getBucketName().equals(value)) {
				return e;
			}
		}
		return defaultE;
	}

	// 根据访问权限选择存储空间bucket
	public static OssBucketEnum findByFileAccessEnum(FileAccessEnum accessEnum) {
		for(OssBucketEnum e : OssBucketEnum.values()) {
			if(e.getAccess().equals(accessEnum)) {
				return e;
			}
		}
		return OssBucketEnum.nnstatic;
	}
	
	// 根据文件名objectName查询存储空间bucket
	public static OssBucketEnum findByObjectName(String objectName) {
		for(OssBucketEnum e : OssBucketEnum.values()) {
			if(objectName.startsWith(e.prefix)) {
				return e;
			}
		}
		return OssBucketEnum.nnstatic;
	}
}
