package com.rx.web.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rx.web.utils.HttpServletHelper;

/**
 * 
 * Cookie 工具类
 *
 */
public final class CookieUtil {
	protected static final Log logger = LogFactory.getLog(CookieUtil.class);

	
	
	
	/**
	 * 添加Cookie
	 * 
	 * @param cookieName  名称
	 * @param cookieValue 值
	 * @param maxAge      存活时间(按秒算)
	 */
	public static void addCookie(String cookieName, String cookieValue, int maxAge) {
		/** 获取Cookie */
		Cookie cookie = getCookie(cookieName);
		if (cookie == null) {
			cookie = new Cookie(cookieName, cookieValue);
		} else {
			cookie.setValue(cookieValue);
		}
		/** 设置有效时间 */
		cookie.setMaxAge(maxAge);
		/** http://localhost:8080/ 设置访问路径 */
		cookie.setPath("/");
		/** 添加cookie到用户浏览器 */
		HttpServletHelper.getResponse().addCookie(cookie);
	}

	/**
	 * 重新设置cookie的生命周期
	 * 
	 * @param cookieName
	 * @param maxAge
	 */
	public static void expire(String cookieName, int maxAge) {
		/** 获取Cookie */
		Cookie cookie = getCookie(cookieName);
		if (null != cookie) {
			cookie.setPath("/");
			/** 设置有效时间 */
			cookie.setMaxAge(maxAge);
			/** 添加cookie到用户浏览器 */
			HttpServletHelper.getResponse().addCookie(cookie);
		}
	}

	/**
	 * 获取Cookie
	 * 
	 * @param cookieName 名称
	 * @return Cookie
	 */
	public static Cookie getCookie(String cookieName) {
		/* 获取当前浏览器中所有的Cookie */
		HttpServletRequest httpServletRequest = HttpServletHelper.getRequest();
		if (httpServletRequest != null) {
			Cookie[] cookies = httpServletRequest.getCookies();
			if (cookies != null && cookies.length > 0) {
				for (Cookie cookie : cookies) {
					if (cookie.getName().equals(cookieName)) {
						return cookie;
					}
				}
			}
		}
		return null;
	}

	/**
	 * 删除Cookie
	 * 
	 * @param cookieName 名称
	 */
	public static void removeCookie(String cookieName) {
		/** 获取Cookie */
		Cookie cookie = getCookie(cookieName);
		if (cookie != null) {
			cookie.setPath("/");
			cookie.setMaxAge(0); // 设置cookie立即失效
			HttpServletHelper.getResponse().addCookie(cookie);
		}
	}
	
	
	
	
	/**
	 * 得到Cookie的值, 不编码
	 *
	 * @param request
	 * @param cookieName
	 * @return
	 */
	public static String getCookieValue(HttpServletRequest request, String cookieName) {
		return getCookieValue(request, cookieName, false);
	}

	/**
	 * 得到Cookie的值,
	 *
	 * @param request
	 * @param cookieName
	 * @return
	 */
	public static String getCookieValue(HttpServletRequest request, String cookieName, boolean isDecoder) {
		Cookie[] cookieList = request.getCookies();
		if (cookieList == null || cookieName == null) {
			return null;
		}
		String retValue = null;
		try {
			for (int i = 0; i < cookieList.length; i++) {
				if (cookieList[i].getName().equals(cookieName)) {
					if (isDecoder) {
						retValue = URLDecoder.decode(cookieList[i].getValue(), "UTF-8");
					} else {
						retValue = cookieList[i].getValue();
					}
					break;
				}
			}
		} catch (UnsupportedEncodingException e) {
			logger.error("Cookie Decode Error.", e);
		}
		return retValue;
	}

	/**
	 * 得到Cookie的值,
	 *
	 * @param request
	 * @param cookieName
	 * @return
	 */
	public static String getCookieValue(HttpServletRequest request, String cookieName, String encodeString) {
		Cookie[] cookieList = request.getCookies();
		if (cookieList == null || cookieName == null) {
			return null;
		}
		String retValue = null;
		try {
			for (int i = 0; i < cookieList.length; i++) {
				if (cookieList[i].getName().equals(cookieName)) {
					retValue = URLDecoder.decode(cookieList[i].getValue(), encodeString);
					break;
				}
			}
		} catch (UnsupportedEncodingException e) {
			logger.error("Cookie Decode Error.", e);
		}
		return retValue;
	}

