package com.rx.web.user;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;

import com.rx.web.httpsession.RxSessionConfig;

public class RxUserConfig {

	private static Map<Class<? extends RxUser>,RxUserConfig> configs = new HashMap<Class<? extends RxUser>,RxUserConfig>();
	
	protected static List<Class<? extends RxUser>> registerUserClasses = new ArrayList<Class<? extends RxUser>>();
	
	
	public static void registerUserClass(Class<? extends RxUser> userClass) {
		if(!registerUserClasses.contains(userClass)) {
			registerUserClasses.add(userClass);
		}
	}
	
	public static boolean hasRegisterUserClass(Class<? extends RxUser> userClass) {
		return registerUserClasses.contains(userClass);
	}
	
	/*
	public static Class<? extends RxUser> getLastUserClass(){
		if(registerUserClasses.isEmpty()) {
			return null;
		}else {
			return registerUserClasses.get(0);
		}
	}
	*/
	
	public static RxUserConfig getConfig(Class<? extends RxUser> userClass) {
		
		RxUserConfig cfg = configs.get(userClass);
		
		if(cfg == null) {
			
			RxAnUserSession an = userClass.getAnnotation(RxAnUserSession.class);
			
			cfg = new RxUserConfig();
			
			cfg.userSessionAttribute = "rxuser-login-" + userClass.getName();
			
			RxSessionConfig scfg = new RxSessionConfig((Class<? extends HttpSession>) ((ParameterizedType) userClass.getGenericSuperclass()).getActualTypeArguments()[0]);
			if(an != null) {
				cfg.sso = an.sso();
				
				if(!RxAnUserSession.NULL.equals(an.attribute())) {
					cfg.userSessionAttribute = an.attribute();
				}
				if(!RxAnUserSession.NULL.equals(an.cookieKey())){
					scfg.setCookieKey(an.cookieKey());
				}
				if(!RxAnUserSession.NULL.equals(an.tokenKey())){
					scfg.setTokenKey(an.tokenKey());
				}
				if(an.httpTimeout() != 0) {
					scfg.setHttpTimeout(an.httpTimeout());
				}
				if(an.cookieTimeout() != 0) {
					scfg.setCookieTimeout(an.cookieTimeout());
				}
				if(an.tokenTimeout() != 0) {
					scfg.setTokenTimeout(an.tokenTimeout());
				}
				scfg.setUseThreadCache(an.userThreadCache());
				scfg.setTokenInHeader(an.tokenInHeader());
			}
			cfg.sessionConfig = scfg;
			configs.put(userClass, cfg);
		}
		
		return cfg;
	}
	
	private RxSessionConfig sessionConfig;
	
	private String userSessionAttribute;

	private boolean sso = false;

	/*
	public boolean cleanSessionCode() {
 		if(sessionType == SessionTypeEnum.HttpSession) {
 			HttpSession hs = AppContextHelper.getRequest().getSession(false);
			if(hs != null) {
				hs.invalidate();
				return true;
			}
 		}else if(sessionType == SessionTypeEnum.Cookie) {
			Cookie cookie = CookieTools.getCookie(cookieKey);
 			if(cookie != null) {
 				CookieTools.removeCookie(cookieKey);
 				return true;
 			}
 		}else if(sessionType == SessionTypeEnum.Token) {
 			String code = AppContextHelper.getRequest().getParameter(tokenKey);
 			if(code != null) {
 				CacheHelper.getCacher().del(code);
 				return true;
 			}
 		}
 		return false;
     }
	*/
	
	
	/*
	public RxUserConfig setSessionType(SessionTypeEnum sessionType) {
		this.sessionType = sessionType;
		return this;
	}
	*/

	public boolean isSSO() {
		return sso;
	}

	public RxSessionConfig getSessionConfig() {
		return sessionConfig;
	}

	public String getUserSessionAttribute() {
		return userSessionAttribute;
	}

}
