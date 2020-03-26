package com.rx.base.enm;

import java.util.Objects;

public enum RxDatePattern {
    NULL("", ""),
    ISO8601Long("yyyy-MM-dd HH:mm:ss", "Y-m-d H:i:s"),
    ISO8601Short("yyyy-MM-dd", "Y-m-d"),
    ChineseDate("yyyy年MM月dd日", "Y年m月d日"),
    ShortDate("n/j/Y", "n/j/Y"),
    LongDate("l, F d, Y", "l, F d, Y"),
    FullDateTime("l, F d, Y g:i:s A", "l, F d, Y g:i:s A"),
    MonthDay("F d", "F d"),
    ShortTime("g:i A", "g:i A"),
    LongTime("g:i:s A", "g:i:s A"),
    SortableDateTime("Y-m-d\\TH:i:s", "Y-m-d\\TH:i:s"),
    UniversalSortableDateTime("Y-m-d H:i:sO", "Y-m-d H:i:sO"),
    YearMonth("F, Y", "F, Y");

    private String format;
    private String jsFormat;

    RxDatePattern(String format, String jsFormat) {
        this.format = format;
        this.jsFormat = jsFormat;
    }

    public String getFormat() {
        return this.format;
    }

    public String getJsFormat() {
        return jsFormat;
    }

    public void setJsFormat(String jsFormat) {
        this.jsFormat = jsFormat;
    }
    
    
    
    public static RxDatePattern getByJsFormat(String jsFormat) {
    	RxDatePattern[] ts = RxDatePattern.class.getEnumConstants();
		for(RxDatePattern t :ts) {
			if (Objects.equals(t.getJsFormat(),jsFormat)) {
				return t;
			}
		}
		return RxDatePattern.NULL;
    }
}