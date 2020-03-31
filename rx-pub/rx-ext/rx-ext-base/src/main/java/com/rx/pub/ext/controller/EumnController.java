package com.rx.pub.ext.controller;

import com.rx.base.Showable;
import com.rx.base.result.DataResult;
import com.rx.base.result.type.ValidateException;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
	@RestController
@RequestMapping("/pub/enum")
public class EumnController {
    /**
     * 上传图片
     * @return
     * @throws Exception
     */
    @RequestMapping("/enumList")
    public DataResult enumList(String className) throws Exception{
    	
    	Assert.notNull(className,"枚举类名不能为空");
    	
    	Class clz = null;
    	if(className.contains(".")){
    		clz = Class.forName(className);
    	}
    	if(clz == null){
    		throw new ValidateException("未找到类名");
    	}
    	return new DataResult(getEnums(clz));
    }
    
    
    private static List getEnums(Class clz){
    	
    	List list = new ArrayList();
    	if(clz.isEnum()){
    		Map<String,Object> p;
    		for (Showable obj: (Showable[])clz.getEnumConstants()) {
    			p = new HashMap<String,Object>();
    			p.put("valueField",obj.value());
    			p.put("displayField",obj.display());
    			list.add(p);
    		}
    	}
    	return list;
    }

}
