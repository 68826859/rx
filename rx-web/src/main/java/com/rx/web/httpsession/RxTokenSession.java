package com.rx.web.httpsession;


@SuppressWarnings("deprecation")

@RxAnSession(catalog="rx:session:token-sessions:")
public class RxTokenSession extends RxSession{
	
	private static final long serialVersionUID = -7622639754610367089L;
	
	
	protected RxTokenSession(String id,RxSessionConfig cfg) {
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
	


}
