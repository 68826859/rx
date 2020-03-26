package com.rx.common.httpclient;

import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.FileBody;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * httpclient 工具类
 */
public class HttpHelper {

	private static Integer socketTimeout = 50;
	private static Integer connectTimeout = 6000;
	private static Integer connectionRequestTimeout = 50;

	/**
	 * 使用Get方式 根据URL地址，获取ResponseContent对象
	 * 
	 * @param url
	 *            完整的URL地址
	 * @return ResponseContent 如果发生异常则返回null，否则返回ResponseContent对象
	 */
	public static ResponseContent getUrlRespContent(String url) {
		HttpClientWrapper hw = new HttpClientWrapper(connectionRequestTimeout,connectTimeout, socketTimeout);
		ResponseContent response = null;
		try {
			response = hw.getResponse(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	/**
	 * 使用Get方式 根据URL地址，获取ResponseContent对象
	 * 
	 * @param url
	 *            完整的URL地址
	 * @param urlEncoding
	 *            编码，可以为null
	 * @return ResponseContent 如果发生异常则返回null，否则返回ResponseContent对象
	 */
	public static ResponseContent getUrlRespContent(String url,
			String urlEncoding) {
		HttpClientWrapper hw = new HttpClientWrapper(connectionRequestTimeout,
				connectTimeout, socketTimeout);
		ResponseContent response = null;
		try {
			response = hw.getResponse(url, urlEncoding);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	/**
	 * 将参数拼装在url中，进行post请求。
	 * 
	 * @param url
	 * @return
	 */
	public static ResponseContent postUrl(String url) {
		HttpClientWrapper hw = new HttpClientWrapper();
		ResponseContent ret = null;
		try {
			setParams(url, hw);
			ret = hw.postNV(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	private static void setParams(String url, HttpClientWrapper hw) {
		String[] paramStr = url.split("[?]", 2);
		if (paramStr == null || paramStr.length != 2) {
			return;
		}
		String[] paramArray = paramStr[1].split("[&]");
		if (paramArray == null) {
			return;
		}
		for (String param : paramArray) {
			if (param == null || "".equals(param.trim())) {
				continue;
			}
			String[] keyValue = param.split("[=]", 2);
			if (keyValue == null || keyValue.length != 2) {
				continue;
			}
			hw.addNV(keyValue[0], keyValue[1]);
		}
	}

	/**
	 * 上传文件（包括图片）
	 * 
	 * @param url
	 *            请求URL
	 * @param paramsMap
	 *            参数和值
	 * @return
	 */
	public static ResponseContent postEntity(String url,Map<String, Object> paramsMap) {
		HttpClientWrapper hw = new HttpClientWrapper();
		ResponseContent ret = null;
		try {
			setParams(url, hw);
			Iterator<String> iterator = paramsMap.keySet().iterator();
			while (iterator.hasNext()) {
				String key = iterator.next();
				Object value = paramsMap.get(key);
				if (value instanceof File) {
					FileBody fileBody = new FileBody((File) value);
					hw.getContentBodies().add(fileBody);
				} else if (value instanceof byte[]) {
					byte[] byteVlue = (byte[]) value;
					ByteArrayBody byteArrayBody = new ByteArrayBody(byteVlue,
							key);
					hw.getContentBodies().add(byteArrayBody);
				} else {
					if (value != null && !"".equals(value)) {
						hw.addNV(key, String.valueOf(value));
					} else {
						hw.addNV(key, "");
					}
				}
			}
			ret = hw.postEntity(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * 使用post方式，发布对象转成的json给Rest服务。
	 * 
	 * @param url
	 * @param jsonBody
	 * @return
	 */
	public static ResponseContent postJsonEntity(String url, String jsonBody) {
		return postEntity(url, jsonBody, "application/json;charset=utf-8");
	}

	/**
	 * 使用post方式，发布对象转成的xml给Rest服务
	 * 
	 * @param url
	 *            URL地址
	 * @param xmlBody
	 *            xml文本字符串
	 * @return ResponseContent 如果发生异常则返回空，否则返回ResponseContent对象
	 */
	public static ResponseContent postXmlEntity(String url, String xmlBody) {
		return postEntity(url, xmlBody, "application/xml");
	}

	public static ResponseContent postEntity(String url, String body,
			String contentType) {
		HttpClientWrapper hw = new HttpClientWrapper();
		ResponseContent ret = null;
		try {
			hw.addNV("body", body);
			ret = hw.postNV(url, contentType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static void main(String[] args) {
		testGet();
//		 testUploadFile();
		
//		String str = "abc/ddd?a=b&c=d";
//		str="(sssa)";
//		Pattern p = Pattern.compile("(?=a)");
//		Matcher m = p.matcher(str);
//		while(m.find()){
//			System.out.println(m.group());
//		}
		
	}

	// test
	public static void testGet() {
		String url = "http://www.baidu.com/";
		ResponseContent responseContent = getUrlRespContent(url);
		try {
			System.out.println(responseContent.getContent());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	// test
	public static void testUploadFile() {
		try {
			String url = "http://192.168.0.38:8080/FileCenter/upload";
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			paramsMap.put("userName", "xxx");
			paramsMap.put("password", "jj");
			paramsMap.put("file", new File("/Users/mo/Desktop/1.jpg"));
			ResponseContent ret = postEntity(url, paramsMap);
			System.out.println(ret.getContent());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}