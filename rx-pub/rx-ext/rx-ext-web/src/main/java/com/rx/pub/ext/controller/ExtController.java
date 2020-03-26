package com.rx.pub.ext.controller;


import java.util.List;

import com.rx.base.page.Pager;
import com.rx.base.result.DataResult;
import com.rx.base.result.type.ValidateException;
import com.rx.base.service.BaseService;
import com.rx.base.vo.ListVo;
import com.rx.ext.Define;
import com.rx.ext.annotation.ExtClass;
import com.rx.extrx.spring.SpringProvider;
import com.rx.spring.utils.SpringContextHelper;
import com.rx.web.utils.HttpServletHelper;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/ext")
@ExtClass(extend=SpringProvider.class,alternateClassName="ExtController")
public class ExtController {
    
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
	
	
	/**
     * 获得模型
     * @return
     * @throws Exception
     */
    @RequestMapping("/define")
    public DataResult define(String className) throws Exception{
    	Class<?> clazz = null;
    	try {
            if(className.contains(".")){
                clazz = Class.forName(className);
            }
            if(clazz == null){
                throw new Exception();
            }
    	}catch (Exception e){
            throw new ValidateException("未找到类名").setData(className);
    	}
    	HttpServletResponse response = getResponse();
    	
    	response.setHeader("Content-Type", "application/javascript;charset=UTF-8");
    	response.setCharacterEncoding("UTF-8");
    	ServletOutputStream os = response.getOutputStream();
    	os.write(Define.define(clazz).getBytes("UTF-8"));
    	os.flush();
    	return null;
    }
    
    @RequestMapping("/listModel")
    public DataResult listModel(String className, String query,String[] keys) throws Exception{
    	Class<?> clazz = null;
    	try {
            if(className.contains(".")){
                clazz = Class.forName(className);
            }
    	}catch (Exception e){
            throw new ValidateException("未找到目标类:"+className);
    	}
    	BaseService<?> service = SpringContextHelper.getBeanService(clazz);
    	String limit = HttpServletHelper.getRequest().getParameter("limit");
    	if(limit != null && Integer.parseInt(limit) > 0){
			Pager<ListVo> pager = new Pager<ListVo>();
			pager.setPageSize(Integer.parseInt(limit));
			Pager<ListVo> list = service.selectDisplayListPage(null,query,keys,pager);
	    	return new DataResult(list);
    	}
    	List<ListVo> list = service.selectDisplayList(null,query,keys);
    	return new DataResult(list);
    }
    
    @RequestMapping("/listFK")
    public DataResult listFK(String className, String query,String[] keys) throws Exception{
    	Class<?> clazz = null;
    	try {
            if(className.contains(".")){
                clazz = Class.forName(className);
            }
    	}catch (Exception e){
            throw new ValidateException("未找到目标类:"+className);
    	}
    	BaseService<?> service = SpringContextHelper.getBeanService(clazz);
    	List<ListVo> list = service.selectDisplayList(null,query,keys);
    	return new DataResult(list);
    }
    
    
    
    @RequestMapping("/del")
    public DataResult del(String clazz, String id) throws Exception{
    	return new DataResult("删除成功");
    }

    public static void main(String[] args){
    	/*
    	try {
			System.out.println(cn.com.gdca.lra.system.vo.AddOrg.class.getDeclaredField("orgName").getDeclaringClass());
	} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	}
	*/
    	try {
    		
    		
    		//Base b = Base.forClass(BasestructureTreePanel.class);
    		//for(Class<?> cls:BasestructureTreePanel.class.getClasses()){
    		//	System.out.println(cls.getName());
    		//}
    		//System.out.println(b.toJS());
    		//b.addConfig("11111", "11111");
    		//System.out.println(b.toJS());
    		
    		//Base.setupPlugin(ExtDefinePermissionPlugin.class);
    		
    		
    		//System.out.println(URLEncoder.encode("我的"));
    		//DictionaryMgr.registerDictionarys(RxDictionaryField.class, DictionaryEnum.class);
    		//System.out.println(Define.define(CertBusStatusEumn.class));
    		//System.out.println(Define.define(cn.com.gdca.lra.system.po.SysOrgRoleRef.class));
			//System.out.println(Define.define(cn.com.gdca.lra.system.vo.AddOrg.class));
	} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	}
		
    	
    }
    
}
