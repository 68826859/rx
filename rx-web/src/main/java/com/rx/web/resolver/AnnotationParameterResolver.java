package com.rx.web.resolver;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


public class AnnotationParameterResolver implements HandlerMethodArgumentResolver {
	
	private static List<Class<?>> clazzList = new ArrayList<Class<?>>();
	static {
	//clazzList.add(RxPattern.class);
	//clazzList.add(RxValidate.class);
	}
	
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		
	Annotation[] ans = parameter.getParameterAnnotations();
		
	if(ans != null){
			for(Annotation annotation : ans){
				if(clazzList.contains(annotation.annotationType())) return true;
			}
	}
	return false;
		
	}
	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		
	Annotation[] ans = parameter.getParameterAnnotations();
		
	if(ans != null){
			String paramName = parameter.getParameterName();
			for(Annotation annotation : ans){
				
				/*
				
				if(annotation.annotationType() == RxValidate.class){
					String vv = webRequest.getParameter(paramName);
					RxValidate validate = (RxValidate) annotation;
					Class<? extends RxValidator> cls = validate.cls();
					return cls.newInstance().validate(paramName,vv);
				}else if(annotation.annotationType() == RxPattern.class){
					if(parameter.getParameterType()!=String.class) throw new ValidateException("RxPattern只适用于String参数");
					String msg = paramName+"格式不对";
					String vv = webRequest.getParameter(paramName);
					if(vv==null) throw new ValidateException(paramName+"必填");
					RxPattern mjPattern = (RxPattern) annotation;
					String mm = mjPattern.message();
					msg = (StringUtils.isBlank(mm))? msg : mm;
					String regex = mjPattern.regexp();
					boolean flag = Pattern.matches(regex, vv);
					if(!flag) throw new ValidateException(msg);
					return vv;
				}
				*/
				/////////////////////////////////////////MjDefaultValue///////////////////////////////////////////////
				
//				if(annotation.annotationType() == MjDefaultValue.class){
//					MjDefaultValue mjDefaultValue = (MjDefaultValue) annotation;
//					if(parameter.getParameterType()!=String.class) throw new ValidateException("MjDefaultValue只适用于String参数");
//					if(AppContextHelper.getSysUser()==null) return value; //throw new LoginException(ConstantHelper.LOGIN_MESSAGE);
//					String vv = webRequest.getParameter(paramName);
//					if(StringUtils.isBlank(vv)){
//						switch (mjDefaultValue.value()) {
//						case ORGID:return AppContextHelper.getSysUser().getOrgId();
//						case USERNAME: return AppContextHelper.getSysUser().getUserName();
//						default: break;
//						}
//					}else{
//						value = vv;
//					}
					
//				}
			}
	}
	return webRequest.getParameter(parameter.getParameterName());
	}

}
