package com.rx.pub.ext.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//@RequestMapping("dev")
public class DevController {
    
    @RequestMapping("/dev") 
    public String dev(HttpServletRequest request/*, @RequestParam(value = "name", defaultValue = "springboot-thymeleaf") String name*/) {
    	request.setAttribute("ExtBasePath","https://nnstatic.oss-cn-shenzhen.aliyuncs.com/jsFramework/ext6/");
        request.setAttribute("ServerPath", request.getServerName() + ":" + request.getServerPort() + request.getContextPath()); 
        request.setAttribute("ContextPath", request.getContextPath()); 
        return "dev"; 
    } 
}