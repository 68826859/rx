package com.rx.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.rx.base.result.DataResult;
import com.rx.base.result.type.ExtraException;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//import com.rx.pub.log.enm.LogTypeEumn;
//import com.rx.pub.log.service.impl.RxLogUtil;

public class HttpHelper {
    private static final int TIME_OUT = 10000;

    //private static void staticLog(String url, String param, String result,Integer logType) {
//        RxLogUtil.staticLog(url, param, "RemotAuth", result, logType);
   // }

    public static DataResult doGet(String url, Map<String, Object> param,Class<?> resDataType) {
        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        DataResult res = new DataResult();
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            if (param != null) {
                for (String key : param.keySet()) {
                    Object value = param.get(key);
                    builder.addParameter(key, value == null ? "" : value.toString());
                }
            }
            URI uri = builder.build();
            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(TIME_OUT).setSocketTimeout(TIME_OUT).setConnectionRequestTimeout(TIME_OUT).build();
            httpGet.setConfig(requestConfig);
            // 执行请求
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            
            res = createResult(res,response,resDataType);
        } catch (Exception e) {
        	res = new ExtraException("服务器异常",e);
        } finally {
//            staticLog(url, JSON.toJSONString(param), resultString, LogTypeEumn.请求第三方接口.getCode());
            try {
                if (response != null) {
                    response.close();
                }
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    
    private static DataResult createResult(DataResult res,CloseableHttpResponse response,Class<?> resDataType) throws ParseException, IOException {
    	res.setCode(response.getStatusLine().getStatusCode());
        
        HttpEntity entity = response.getEntity();
        
        if(entity != null) {
        	String resultString = EntityUtils.toString(entity, "UTF-8");
        	if(resDataType == null || resDataType == String.class) {
        		res.setData(resultString);
        	}else {
        		JSONObject json = JSONObject.parseObject(resultString,Feature.DisableSpecialKeyDetect);
        		String type = json.getString("@type");
        		if(type != null && type.startsWith("com.rx.base.result")) {
        			if(DataResult.class.isAssignableFrom(resDataType)) {
        				return (DataResult)JSONObject.parseObject(resultString,resDataType,Feature.DisableSpecialKeyDetect);
        			}else {
        				DataResult ds = JSONObject.parseObject(resultString,DataResult.class,Feature.DisableSpecialKeyDetect);
        				ds.setAlertMsg(json.getString("alertMsg"));
        				if(ds.getCode() == 200) {
        					ds.setData(JSONObject.parseObject(json.getString("data"),resDataType,Feature.DisableSpecialKeyDetect));
        				}
        				return ds;
        			}
        		}
        		res.setData(JSONObject.parseObject(resultString,resDataType));
        	}
        }
        return res;
    }
    
    public static DataResult doGet(String url) {
        return doGet(url, null,String.class);
    }

    public static DataResult doPost(String url, Map<String, Object> param,Class<?> resDataType) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        
        DataResult res = new DataResult();
        
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(TIME_OUT).setSocketTimeout(TIME_OUT).setConnectionRequestTimeout(TIME_OUT).build();
            httpPost.setConfig(requestConfig);
            // 创建参数列表
            if (param != null) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (String key : param.keySet()) {
                    Object value = param.get(key);
                    if(value == null) {
                    	paramList.add(new BasicNameValuePair(key,""));
                    }else if(value.getClass().isArray()) {
                    	int len = Array.getLength(value);
                    	if(len > 0) {
                    		for(int i=0;i<len;i++) {
                    			Object o = Array.get(value, i);
                    			if(o == null) {
                    				paramList.add(new BasicNameValuePair(key,""));
                    			}else {
                    				paramList.add(new BasicNameValuePair(key,o.toString()));
                    			}
                    		}
                    	}
                    }else {
                    	paramList.add(new BasicNameValuePair(key,value.toString()));
                    }
                }
                // 模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, "utf-8");
                httpPost.setEntity(entity);
            }
            // 执行http请求
            response = httpClient.execute(httpPost);
            res = createResult(res,response,resDataType);
        } catch (Exception e) {
        	res = new ExtraException("服务器异常",e);
        } finally {
//            staticLog(url, JSON.toJSONString(param), resultString, LogTypeEumn.请求第三方接口.getCode());
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return res;
    }

    public static DataResult doPost(String url) {
        return doPost(url, null,String.class);
    }
    
    public static DataResult doPostJson(String url, String json, Map<String, String> header,Class<?> resDataType) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        DataResult res = new DataResult();
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            RequestConfig requestConfig = RequestConfig
                    .custom()
                    .setConnectTimeout(TIME_OUT)
                    .setSocketTimeout(TIME_OUT)
                    .setConnectionRequestTimeout(TIME_OUT)
                    .build();
            httpPost.setConfig(requestConfig);
            // 创建请求内容
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);
            if (header != null){
                for (Map.Entry<String, String> head : header.entrySet()){
                    httpPost.addHeader(head.getKey(), head.getValue());
                }
            }
            // 执行http请求
            response = httpClient.execute(httpPost);
            res = createResult(res,response,resDataType);
        } catch (Exception e) {
        	res = new ExtraException("服务器异常",e);
        } finally {
//            staticLog(url, json, resultString, LogTypeEumn.请求第三方接口.getCode());
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    public static DataResult doPostJson(String url, String json) {
        return doPostJson(url, json, null,null);
    }
    
    public static DataResult doPostJson(String url, JSONObject json) {
        return doPostJson(url, json.toJSONString());
    }

    public static DataResult doPostJson(String url, JSONObject json, Map<String, String> header) {
        return doPostJson(url, json.toJSONString(), header,null);
    }

    public static byte[] getRequestPostBytes(HttpServletRequest request)
            throws IOException {
        int contentLength = request.getContentLength();
        if(contentLength < 0){
            return new byte[0];
        }
        byte[] buffer = new byte[contentLength];
        int i = 0;
        while (i < contentLength) {
            int readlen = request.getInputStream().read(
                    buffer,
                    i,
                    contentLength - i);
            if (readlen == -1) {
                break;
            }
            i += readlen;
        }
        return buffer;
    }



	}
