package com.rx.web.handler;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
//import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import com.rx.base.result.DataResult;
import com.rx.base.result.type.ExtraException;
import com.rx.base.result.type.ValidateException;

import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;

@ControllerAdvice
public class GlobalExceptionHandler {

	
	public static boolean changeResponseStatus = true;
	
	protected final Log log = LogFactory.getLog(GlobalExceptionHandler.class);

	/** 验证异常
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	@ResponseBody
	public DataResult MethodArgumentNotValidHandler(HttpServletResponse response, MethodArgumentNotValidException ex) {
		ValidateException ve = new ValidateException();
		BindingResult bindingResult = ex.getBindingResult();
		String errorMesssage = "参数验证不通过：";
		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			errorMesssage += fieldError.getDefaultMessage() + ", ";
		}
		ve.setAlertMsg(errorMesssage);
		ve.setData(StackTraceMsg(ex));
		if(changeResponseStatus) {
			response.setStatus(ve.getCode());
		}
		return ve;
	} */

	/** 验证异常*/
	@ExceptionHandler(value = BindException.class)
	@ResponseBody
	public DataResult BindExceptionHandler(HttpServletResponse response, BindException ex) {
		ValidateException ve = new ValidateException();
		List<FieldError> fieldErrors = ex.getFieldErrors();
		StringBuffer msg = new StringBuffer();
		for(FieldError fieldError : fieldErrors) {
		//FieldError fieldError = ex.getFieldError();
			String dmsg = fieldError.getDefaultMessage();
			if (dmsg != null && dmsg.contains("java.lang.NumberFormatException")) {
				Pattern p = Pattern.compile("for property '.+'");
				Matcher m = p.matcher(dmsg);
				msg.append("参数格式错误");
				if (m.find()) {
					String group = m.group();
					msg.append(":").append(group);
				}
			}else {
				msg.append(dmsg).append(";");
			}
		}
		ve.setAlertMsg(msg.toString());
		ve.setData(StackTraceMsg(ex));
		if(changeResponseStatus) {
			response.setStatus(ve.getCode());
		}
		return ve;
	} 

	/** 数据库记录重复异常 
	@ExceptionHandler(value = DuplicateKeyException.class)
	@ResponseBody
	public DataResult DuplicateKeyExceptionHandler(HttpServletResponse response, DuplicateKeyException ex) {
		ExtraException extraException = new ExtraException();
		//MySQLIntegrityConstraintViolationException mcv = (MySQLIntegrityConstraintViolationException) ex.getCause();
		//String msg = mcv.getMessage();
		//msg = msg.substring("Duplicate entry".length(), msg.lastIndexOf("for key"));
		//msg += " 已存在";
		extraException.setAlertMsg("记录重复");
		extraException.setData(StackTraceMsg(ex));
		response.setStatus(extraException.getCode());
		return extraException;
	}*/
	
	/** 上传文件过大异常 */
	@ExceptionHandler(value = MaxUploadSizeExceededException.class)
	@ResponseBody
	public DataResult DuplicateKeyExceptionHandler(HttpServletResponse response, MaxUploadSizeExceededException ex) {
		long fs = ex.getMaxUploadSize();
		String mb = (fs == 0 ? 0 : fs / 1024 / 1024) + "MB";
		ExtraException extraException = new ExtraException("上传文件应不大于" + mb + ":" + ex.getMessage());
		extraException.setData(StackTraceMsg(ex));
		if(changeResponseStatus) {
			response.setStatus(extraException.getCode());
		}
		return extraException;
	}
	
	
	
	
	/** 业务异常 */
	@ExceptionHandler(DataResult.class)
	@ResponseBody
	public DataResult dataResultHandler(HttpServletResponse response, DataResult dataResult) {
		if(changeResponseStatus) {
			response.setStatus(dataResult.getCode());
		}
		//if (OUT_DBUG != null && OUT_DBUG.equals("1")) {
			// 处理错误信息和记录日志
		String strs = StackTraceMsg(dataResult);
			// 系统异常记录日志
		log.debug(strs);
		//}
		return dataResult;
	}
	
	
	
	/** 系统异常 */
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public DataResult exceptionHandler(HttpServletResponse response, Exception ex) {
		//if(ex.getCause() instanceof PersistenceException) {
		//	if(((PersistenceException)ex.getCause()).getCause() instanceof DataResult)
		//	return dataResultHandler(response,(DataResult)ex.getCause().getCause());
		//}
		
		return ExtraExceptionHandler(response, ex);
	}

	/** 其他异常 */
	@ExceptionHandler(Throwable.class)
	@ResponseBody
	public DataResult throwableHandler(HttpServletResponse response, Throwable ex) {
		return ExtraExceptionHandler(response, ex);
	}

	private ExtraException ExtraExceptionHandler(HttpServletResponse response, Throwable ex) {
		// 获取堆错误信息
		String strs = StackTraceMsg(ex);
		// 系统异常记录日志
		log.error(strs);
		// 处理客户端返回数据
		ExtraException extraException = new ExtraException(ex.getMessage());
		if(changeResponseStatus) {
			response.setStatus(extraException.getCode());
		}
		extraException.setAlertMsg("系统发生未知错误:"+ex.getClass().getName());
		extraException.setData(strs);
		return extraException;
	}

	private String StackTraceMsg(Throwable ex) {
		StringWriter sw = new StringWriter();
		ex.printStackTrace(new PrintWriter(sw, true));
		return sw.toString();
	}

}
