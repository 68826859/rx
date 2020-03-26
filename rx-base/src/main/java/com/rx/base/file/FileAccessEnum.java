package com.rx.base.file;

import com.rx.base.Showable;

public enum FileAccessEnum implements Showable<Integer>  {

	公共读写(0),
	私有(1),
	公共读(2),
	;
	
	
	FileAccessEnum(int code) {
		this.code = code;
	}
	
	@Override
	public String display() {
        return this.name();
	}

	@Override
	public Integer value() {
        return this.code;
	}

	private Integer code;

	public Integer getCode() {
		return this.code;
	}
	
	public static FileAccessEnum findByValue(Integer value, FileAccessEnum defaultE) {
		if (value != null) {
			for (FileAccessEnum e : FileAccessEnum.values()) {
				if (e.getCode().equals(value)) {
					return e;
				}
			}
		}
		return defaultE;
	}

	
}
