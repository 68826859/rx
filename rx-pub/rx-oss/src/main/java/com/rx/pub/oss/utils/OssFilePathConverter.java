package com.rx.pub.oss.utils;

import org.springframework.stereotype.Component;

import com.rx.pub.file.base.RxFilePathConverter;
import com.rx.pub.file.base.RxFilePathParam;
import com.rx.pub.oss.utils.OssHelper;

@Component
public class OssFilePathConverter implements RxFilePathConverter {

    public static String STYLE = "x-oss-process=";

	@Override
	public String convert(String path, RxFilePathParam param) {
	    // 绝对路径
	    if(path.startsWith("http://") || path.startsWith("https://")){
	        if(param != null) {
                return path + "?" + STYLE + toStyle(param);
            }else{
	            return path;
            }
        }
        // 相对路径
		return OssHelper.getUrl(path, toStyle(param));
	}

    /**
     * 转换成oss的style
     * @return
     */
	private String toStyle(RxFilePathParam param){
        String style = null;
        // 如果有样式就使用样式
        if(param != null) {
            if (param.getQuery() != null) {
                if (param.getQuery().startsWith(STYLE)) {
                    style = param.getQuery().replace(STYLE, "");
                }
            } else {
                // 如果没有则根据其他属性拼接
                style = "image/resize";
                if (param.getWidth() != null && param.getHeight() != null) {
                    style = style + ",m_fixed,h_" + param.getHeight() + ",w_" + param.getWidth();
                } else if (param.getWidth() != null && param.getHeight() == null) {
                    style = style + ",l_" + param.getWidth();
                } else if (param.getWidth() == null && param.getHeight() != null) {
                    style = style + ",l_" + param.getHeight();
                }
            }
        }
        return style;
    }
}
