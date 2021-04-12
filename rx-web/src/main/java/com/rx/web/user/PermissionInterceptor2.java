package com.rx.web.user;

import com.rx.base.result.type.ForbiddenException;
import com.rx.base.result.type.LoginException;
import com.rx.base.user.RxPermissionable;
import com.rx.base.user.RxUserable;

import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PermissionInterceptor2{
	
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod method = (HandlerMethod) handler;
            Annotation[] as = method.getMethod().getAnnotations();
            Map<Class<? extends RxUserable>,StringBuffer> map = null;
            Class<? extends RxUserable> ucls = null;
            
            StringBuffer sbName = null;
            for (Annotation an : as) {
            	if(an.annotationType() == RxPermission.class) {
            		
            		sbName = new StringBuffer("登录权限");
            		if(map == null) {
            			map = new HashMap<>();
            		}
            		
            		ucls = ((RxPermission)an).type();
        			if( ucls == RxUser.class) {
        				for(Class<? extends RxUserable> userCls : RxUserConfig.registerUserClasses) {
                			addMapUserPermission(map,userCls,null);
                		 }
        			}else {
        				addMapUserPermission(map,ucls,null);
        			}
            	}else if (PermissionMgr.hasAnnotation(an)) {
            		
            		if(sbName == null) {
            			sbName = new StringBuffer();
            		}
            		sbName.append(PermissionMgr.getAnnotationPermissionNames(an));
            		
            		if(map == null) {
            			map = new HashMap<>();
            		}
            		
                	ucls = PermissionMgr.getAnnotationUserTypes(an);
                	String ps = PermissionMgr.getAnnotationPermissionStr(an);
                	if(ucls == null) {
                		for(Class<? extends RxUser> userCls : RxUserConfig.registerUserClasses) {
                			addMapUserPermission(map,userCls,ps);
                		 }
                	}else {
                		addMapUserPermission(map,ucls,ps);
                	}
                }else {
                    permissionAnHandler(an);
                }
            }
            if(map != null) {
            	boolean hasUser = false;
            	Exception ex = null;
            	for(Class<? extends RxUser> userCls : RxUserConfig.registerUserClasses) {
        			RxUser<?> user = RxUser.getUser(userCls);
        			if(user != null) {
        				hasUser = true;
        				if(!map.containsKey(user.getClass())) {
                        	ex = new LoginException("请先登录");
                        	continue;
                        }
                    	StringBuffer permissions = map.get(user.getClass());
                    	if(permissions != null && permissionHandler(user,permissions.toString())) {
                    		return true;
                    	}
                        ex = new ForbiddenException(String.format("您没有 %s 的权限",sbName == null?"":sbName.toString()));
        			}
        		}
            	if(!hasUser) {
            		throw new LoginException("请先登录");
            	}
            	if(ex != null) {
            		throw ex;
            	}
    		}
        }
        return true;
    }

    private void addMapUserPermission(Map<Class<? extends RxUserable>,StringBuffer> map, Class<? extends RxUserable> userType,String permissions) {
    	StringBuffer sb = null;
    	if(map.containsKey(userType)) {
    		sb = map.get(userType);
    	}else {
    		sb = new StringBuffer();
    		map.put(userType, sb);
    	}
    	if(permissions != null) {
    		sb.append(permissions);
    	}
    }
    
    

    
    private boolean checkUserType(RxUser<?> user,String permissions) {
        if (!user.isAdmin() && StringUtils.hasText(permissions)) {
            List<? extends RxPermissionable> ps = user.getMyPermissions();
            boolean flag = false;
            if(ps != null && ps.size() > 0){
                for (RxPermissionable p : ps) {
                    if (permissions.indexOf(p.getId() + ",") != -1) {
                        flag = true;
                        break;
                    }
                }
            }
            return flag;
        }else {
        	return true; 
        }
    }
	
    
    protected void permissionAnHandler(Annotation an) throws Exception {
        
    }
    
    protected boolean permissionHandler(RxUser<?> user,String permissions) throws Exception {
        return checkUserType(user,permissions);
    }
    
    
    /*
    protected void permissionHandler2(String permissions, Annotation an, Class<? extends RxUser> userType) throws Exception {
        if (userType != null) {
            RxUser<?> user = RxUser.getUser(userType);
            if (user == null) {
                throw new LoginException("请先登录");
            }
            if (!checkUserType(user,permissions)) {
                throw new ForbiddenException(String.format("您没有 %s 的权限", PermissionMgr.getAnnotationPermissionNames(an)));
            }
        } else {
            RxUser<?> user = RxUser.getUser();
            if (user == null) {
                throw new LoginException("请先登录");
            } else if (checkUserType(user,permissions)) {
                return;
            } else {
                throw new ForbiddenException(String.format("您没有 %s 的权限", PermissionMgr.getAnnotationPermissionNames(an)));
            }
        }
    }
    */
}
