package com.rx.web.httpsession;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

import com.rx.web.utils.CookieUtil;
import com.rx.web.utils.HttpServletHelper;

public class RxSessionConfig implements Serializable{
	
	private static final long serialVersionUID = -6794764219858353804L;
	
	/*
	protected static ThreadLocal<HttpSession> sessionThreadLocal = new ThreadLocal<HttpSession>();
	
	public static void cleanSessionCache() {
		if(useThreadLocal) {
			sessionThreadLocal.remove();
		}
	}
	*/

	private Class<? extends HttpSession> sessionClass = HttpSession.class;
	
	private String cookieKey = "rx-user-session";
	
	private String tokenKey = "code";
	
	
	private boolean tokenInHeader = false;
	
	private long httpTimeout = 30 * 60;//http超时时间，秒
	
	private int cookieTimeout = 60 * 60 * 24;//单位秒
	
	private long tokenTimeout = 1000 * 60 * 60 * 24 * 7; // 单位毫秒

	private boolean useThreadCache = false;
	
	private String catalog = "rx:session:sessions:";
	
	public RxSessionConfig(Class<? extends HttpSession> sessionClass) {
		this.sessionClass = sessionClass;
		RxAnSession an = sessionClass.getAnnotation(RxAnSession.class);
		if(an != null && !RxAnSession.NULL.equals(an.catalog())) {
			catalog = an.catalog();
		}
		
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj != null && obj instanceof RxSessionConfig) {
			RxSessionConfig cfg = (RxSessionConfig)obj;
			if(Objects.equals(cfg.sessionClass,this.sessionClass) && Objects.equals(cfg.cookieKey,this.cookieKey) && Objects.equals(cfg.tokenKey,this.tokenKey)) {
				return true;
			}
		}
		return false;//super.equals(obj);
	}
	
	
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
	
	
	public String getRequestSessionCode(boolean autoCreate) {
		String code = null;
		if(sessionClass == RxSpringSession.class || sessionClass == HttpSession.class) {
			HttpSession hs = HttpServletHelper.getRequest().getSession(false);
			if(hs != null) {
				code = hs.getId();
			}else if(autoCreate) {
				code = HttpServletHelper.getSession().getId();
			}
		}else if(sessionClass == RxCookieSession.class) {
			Cookie cookie = CookieUtil.getCookie(cookieKey);
			if(cookie != null) {
				code = cookie.getValue();
			}else if(autoCreate){
				code = UUID.randomUUID().toString();
				CookieUtil.addCookie(cookieKey, code, cookieTimeout);
			}
		}else if(sessionClass == RxTokenSession.class) {
			if(!useThreadCache) {
				Object oldToken = HttpServletHelper.getRequest().getAttribute("rx-tokenKey-random");
				if(oldToken != null) {
					return (String)oldToken;
				}
			}
			String token = null;
			if(tokenInHeader) {
				token = HttpServletHelper.getRequest().getHeader(tokenKey);
			}else{
				token = HttpServletHelper.getRequest().getParameter(tokenKey);
			}
			if(org.springframework.util.StringUtils.hasText(token)) {
				code = token;
			}else if(autoCreate) {
				code = UUID.randomUUID().toString();
				if(!useThreadCache) {
					HttpServletHelper.getRequest().setAttribute("rx-tokenKey-random", code);
				}
			}
		}
		return code;
	}
	
	public String getCookieKey() {
		return cookieKey;
	}

	public RxSessionConfig setCookieKey(String cookieKey) {
		this.cookieKey = cookieKey;
		return this;
	}

	public String getTokenKey() {
		return tokenKey;
	}

	public RxSessionConfig setTokenKey(String tokenKey) {
		this.tokenKey = tokenKey;
		return this;
	}

	public long getHttpTimeout() {
		return httpTimeout;
	}

	public RxSessionConfig setHttpTimeout(long httpTimeout) {
		this.httpTimeout = httpTimeout;
		return this;
	}

	public int getCookieTimeout() {
		return cookieTimeout;
	}

	public RxSessionConfig setCookieTimeout(int cookieTimeout) {
		this.cookieTimeout = cookieTimeout;
		return this;
	}

	public long getTokenTimeout() {
		return tokenTimeout;
	}

	public RxSessionConfig setTokenTimeout(long tokenTimeout) {
		this.tokenTimeout = tokenTimeout;
		return this;
	}

	public Class<? extends HttpSession> getSessionClass() {
		return sessionClass;
	}

	public String getCatalog() {
		return catalog;
	}

	public boolean isUseThreadCache() {
		return useThreadCache;
	}

	public void setUseThreadCache(boolean useThreadCache) {
		this.useThreadCache = useThreadCache;
	}

	public boolean isTokenInHeader() {
		return tokenInHeader;
	}

	public RxSessionConfig setTokenInHeader(boolean tokenInHeader) {
		this.tokenInHeader = tokenInHeader;
		return this;
	}
}
