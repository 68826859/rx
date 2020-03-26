package com.rx.pub.oss.utils;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.comm.Protocol;
import com.aliyun.oss.common.utils.IOUtils;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sts.model.v20150401.AssumeRoleRequest;
import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;
import com.rx.base.cache.CacheHelper;
import com.rx.base.file.FileAccessEnum;
import com.rx.base.result.type.BusinessException;
import com.rx.pub.oss.enm.FileTypeDirEnum;
import com.rx.pub.oss.enm.FileTypeEnum;
import com.rx.pub.oss.enm.OssBucketEnum;
import com.rx.spring.utils.PropertiesHelper;
import com.rx.spring.utils.SpringContextHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description: OSS上传
 * @Param:
 * @date: 2018/9/30
 */
public class OssHelper {

	
	
    private final static String endpoint = "ENDPOINT";
    private final static String accessKeyId = "ACCESS_KEY_ID";
    private final static String accessKeySecret = "ACCESS_KEY_SECRET";

    //private final static String oss_Bucket = PropertiesHelper.getValue("BUCKET");
    private final static String suff = "SUFF";

    private final static String roleArn = "ROLE_ARN";
    private final static String durationSeconds = "DURATION_SECONDS";
    private final static String policyFile = "POLICY_FILE";
    public final static String roleSessionName = "ROLE_SESSION_NAME";

    public static final String REGION = "REGION";
    
    public static final String STS_API_VERSION = "2015-04-01";

    private final static Logger log = LoggerFactory.getLogger(OssHelper.class);

    
    //private static OSSClient ossClient;

    static {
        // 如果缓存剩余时间小于10分钟则刷新ossClient
        Long pttl = CacheHelper.getCacher().pttl(PropertiesHelper.getValue(roleSessionName));
        if (pttl < 1000 * 60 * 10) {
        	try {
        		refreshClient();
        	}catch (Exception e) {
				log.error("初始化ossHelper发生错误", e);
			}
        }
    }

    /**
     * 简单文件上传
     *
     * @param is         文件流
     * @param extension  文件类型
     * @param accessEnum 访问权限
     * @return
     */
    public static String simpleUpload(InputStream is, String extension, FileAccessEnum accessEnum) {
        return simpleUpload(is, extension, accessEnum, null);
    }

