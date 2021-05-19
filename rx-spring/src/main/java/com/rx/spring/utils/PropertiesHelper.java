package com.rx.spring.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.env.Environment;

import com.rx.base.bean.RxBeanHelper;

/**
 * 读取配置文件
 */
public class PropertiesHelper {

    private static transient final Log log = LogFactory.getLog(PropertiesHelper.class);
    
    private static Environment getEnv() {
    	return ((Environment)RxBeanHelper.getFactoryBean(Environment.class));
    }
    
    public static String getValue(String key) {
        

        return getEnv().getProperty(key);

    }

    public static String getValue(String key, String defaultValue) {
        String v = getValue(key);
        if (null == v) return defaultValue;
        return v;
    }
    
    /*
    private static Properties pp = null;

    public static String getValue(String key) {
        if (!StringUtils.hasText(key)) {
            log.debug("找不到key为 "+key+" 对应的值");
            return null;
        }

        if (pp == null) {
            pp = getProperties();
        }

        return pp.getProperty(key);

    }

    public static String getValue(String key, String defaultValue) {
        String v = getValue(key);
        if (null == v) return defaultValue;
        return v;
    }

    private static Properties getProperties() {
        Properties prop = new Properties();
        InputStream in = null;
        try {
            Resource resource = SpringContextHelper.springContext.getResource("classpath:config.properties");
            in = resource.getInputStream();
            //in = (ByteArrayInputStream)Thread.currentThread().getContextClassLoader().getResourceAsStream("/config.properties");
            prop.load(in);
        } catch (Exception e) {
            log.error("加载config.properties文件失败...");
            e.printStackTrace();
        } finally {
            if (in != null)
                try {
                    in.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
        }
        return prop;
    }
    
    */
}