	/**
	 * 设置Cookie的值 不设置生效时间默认浏览器关闭即失效,也不编码
	 */
	public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName,
			String cookieValue) {
		setCookie(request, response, cookieName, cookieValue, -1);
	}

	/**
	 * 设置Cookie的值 在指定时间内生效,但不编码
	 */
	public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName,
			String cookieValue, int cookieMaxage) {
		setCookie(request, response, cookieName, cookieValue, cookieMaxage, false);
	}

	/**
	 * 设置Cookie的值 不设置生效时间,但编码
	 */
	public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName,
			String cookieValue, boolean isEncode) {
		setCookie(request, response, cookieName, cookieValue, -1, isEncode);
	}

	/**
	 * 设置Cookie的值 在指定时间内生效, 编码参数
	 */
	public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName,
			String cookieValue, int cookieMaxage, boolean isEncode) {
		doSetCookie(request, response, cookieName, cookieValue, cookieMaxage, isEncode);
	}

	/**
	 * 设置Cookie的值 在指定时间内生效, 编码参数(指定编码)
	 */
	public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName,
			String cookieValue, int cookieMaxage, String encodeString) {
		doSetCookie(request, response, cookieName, cookieValue, cookieMaxage, encodeString);
	}

	/**
	 * 删除Cookie带cookie域名
	 */
	public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String cookieName) {
		doSetCookie(request, response, cookieName, "", -1, false);
	}

	/**
	 * 设置Cookie的值，并使其在指定时间内生效
	 *
	 * @param cookieMaxage cookie生效的最大秒数
	 */
	private static final void doSetCookie(HttpServletRequest request, HttpServletResponse response, String cookieName,
			String cookieValue, int cookieMaxage, boolean isEncode) {
		try {
			if (cookieValue == null) {
				cookieValue = "";
			} else if (isEncode) {
				cookieValue = URLEncoder.encode(cookieValue, "utf-8");
			}
			Cookie cookie = new Cookie(cookieName, cookieValue);
			if (cookieMaxage > 0)
				cookie.setMaxAge(cookieMaxage);
			if (null != request)// 设置域名的cookie
				cookie.setDomain(getDomainName(request));
			cookie.setPath("/");
			response.addCookie(cookie);
		} catch (Exception e) {
			logger.error("Cookie Encode Error.", e);
		}
	}

	/**
	 * 设置Cookie的值，并使其在指定时间内生效
	 *
	 * @param cookieMaxage cookie生效的最大秒数
	 */
	private static final void doSetCookie(HttpServletRequest request, HttpServletResponse response, String cookieName,
			String cookieValue, int cookieMaxage, String encodeString) {
		try {
			if (cookieValue == null) {
				cookieValue = "";
			} else {
				cookieValue = URLEncoder.encode(cookieValue, encodeString);
			}
			Cookie cookie = new Cookie(cookieName, cookieValue);
			if (cookieMaxage > 0)
				cookie.setMaxAge(cookieMaxage);
			if (null != request)// 设置域名的cookie
				cookie.setDomain(getDomainName(request));
			cookie.setPath("/");
			response.addCookie(cookie);
		} catch (Exception e) {
			logger.error("Cookie Encode Error.", e);
		}
	}

	/**
	 * 得到cookie的域名
	 */
	private static final String getDomainName(HttpServletRequest request) {
		String domainName = null;

		String serverName = request.getRequestURL().toString();
		if (serverName == null || serverName.equals("")) {
			domainName = "";
		} else {
			serverName = serverName.toLowerCase();
			serverName = serverName.substring(7);
			final int end = serverName.indexOf("/");
			serverName = serverName.substring(0, end);
			final String[] domains = serverName.split("\\.");
			int len = domains.length;
			if (len > 3) {
				// www.xxx.com.cn
				domainName = "." + domains[len - 3] + "." + domains[len - 2] + "." + domains[len - 1];
			} else if (len <= 3 && len > 1) {
				// xxx.com or xxx.cn
				domainName = "." + domains[len - 2] + "." + domains[len - 1];
			} else {
				domainName = serverName;
			}
		}

		if (domainName != null && domainName.indexOf(":") > 0) {
			String[] ary = domainName.split("\\:");
			domainName = ary[0];
		}
		return domainName;
	}

}
