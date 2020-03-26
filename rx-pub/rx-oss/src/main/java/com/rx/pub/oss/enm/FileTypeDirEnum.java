package com.rx.pub.oss.enm;

import com.rx.base.file.FileAccessEnum;
import com.rx.spring.utils.PropertiesHelper;

public enum FileTypeDirEnum {

	JPG("jpg", PropertiesHelper.getValue("DIR.IMAGES")),
	PNG("png", PropertiesHelper.getValue("DIR.IMAGES")),
	GIF("gif", PropertiesHelper.getValue("DIR.IMAGES")),
	BMP("bmp", PropertiesHelper.getValue("DIR.IMAGES")),

	MP3("mp3", PropertiesHelper.getValue("DIR.AUDIO")),
	MP4("mp4", PropertiesHelper.getValue("DIR.VIDEO")),

	PDF("pdf", PropertiesHelper.getValue("DIR.PDF")),
	APK("apk", PropertiesHelper.getValue("DIR.APK"));

	/**
	 * 文件后缀
	 */
	private String suffix;
	/**
	 * 对应oss目录
	 */
	private String ossDir;

	FileTypeDirEnum(String suffix, String ossDir) {
		this.suffix = suffix;
		this.ossDir = ossDir;
	}

	public String getSuffix() {
		return suffix;
	}

	public String getOssDir() {
		return ossDir;
	}

	// 根据文件类型选择存储目录
	public static String findOssDirBySuffix(String suffix) {
		for(FileTypeDirEnum e : FileTypeDirEnum.values()) {
			if(e.getSuffix().equals(suffix)) {
				return e.getOssDir();
			}
		}
		return PropertiesHelper.getValue("DIR");
	}

}
