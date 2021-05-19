package com.rx.web.user;


import java.util.HashSet;
import java.util.List;

import javax.persistence.Transient;
import javax.servlet.http.HttpSession;
import org.springframework.util.DigestUtils;
import com.rx.base.result.type.BusinessException;
import com.rx.base.user.RxPermissionProvider;
import com.rx.base.user.RxPermissionable;
import com.rx.spring.utils.SpringContextHelper;
import com.rx.web.httpsession.RxSession;
import com.rx.web.httpsession.RxSessionConfig;
import com.rx.web.utils.HttpServletHelper;
import com.alibaba.fastjson.annotation.JSONField;
import com.rx.base.bean.RxBeanHelper;
import com.rx.base.cache.CacheHelper;


public abstract class RxUserAbstract<T extends HttpSession> implements RxUser<T> {
	
	private static final long serialVersionUID = -3467355766974793635L;

	
	public final static String SSO_SESSION_CATALOG = "rx:user:sso:";
	
	@Transient
	private String ssoKey = null;
	
	@Transient
	@JSONField(serialize = false)
	//@JsonIgnore
	private List<? extends RxPermissionable> myPermissions = null;
	
	
    public String getSsoKey() {
    	if(ssoKey == null) {
    		ssoKey = SSO_SESSION_CATALOG + DigestUtils.md5DigestAsHex((this.getClass().getName() + this.getId()).getBytes());
    	}
    	return ssoKey;
    }
	
	/**
	 * 刷新用户的会话id
	 * @return
	 */
	public String freshSessionCode(){
		HttpSession session = this.getMySession(false);
		if(session != null) {
			session.invalidate();
		}
		return this.login();
	}
	
	/**
	 * 	使用户对象处于登录状态
	 * @return 返回会话code
	 */
	public String login() {
		if(RxUserConfig.hasRegisterUserClass(this.getClass())) {
			RxUserConfig cfg = RxUserConfig.getConfig(this.getClass());
		   	RxSessionConfig scfg = cfg.getSessionConfig();
		   	HttpSession session = RxSession.getSession(scfg,true);
		   	session.setAttribute(cfg.getUserSessionAttribute(), this);
		   	String code = session.getId();
		   	if(cfg.isSSO()) {
			    CacheHelper.getCacher().put(getSsoKey(), code);
		   	}else {
		   		Object obj = CacheHelper.getCacher().getObject(getSsoKey());
		   		HashSet<String> set = null;
		   		if(obj != null && obj instanceof HashSet) {
		   			set = (HashSet)obj;
		   		}else {
		   			set = new HashSet<String>();
		   		}
		   		set.add(session.getId());
		   		CacheHelper.getCacher().put(getSsoKey(),set,scfg.getTokenTimeout());
		   	}
		   	return code;
		}else {
			throw new BusinessException("用户类型没有注册，不能登录");
		}
	}
	
	
	/**
	 * 登出用户
	 */
	public void logout() {
		if(RxUserConfig.hasRegisterUserClass(this.getClass())) {
			RxUserConfig cfg = RxUserConfig.getConfig(this.getClass());
			HttpSession session = this.getMySession(false);
			if(session != null) {
				session.removeAttribute(cfg.getUserSessionAttribute());
				if(cfg.isSSO()) {
			   		String ssokey = getSsoKey();
			   		String ssoId = CacheHelper.getCacher().getString(ssokey);
			   		if(org.springframework.util.StringUtils.hasText(ssoId) && ssoId.equals(session.getId())) {
		    			CacheHelper.getCacher().evict(ssokey);
			   		}
			   	}else {
			   		Object obj = CacheHelper.getCacher().getObject(getSsoKey());
			   		if(obj != null && obj instanceof HashSet) {
			   			HashSet<String> set = (HashSet)obj;
			   			try {
			   				set.remove(session.getId());
			   				CacheHelper.getCacher().put(getSsoKey(),set,cfg.getSessionConfig().getTokenTimeout());
			   			}catch(Exception ex) {
			   				ex.printStackTrace();
			   			}
			   		}
			   	}
				return;
			}
		}
		RxUserConfig cfg = RxUserConfig.getConfig(this.getClass());
		String ssokey = getSsoKey();
		if(cfg.isSSO()) {
			String ssoId = CacheHelper.getCacher().getString(ssokey);
	   		if(org.springframework.util.StringUtils.hasText(ssoId)) {
    			CacheHelper.getCacher().evict(ssokey);
	   		}
		}else {
			Object obj = CacheHelper.getCacher().getObject(ssokey);
	   		if(obj != null && obj instanceof HashSet) {
	   			HashSet<String> set = (HashSet)obj;
	   			HttpSession se = null;
	   			for(String sc : set) {
	   				se = RxSession.getSession(sc, cfg.getSessionConfig());
	   				if(se != null) {
	   					se.removeAttribute(cfg.getUserSessionAttribute());
	   				}
	   			}
	   			//CacheHelper.getCacher().del(ssokey);
	   		}
		}
	}
	
	/**
	 * 获取会话
	 * @param autoCreate
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T getMySession(boolean autoCreate) {
		//HttpSession session = RxSession.getSession(RxUserConfig.getConfig((Class<? extends RxUser.User<? extends HttpSession>>)this.getClass()).getSessionConfig(), autoCreate);
		HttpSession session = RxUser.getSession(this.getClass(), autoCreate);
		if(session != null) {
			RxUserConfig cfg = RxUserConfig.getConfig(this.getClass());
			Object user = session.getAttribute(cfg.getUserSessionAttribute());
			if(user != null && user instanceof RxUser) {
				if(((RxUser)user).getId().equals(this.getId())) {
					return (T)session;
				}
			}
		}
		return null;
	}
    
	public void cleanMyPermissionsCache(){
		this.myPermissions = null;
		RxUserConfig cfg = RxUserConfig.getConfig(this.getClass());
		this.getMySession(true).setAttribute(cfg.getUserSessionAttribute(), this);
	}
	
	
	public List<? extends RxPermissionable> getMyPermissions(){
		if(this.myPermissions == null) {
			this.myPermissions = RxBeanHelper.getFactoryBean(RxPermissionProvider.class).getUserPermissions(this);
		}
		RxUserConfig cfg = RxUserConfig.getConfig(this.getClass());
		this.getMySession(true).setAttribute(cfg.getUserSessionAttribute(), this);
		return this.myPermissions;
	}

	@Override
	public String getGroupId() {
		return this.getClass().getName();
	}
	
}
