package com.rx.web.user;

import com.alibaba.fastjson.annotation.JSONField;
import com.rx.web.httpsession.RxSession;
import com.rx.web.utils.HttpServletHelper;
import com.rx.base.user.RxPermissionable;
import com.rx.base.user.RxUserable;
import com.rx.base.cache.CacheHelper;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;
import java.util.List;

public interface RxUser<T extends HttpSession> extends RxUserable {
    
	public static Log logger = LogFactory.getLog(RxUser.class);
	
	public static String RX_USER_CLASS = "rxuserclass";
	
	
    public static HttpSession getSession(Class<? extends RxUser> userClass,boolean autoCreate)  {
    	RxUserConfig cfg = RxUserConfig.getConfig(userClass);
    	
    	return RxSession.getSession(cfg.getSessionConfig(), autoCreate);
    }
    
    public static String getUserId(Class<? extends RxUser> userClass) {
    	RxUser<?> user = getUser(userClass);
        return user == null ? null : user.getId();
    }
    
    
    public static RxUser<?> getUser(){
    	
    	String userClass = HttpServletHelper.getRequest().getHeader(RX_USER_CLASS);
    	
    	if(StringUtils.hasText(userClass)) {
    		Class<? extends RxUser> rxUserCls = null;
    		try {
    			rxUserCls = (Class<? extends RxUser>)Class.forName(userClass);
			} catch (ClassNotFoundException e) {
				logger.error("客户端传递了错误的Rx-User-Class类型:", e);
			}
    		if(rxUserCls == null) {
    			logger.error("客户端传递了的Rx-User-Class类型未找到对应的类:" + userClass);
    		}else {
    			RxUser<?> user2 = RxUser.getUser(rxUserCls);
    			
    			if(user2 == null) {
    				//logger.error("客户端传递了的Rx-User-Class类型:" + userClass + ";但未找到对应的用户;");
    			}else {
    				return user2;
    			}
    		}
    	}
    	for(Class<? extends RxUser> userCls : RxUserConfig.registerUserClasses) {
			RxUser<?> user2 = RxUser.getUser(userCls);
			if(user2 != null) {
				return user2;
			}
		}
    	return null;
    }
    
    public static<E extends RxUser> E getUser(Class<E> userClass) {
    	
    	String userCacheKey = "rx-user-request-cache-"+userClass.getSimpleName();
    	Object old = HttpServletHelper.getRequest().getAttribute(userCacheKey);
		if(old != null && old instanceof RxUser) {
			return (E)old;
		}
    	
    	RxUserConfig cfg = RxUserConfig.getConfig(userClass);
    	if(cfg == null) {
    		return null;
    	}
    	HttpSession session = RxSession.getSession(cfg.getSessionConfig(), false);
    	if(session == null) {
    		return null;
    	}
    	Object obj = session.getAttribute(cfg.getUserSessionAttribute());
    	if(obj == null) {
    		return null;
    	}
    	if(obj instanceof RxUser) {
    		RxUser<?> user = (RxUser<?>)obj;
        	if(cfg.isSSO()) {
        		String sessionId = CacheHelper.getCacher().getString(user.getSsoKey());
        		if(!session.getId().equals(sessionId)) {
        			//账号在其他地方登录
        			return null; 
        		}
        	}
        	HttpServletHelper.getRequest().setAttribute(userCacheKey,user);
    		return (E) user;
    	}else {
    		return null;
    	}
    }

    
    public boolean isAdmin();
    
    /**
     * 获取分组标识
     * @return
     */
    public String getGroupId();
    /**
     * 获得用户是否是测试用户
    * @return
    */
    public boolean isTest();
    
    /**
     * 获得用户的单点登录的key
     * @return
     */
    public String getSsoKey();
    
    /**
     * 设定用户为登录状态
     * @return 返回会话id
     */
    public String login();
    
    /**
     * 设定用户为登出状态
     * @return 
     */
    public void logout();
    
    @JSONField(serialize=false)
    public T getMySession(boolean autoCreate);

    
    public List<? extends RxPermissionable> getMyPermissions();

}