    /**
     * 简单文件上传（带文件前缀）
     *
     * @param is         文件流
     * @param extension  文件类型
     * @param accessEnum 访问权限
     * @param filePerfix 文件前缀
     * @return 相对路径
     */
    public static String simpleUpload(InputStream is, String extension, FileAccessEnum accessEnum, String filePerfix) {
        OssBucketEnum bucketEnum = OssBucketEnum.findByFileAccessEnum(accessEnum);
        String keySuffixWithSlash = null;
        if (extension.indexOf(".") > -1) {
            extension = extension.substring(extension.indexOf(".") + 1);
        }
        keySuffixWithSlash = FileTypeDirEnum.findOssDirBySuffix(extension) + randomFileName(filePerfix) + "." + extension;
		/*Long pttl = CacheHelper.getCacher().pttl(roleSessionName);
		if (pttl < 0 || ossClient == null) {
			refreshClient();
		}*/
        //Map<String, String> respMap = new HashMap<String, String>();
        OSSClient ossClient = null;
        try {
            //respMap = assumeRole();
            //ClientConfiguration configuration = new ClientConfiguration();
            //configuration.setProtocol(Protocol.HTTPS);
            //ossClient = new OSSClient(PropertiesHelper.getValue(endpoint), respMap.get("AccessKeyId"), respMap.get("AccessKeySecret"), respMap.get("SecurityToken"), configuration);
            ossClient = new OSSClient(PropertiesHelper.getValue(endpoint), PropertiesHelper.getValue(accessKeyId), PropertiesHelper.getValue(accessKeySecret));
            ossClient.putObject(bucketEnum.getBucketName(), keySuffixWithSlash, is);
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            throw new BusinessException("文件上传异常");
        } finally {
            IOUtils.safeClose(is);
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return keySuffixWithSlash;
    }

    /**
     * 删除文件
     *
     * @param objectName
     */
    public static void delete(String objectName) {
        if (!StringUtils.hasText(objectName)) {
            throw new BusinessException("objectName不能为空");
        }
        OssBucketEnum bucketEnum = OssBucketEnum.findByObjectName(objectName);
		/*Long pttl = CacheHelper.getCacher().pttl(roleSessionName);
		if (pttl < 0 || ossClient == null) {
			refreshClient();
		}*/
        Map<String, String> respMap = new HashMap<String, String>();
        OSSClient ossClient = null;
        try {
            respMap = assumeRole();
            ClientConfiguration configuration = new ClientConfiguration();
            configuration.setProtocol(Protocol.HTTPS);
            ossClient = new OSSClient(PropertiesHelper.getValue(endpoint), respMap.get("AccessKeyId"), respMap.get("AccessKeySecret"), respMap.get("SecurityToken"), configuration);
            ossClient.deleteObject(bucketEnum.getBucketName(), objectName);
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            throw new BusinessException("文件删除异常");
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

    /**
     * 下载文件
     *
     * @param objectName
     * @return
     */
    public static InputStream download(String objectName) {
        if (!StringUtils.hasText(objectName)) {
            throw new BusinessException("objectName不能为空");
        }
        OssBucketEnum bucketEnum = OssBucketEnum.findByObjectName(objectName);
		/*Long pttl = CacheHelper.getCacher().pttl(roleSessionName);
		if (pttl < 0 || ossClient == null) {
			refreshClient();
		}*/
        Map<String, String> respMap = new HashMap<String, String>();
        OSSClient ossClient = null;
        try {
            respMap = assumeRole();
            ClientConfiguration configuration = new ClientConfiguration();
            configuration.setProtocol(Protocol.HTTPS);
            ossClient = new OSSClient(PropertiesHelper.getValue(endpoint), respMap.get("AccessKeyId"), respMap.get("AccessKeySecret"), respMap.get("SecurityToken"), configuration);
            OSSObject ossObject = ossClient.getObject(bucketEnum.getBucketName(), objectName);
            return ossObject.getObjectContent();
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            throw new BusinessException("文件下载异常");
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

    /**
     * STS分配临时角色
     *
     * @return
     * @throws ClientException
     */
    public static Map<String, String> assumeRole() throws ClientException {
        // 判断缓存中没有临时用户
    	String roleSession = PropertiesHelper.getValue(roleSessionName);
    	
        Map<String, String> respMap = (Map<String, String>) CacheHelper.getCacher().getObject(roleSession);
        Long pttl = CacheHelper.getCacher().pttl(roleSession);
        // 双重检测同步锁
        if (respMap == null || (pttl < 0)) {
            synchronized (OssHelper.class) {
                respMap = (Map<String, String>) CacheHelper.getCacher().getObject(roleSession);
                pttl = CacheHelper.getCacher().pttl(roleSession);
                if (respMap == null || (pttl < 0)) {
                    refreshClient();
                    respMap = (Map<String, String>) CacheHelper.getCacher().getObject(roleSession);
                }
            }
        }
        return respMap;

    }

    /**
     * 获取Object的临时访问路径
     * 生成以GET方法访问的签名URL，原图
     *
     * @param objectName
     * @return
     */
    public static String getUrl(String objectName) {
        return getUrl(objectName, null);
    }

    /**
     * 获取Object的临时访问路径
     * 生成以GET方法访问的签名URL，带样式，访客可以直接通过浏览器访问相关内容。
     *
     * @param objectName
     * @param style      oss样式字符串
     * @return
     */
    public static String getUrl(String objectName, String style) {
        if (!StringUtils.hasText(objectName)) {
            throw new BusinessException("objectName不能为空");
        }
        OssBucketEnum bucketEnum = OssBucketEnum.findByObjectName(objectName);
        String urlStr = "";
        if (bucketEnum.getAccess().equals(FileAccessEnum.私有)) {
			/*Long pttl = CacheHelper.getCacher().pttl(roleSessionName);
			if (pttl < 0 || ossClient == null) {	
		    	refreshClient();
	    	}*/
            // 设置URL过期时间。
        	
        	long durationSecond = Long.parseLong(PropertiesHelper.getValue(durationSeconds));
            Date expiration = new Date(System.currentTimeMillis() + durationSecond * 1000);
            GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(bucketEnum.getBucketName(), objectName, HttpMethod.GET);
            req.setExpiration(expiration);
            if (StringUtils.hasText(style)) {
                req.setProcess(style);
            }
            Map<String, String> respMap = new HashMap<String, String>();
            OSSClient ossClient = null;
            URL url = null;
            try {
                respMap = assumeRole();
                ClientConfiguration configuration = new ClientConfiguration();
                configuration.setProtocol(Protocol.HTTPS);
                ossClient = new OSSClient(PropertiesHelper.getValue(endpoint), respMap.get("AccessKeyId"), respMap.get("AccessKeySecret"), respMap.get("SecurityToken"), configuration);
                url = ossClient.generatePresignedUrl(req);
            } catch (Exception e) {
                log.warn(e.getMessage(), e);
                throw new BusinessException("文件上传异常");
            } finally {
                if (ossClient != null) {
                    ossClient.shutdown();
                }
            }
            urlStr = url.toString();
        } else {
            urlStr = bucketEnum.getBucketDomain() + "/" + objectName;
            if (StringUtils.hasText(style)) {
                urlStr = urlStr + "?x-oss-process=" + style;
            }
        }
        return urlStr;
    }

    /**
     * 刷新客户端，重新缓存
     */
    public static void refreshClient() {
        CacheHelper.getCacher().evict(PropertiesHelper.getValue(roleSessionName));

        
        //SpringContextHelper.springContext.getResource("classpath:config.properties")
        InputStream policyInputStream = null;
		try {
			policyInputStream = SpringContextHelper.springContext.getResource("classpath:"+ PropertiesHelper.getValue(policyFile)).getInputStream();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        //InputStream policyInputStream = OssHelper.class.getClassLoader().getResourceAsStream("/" + PropertiesHelper.getValue(policyFile));
        String policy = ReadJson(policyInputStream);
        IClientProfile profile = DefaultProfile.getProfile(PropertiesHelper.getValue(REGION), PropertiesHelper.getValue(accessKeyId), PropertiesHelper.getValue(accessKeySecret));
        DefaultAcsClient client = new DefaultAcsClient(profile);
        ProtocolType protocolType = ProtocolType.HTTPS;
        // 创建一个 AssumeRoleRequest 并设置请求参数
        final AssumeRoleRequest request = new AssumeRoleRequest();
        request.setVersion(STS_API_VERSION);
        request.setMethod(MethodType.POST);
        request.setProtocol(protocolType);
        request.setRoleArn(PropertiesHelper.getValue(roleArn));
        request.setRoleSessionName(PropertiesHelper.getValue(roleSessionName));
        request.setPolicy(policy);
        
        
        long durationSecond = Long.parseLong(PropertiesHelper.getValue(durationSeconds));
        request.setDurationSeconds(durationSecond);

        // 发起请求，并得到response
        Map<String, String> respMap = new LinkedHashMap<String, String>();
        AssumeRoleResponse stsResponse;
        try {
            stsResponse = client.getAcsResponse(request);
            respMap.put("AccessKeyId", stsResponse.getCredentials().getAccessKeyId());
            respMap.put("AccessKeySecret", stsResponse.getCredentials().getAccessKeySecret());
            respMap.put("SecurityToken", stsResponse.getCredentials().getSecurityToken());
            // 放入缓存，并重新创建ossClient
            CacheHelper.getCacher().put(PropertiesHelper.getValue(roleSessionName), respMap, durationSecond - (60 * 2));
        } catch (Exception e) {
            log.warn("ossClient连接失败");
            log.warn(e.getMessage(), e);
            CacheHelper.getCacher().evict(PropertiesHelper.getValue(roleSessionName));
        }
    }

    //读取策略文件的Json字符串
    private static String ReadJson(InputStream is) {
        BufferedReader reader = null;
        // 返回值,使用StringBuffer
        StringBuffer data = new StringBuffer();
        //
        try {
            reader = new BufferedReader(new InputStreamReader(is));
            // 每次读取文件的缓存
            String temp = null;
            while ((temp = reader.readLine()) != null) {
                data.append(temp);
            }
        } catch (IOException e) {
            log.warn(e.getMessage(), e);
        } finally {
            // 关闭文件流
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    log.warn(e.getMessage(), e);
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    log.warn(e.getMessage(), e);
                }
            }
        }
        return data.toString();
    }
	
	
	/*private static String ReadJson(String path) {
	    // 从给定位置获取文件
	    File file = new File(path);
	    BufferedReader reader = null;
	    // 返回值,使用StringBuffer
	    StringBuffer data = new StringBuffer();
	    //
	    try {
	        reader = new BufferedReader(new FileReader(file));
	        // 每次读取文件的缓存
	        String temp = null;
	        while ((temp = reader.readLine()) != null) {
	            data.append(temp);
	        }
	    } catch (FileNotFoundException e) {
            log.warn(e.getMessage(), e);
	    } catch (IOException e) {
            log.warn(e.getMessage(), e);
	    } finally {
	        // 关闭文件流
	        if (reader != null) {
	            try {
	                reader.close();
	            } catch (IOException e) {
                    log.warn(e.getMessage(), e);
	            }
	        }
	    }
	    return data.toString();
	}*/

    /**
     * attachment/yyyy/mm/dd/xxxx.xx
     *
     * @return
     */
    private static String randomFileName(String filePerfix) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String perfix = PropertiesHelper.getValue(suff);
        if (filePerfix != null) {
            perfix = perfix + filePerfix + "/";
        }
        return perfix + sdf.format(new Date()) + "/" + uuidFileName();
    }

    /**
     * 把文件名转uuid名称,后缀名保持不变
     *
     * @param srcFileName
     * @return
     */
    public static String randomFileName(String fileSuff, String srcFileName) {

        int suffIndex = srcFileName.lastIndexOf(".");
        if (-1 != suffIndex) {
            String suffix = srcFileName.substring(suffIndex).trim();//后缀
            if (suffix.length() <= 1) {
                return randomFileName(fileSuff);
            }
            return randomFileName(fileSuff) + suffix;
        } else {
            //没有后缀名
            return randomFileName(fileSuff);
        }

    }

    public static String uuidFileName() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String randomPath() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        return PropertiesHelper.getValue(suff) + sdf.format(new Date()) + "/";
    }

    @SuppressWarnings("unused")
    private static String random_string(int len) {
        String chars = "ABCDe3fERSTZabWYcdhi67jXkmFGHJKMNPQnprsxyz24tw58";
        int maxPos = chars.length();
        String pwd = "";
        for (int i = 0; i < len; i++) {
            int index = (int) Math.floor(Math.random() * maxPos);
            pwd += chars.charAt(index);
        }
        return pwd;
    }

    @SuppressWarnings("unused")
    private static String get_suffix(String filename) {
        int pos = filename.lastIndexOf(".");
        String suffix = "";
        if (pos != -1) {
            suffix = filename.substring(pos);
        }
        return suffix;
    }


    /**
     * 获取文件后缀
     *
     * @param srcFileName
     * @return null 系统不支持此后缀
     */
    public static FileTypeEnum suffixFileName(String srcFileName) {
        try {
            int suffIndex = srcFileName.lastIndexOf(".");
            if (-1 != suffIndex) {
                String suffix = srcFileName.substring(suffIndex + 1).trim();//后缀
                if (suffix.length() <= 1) return null;
                return FileTypeEnum.valueOf(suffix.toUpperCase());
            } else {
                return null;
            }
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println(randomFileName("我是测试的"));
    }

}
