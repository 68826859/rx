package com.rx.web.httpsession;

import javax.servlet.http.Cookie;

import com.rx.web.utils.CookieUtil;

@SuppressWarnings("deprecation")

@RxAnSession(catalog="rx:session:cookie-sessions:")
public class RxCookieSession extends RxSession{
	private static final long serialVersionUID = -9083487544487755650L;
	
	
	protected RxCookieSession(String id,RxSessionConfig cfg) {
		super(id,cfg);
	}
	
	/*
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
	public Object getValue(String name) {
		return null;
	}


	@Override
	public Enumeration<String> getAttributeNames() {
		return null;
	}


	@Override
	public String[] getValueNames() {
		return null;
	}


	@Override
	public void setAttribute(String name, Object value) {
		
	}
	
	
	@Override
	public Object getAttribute(String name) {
		return null;
	}

	@Override
	public void putValue(String name, Object value) {
		
	}

	@Override
	public void removeAttribute(String name) {
		
	}


	@Override
	public void removeValue(String name){
		
	}
	
	@Override
	public boolean isNew() {
		return false;
	}
	
	*/
	@Override
	public void invalidate() {
		super.invalidate();
		Cookie cookie = CookieUtil.getCookie(getSessionCfg().getCookieKey());
		if(cookie != null) {
			CookieUtil.removeCookie(getSessionCfg().getCookieKey());
		}
	}


}
