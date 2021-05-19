package com.rx.sdk;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rx.base.cache.CacheHelper;
import com.rx.base.utils.StringUtil;

public class SdkUtil {
	private static final Logger log = LoggerFactory.getLogger(SdkUtil.class);
	
	
	public static String CharsetName = "UTF-8";
	
	static class miTM implements TrustManager, X509TrustManager {
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}
		public boolean isServerTrusted(X509Certificate[] certs) {
			return true;
		}
		public boolean isClientTrusted(X509Certificate[] certs) {
			return true;
		}
		public void checkServerTrusted(X509Certificate[] certs, String authType) throws CertificateException {
		}
		public void checkClientTrusted(X509Certificate[] certs, String authType) throws CertificateException {
		}
	}
	
	private static void trustAllHttpsCertificates() throws Exception {
		TrustManager[] trustAllCerts = new TrustManager[1];
		TrustManager tm = new miTM();
		trustAllCerts[0] = tm;
		SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, trustAllCerts, null);
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
	}
	
	
	private static void ignoreSsl() throws Exception {
		HostnameVerifier hv = new HostnameVerifier() {
			public boolean verify(String urlHostName, SSLSession session) {
				System.out.println("Warning: URL Host: " + urlHostName + " vs. " + session.getPeerHost());
				return true;
			}
		};
		trustAllHttpsCertificates();
		HttpsURLConnection.setDefaultHostnameVerifier(hv);
	}
	
	
	public static<T> T postUrlEncode(String urlStr,Map<String,Object> data,Class<T> clazz,Type type) throws Exception {
		
		String dataString = null;
		if(data == null) {
			dataString = "";
		}else {
			dataString = mapToUrl(data,true);
		}
		String resString = post(urlStr,dataString,"application/x-www-form-urlencoded");
		return parseResult(resString,clazz,type);
	}
	
	public static<T> T postJson(String urlStr,Object data,Class<T> clazz,Type type) throws Exception {
		
		String dataString = null;
		if(data == null) {
			dataString = "";
		} else if(data instanceof String) {
			dataString = (String)data;
		}else {
			dataString = JSON.toJSONString(data);
		}
		String resString = post(urlStr,dataString,"application/json");
		return parseResult(resString,clazz,type);
	}
	
	public static<T> T parseResult(String resString,Class<T> clazz,Type type) {
		
		JSONObject serverResponse = JSONObject.parseObject(resString);
		
		int status = serverResponse.getIntValue("code");
        if (status != 200){
            throw new SdkException(status,serverResponse.getObject("alertMsg",String.class));
       }
       //List<PermissionItem> permissionItems = jsonObject1.toJavaObject(typePermissionItem);
       if(clazz != null) {
    	   return serverResponse.getObject("data",clazz);
       }
       if(type != null) {
    	   return serverResponse.getObject("data",type);
       }
       return null;
	}  
	
	public static String post(String urlStr,String data,String contentType) throws Exception {

		Long timeStamp = Long.valueOf(System.currentTimeMillis());
		String newUrl = appendUrl(urlStr,"_",timeStamp.toString(),false);
		URL url = new URL(newUrl);
		if ("https".equalsIgnoreCase(url.getProtocol())) {
			ignoreSsl();
		}
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setRequestMethod("POST");
		conn.setUseCaches(false);
		conn.setConnectTimeout(30000);
		conn.setReadTimeout(30000);
		conn.setInstanceFollowRedirects(true);
		conn.setRequestProperty("Content-Type",contentType);
		conn = setHeaders(conn);
		DataOutputStream out = new DataOutputStream(conn.getOutputStream());
		if(data == null) {
			data = "";
		}
		log.info("[" + timeStamp + "]SDK URL:" + newUrl + ",DATA:" + data);
		out.write(data.getBytes(CharsetName));
		out.flush();
		out.close();

		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), CharsetName));

		StringBuffer res = new StringBuffer();
		String lines;
		while ((lines = br.readLine()) != null) {
			res.append(lines);
		}
		br.close();
		conn.disconnect();
		
		String resString = res.toString();
		log.info("[" + timeStamp + "]SDK POST RESULT:" + resString);

		
		return resString;
		
	}
	public static<T> T getUrlEncode(String urlStr,Map<String,Object> data,Class<T> clazz,Type type) throws Exception {
		String resString = get(urlStr,data);
		return parseResult(resString,clazz,type);
	}
	
	public static String get(String urlStr,Map<String,Object> data) throws Exception {
		
		String newUrl = urlStr;
		
		Long timeStamp = Long.valueOf(System.currentTimeMillis());
		if(data == null) {
			newUrl = appendUrl(newUrl,"_",timeStamp.toString(),false);
		}else {
			data.put("_", timeStamp);
			newUrl = appendUrl(urlStr,data,true);
		}
		
		URL url = new URL(newUrl);
		if ("https".equalsIgnoreCase(url.getProtocol())) {
			ignoreSsl();
		}
		log.info("[" + timeStamp + "]SDK GET URL:" + newUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn = setHeaders(conn);
		conn.connect();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(),CharsetName));

		StringBuffer res = new StringBuffer();
		String lines;
		while ((lines = br.readLine()) != null) {
			res.append(lines);
		}
		br.close();
		conn.disconnect();
		
		String resString = res.toString();
		log.info("[" + timeStamp + "]SDK GET RESULT:" + resString);
		return resString;
	}
	
	
	
    public static String appendUrl(String url, Map<String, Object> data,boolean encodeValue) throws Exception {
        if (url.indexOf("?") >= 0) {
        	return url + "&" + mapToUrl(data,true);
        } else {
        	return url + "?" + mapToUrl(data,true);
        }
    }
    public static String appendUrl(String url, String key,String value,boolean encodeValue) throws Exception {
    	if(value != null) {
	        if (url.indexOf("?") >= 0) {
	        	return url + "&" + key + "=" + (encodeValue?URLEncoder.encode(value, CharsetName):value);
	        } else {
	        	return url + "?" + key + "=" + (encodeValue?URLEncoder.encode(value,CharsetName):value);
	        }
    	}
    	return url;
    }
    
    public static String mapToUrl(Map<String, Object> data,boolean encodeValue) throws Exception {
        if(data.isEmpty()) {
        	return null;
        }
    	StringBuffer param = new StringBuffer();
    	Object value = null;
        for (String key : data.keySet()) {
        	value = data.get(key);
        	if(value != null) {
        		param.append(key).append("=");
            	param.append(encodeValue?URLEncoder.encode(data.get(key).toString(),CharsetName):value.toString());
            	param.append("&");
        	}
        }
        return param.substring(0, param.length() - 1);
    }
    
	

	private static HttpURLConnection setHeaders(HttpURLConnection conn) throws Exception {
		String nonce = getRandomFileName(Integer.valueOf(32));
		Long curTime = Long.valueOf(System.currentTimeMillis() / 1000L);
		
		String businessId = SdkProfileConfig.getConfig(SdkProfileConfig.Profile, SdkProfileConfig.BusType_businessId);
		String businessSecret = SdkProfileConfig.getConfig(SdkProfileConfig.Profile, SdkProfileConfig.BusType_businessSecret);
		
		String checkSum = CheckSumBuilder.getCheckSum(businessSecret, nonce, curTime.toString());
		conn.setRequestProperty("businessId", businessId);
		conn.setRequestProperty("Nonce", nonce);
		conn.setRequestProperty("CurTime", curTime.toString());
		conn.setRequestProperty("CheckSum", checkSum);
		return conn;
	}
	
	
	public static void validate(HttpServletRequest request,SecretProvider secretProvider) throws Exception {
		String businessId = request.getHeader("businessId");
    	String nonce = request.getHeader("Nonce");
    	String curTime = request.getHeader("CurTime");
    	String checkSum = request.getHeader("CheckSum");
    	if(StringUtil.noNull(businessId) && StringUtil.noNull(nonce) && StringUtil.noNull(curTime) && StringUtil.noNull(checkSum)) {
    		String businessSecret = CacheHelper.getCacher().getString("business_secret_"+businessId);
    		if(StringUtil.isNull(businessSecret)) {
    			businessSecret = secretProvider.getSecret(businessId);
    			if(businessSecret == null) {
    				throw new SdkException(398,"商户号密钥信息不正确");
    			}
	    		CacheHelper.getCacher().put("business_secret_"+businessId, businessSecret,10*60*1000);
    		}
    		if(StringUtil.noNull(businessSecret)) {
    			String myCheckSum = CheckSumBuilder.getCheckSum(businessSecret, nonce, curTime.toString());
    	    	if(Objects.equals(checkSum, myCheckSum)){
    	    		return;
    	    	}else {
    	    		throw new SdkException(398,"非法访问,businessId:" + businessId + "验证密钥不正确");
    	    	}
    		}
    	}
    	throw new SdkException(398,"非法访问");
	}
	
	
	public static String getRandomFileName(Integer number) {
		String uuid = getUUID();
		Random random = new Random();
		Integer i = Integer.valueOf(0);
		if (number.intValue() < 32) {
			i = Integer.valueOf(random.nextInt(32 - number.intValue()));
		}
		return uuid.substring(i.intValue(), i.intValue() + number.intValue());
	}

	public static String getUUID() {
		String uuid = UUID.randomUUID().toString();
		return uuid.replaceAll("-", "");
	}
}
