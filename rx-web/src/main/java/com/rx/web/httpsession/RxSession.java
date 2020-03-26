package com.rx.web.httpsession;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

import com.rx.base.cache.CacheHelper;
import com.rx.web.utils.HttpServletHelper;

@SuppressWarnings("deprecation")

public abstract class RxSession implements HttpSession,Serializable{
	
	private static final long serialVersionUID = 7733215043083700732L;
	
	
	public static HttpSession getSession(RxSessionConfig sessionConfig,boolean autoCreate){
		
		Class<? extends HttpSession> sessionClass = sessionConfig.getSessionClass();
		if(sessionClass == HttpSession.class) {
			return HttpServletHelper.getRequest().getSession(autoCreate);
		}
		
		if(sessionConfig.isUseThreadCache()) {
			//session = RxSessionConfig.sessionThreadLocal.get();
			Object se = HttpServletHelper.getRequest().getAttribute("rx-session-request-cache");
			if(se != null && se instanceof RxSession) {
				if(sessionConfig.equals(((RxSession)se).getSessionCfg())){
					return (RxSession)se;
				}
			}
		}
		String code = sessionConfig.getRequestSessionCode(autoCreate);
		if(code == null) {
			return null; 
		}
		String sessionKey = sessionConfig.getCatalog() + code;
		Object obj = CacheHelper.getCacher().getObject(sessionKey);
		if(obj != null && obj instanceof HttpSession){
			if(sessionConfig.isUseThreadCache()) {
				//RxSessionConfig.sessionThreadLocal.set((HttpSession) obj);
				HttpServletHelper.getRequest().setAttribute("rx-session-request-cache", obj);
			}
			return (HttpSession) obj;
		}else {
			HttpSession session = null;
			try {
				Constructor<? extends HttpSession> cont = sessionClass.getDeclaredConstructor(String.class,RxSessionConfig.class);
				session = (RxSession)cont.newInstance(code,sessionConfig);
			}catch (Exception e) {
				e.printStackTrace();
			}
			CacheHelper.getCacher().put(sessionKey,session,sessionConfig.getTokenTimeout());//一次登录最长登录时间
			if(sessionConfig.isUseThreadCache()) {
				HttpServletHelper.getRequest().setAttribute("rx-session-request-cache", session);
				//RxSessionConfig.sessionThreadLocal.set(session);
			}
			return session;
		}
	}
	
	
	public static HttpSession getSession(String sessionCode,RxSessionConfig sessionConfig){
		
		Class<? extends HttpSession> sessionClass = sessionConfig.getSessionClass();
		if(sessionClass == HttpSession.class) {
			return null;
		}
		String sessionKey = sessionConfig.getCatalog() + sessionCode;
		Object obj = CacheHelper.getCacher().getObject(sessionKey);
		if(obj != null) {
			return (HttpSession)obj;
		}
		return null;
	}
	
	
	protected RxSession(String id,RxSessionConfig cfg) {
		this.id = id;
		this.sessionCfg = cfg;
	}
	
	private String id;
	
	private RxSessionConfig sessionCfg;
	
	
	public RxSessionConfig getSessionCfg() {
		return sessionCfg;
	}

	protected void freshCache() {
		CacheHelper.getCacher().put(sessionCfg.getCatalog() + id,this,sessionCfg.getTokenTimeout());
	}
	
	
	@Override
	public String getId() {
		return id;
	}
	
	@Override
	public long getCreationTime() {
		return 0;
	}


	@Override
	public long getLastAccessedTime() {
		return 0;
	}


	@Override
	public ServletContext getServletContext() {
		return null;
	}


	@Override
	public void setMaxInactiveInterval(int interval) {
	}


	@Override
	public int getMaxInactiveInterval() {
		return 0;
	}


	@Override
	public HttpSessionContext getSessionContext() {
		return null;
	}


	@Override
	public Enumeration<String> getAttributeNames() {
		return null;
	}


	@Override
	public String[] getValueNames() {
		return (String[])attributes.keySet().toArray();
	}


	private Map<String,Object> attributes = new HashMap<String,Object>();
	
	@Override
	public void setAttribute(String name, Object value) {
		attributes.put(name, value);
		this.freshCache();
	}
	
	
	@Override
	public Object getAttribute(String name) {
		return attributes.get(name);
	}

	@Override
	public void putValue(String name, Object value) {
		attributes.put(name, value);
		this.freshCache();
	}

	@Override
	public Object getValue(String name) {
		return attributes.get(name);
	}
	
	@Override
	public void removeAttribute(String name) {
		attributes.remove(name);
		this.freshCache();
	}


	@Override
	public void removeValue(String name){
		attributes.remove(name);
		this.freshCache();
	}

	@Override
	public void invalidate() {
		CacheHelper.getCacher().evict(sessionCfg.getCatalog() + this.getId());
	}
	
	@Override
	public boolean isNew() {
		return false;
	}
}
