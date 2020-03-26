package com.rx.pub.oss.enm;

public enum FileTypeEnum {

    JPG(".jpg"),PNG(".png"),GIF(".gif"),JPEG(".jpeg"),BMP(".bmp"),APK(".apk");

    private String type;

    FileTypeEnum(){}

    FileTypeEnum(String type){
        this.type = type;
    }


    public String type(){
        return this.type;
    }
    
    public static FileTypeEnum findByValue(String value, FileTypeEnum defaultE) {
		for(FileTypeEnum e : FileTypeEnum.values()) {
			if(e.type().toLowerCase().contains(value.toLowerCase())) {
			//if(e.type().equalsIgnoreCase(value)) {
				return e;
			}
		}
		return defaultE;
	}
    
    /**
	 * 获取文件后缀
	 * @param srcFileName
	 * @return null 系统不支持此后缀
	 */
    public static FileTypeEnum suffixFileName(String srcFileName) {
    	try{
			int suffIndex = srcFileName.lastIndexOf(".");
			if(-1!=suffIndex){
				String suffix = srcFileName.substring(suffIndex+1).trim();//后缀
				if(suffix.length()<=1) return null;
				return FileTypeEnum.valueOf(suffix.toUpperCase());
			}else{
				return null;
			}
		}catch(Exception e){
			return null;
		}
    }
}
