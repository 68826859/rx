package com.rx.web.utils;

import com.rx.base.page.Pager;
import com.rx.base.result.type.BusinessException;
import com.rx.base.result.type.ValidateException;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class HttpServletHelper {

    public static HttpSession getSession() {
        if (getRequest() != null) {
            return getRequest().getSession();
        }
        return null;
    }


    public static HttpServletResponse getResponse() {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        if (ra != null) {
            ServletRequestAttributes sra = (ServletRequestAttributes) ra;
            return sra.getResponse();
        } else {
            return null;
        }
    }

    public static HttpServletRequest getRequest() {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        if (ra != null) {
            ServletRequestAttributes sra = (ServletRequestAttributes) ra;
            return sra.getRequest();
        } else {
            return null;
        }
    }

    public static HttpServletRequest getRequestValid() {
        HttpServletRequest request = getRequest();
        if (request == null){
            throw new BusinessException("");
        }
        return request;
    }
    
    
    public static void main(String[] args) {
    	
    	Integer.valueOf("a").intValue();
    }
    
    
    
    private static Pattern intPattern = Pattern.compile("^[-\\+]?[\\d]*$");
    
    public static boolean isInteger(String str) {
        return intPattern.matcher(str).matches();  
    }
    
    public static <T> Pager<T> getPager(Class<T> clazz) {
        HttpServletRequest req = getRequest();
        Pager<T> pager = new Pager<T>();
        String start = req.getParameter("start");
        String page = req.getParameter("page");
        String limit = req.getParameter("limit");
        if(limit != null && isInteger(limit)) {
        	pager.setPageSize(Integer.valueOf(limit).intValue());
        }else {
        	pager.setPageSize(25);
        	//throw new ValidateException("分页参数每页条数是必须的");
        }
        if(page != null && isInteger(page)) {
        	int pageNum = Integer.valueOf(page).intValue();
        	pager.setPageNum(pageNum);
        	pager.setStartRow(pageNum * pager.getPageSize());
        }else if(start != null && isInteger(start)) {
        	int startRow = Integer.valueOf(start).intValue();
        	pager.setStartRow(startRow);
        	pager.setPageNum((startRow / pager.getPageSize())+1);
        }else {
        	pager.setStartRow(0);
        	pager.setPageNum(1);
        	//throw new ValidateException("分页参数起始页或者当前页参数必传其一");
        }
        return pager;
    }
    
    
    /**
     * 从Request对象中获得客户端IP，处理了HTTP代理服务器和Nginx的反向代理截取了ip
     */
    public static String getLocalIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip.contains(",")) {
            return ip.split(",")[0];
        } else {
            return ip;
        }
    }

}
