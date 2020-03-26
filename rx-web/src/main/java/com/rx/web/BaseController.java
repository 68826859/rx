package com.rx.web;

import com.rx.base.result.AlertTypeEnum;
import com.rx.base.result.DataResult;

public class BaseController {
	
	protected DataResult doJsonOut(Object data){
	return new DataResult(data);
	}
	
	protected DataResult doJsonOut(String msg,Object data){
	DataResult jsonOut = doJsonMsg(msg, AlertTypeEnum.无需关闭的提示1);
	jsonOut.setData(data);
	return jsonOut;
	}
	//	protected void doJsonStringOut(Object data){
//		HttpServletResponse response = AppContextHelper.getResponse();
//		response.setContentType("application/json;charset=utf-8");
//		OutputStream os = null;
//		try {
//			os = response.getOutputStream();
//			os.write(JSONObject.toJSONString(data).getBytes());
//			os.flush();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		IOUtils.close(os);
//	}
	
	/**
	* 输出提示信息
	* @param msg 提示信息
	* @param alertType 提示方式,0不弹框  1:无需关闭的提示,2:需要关闭的提示,3:无需关闭的错误,4:需要关闭的错误
	* @throws Exception
	*/
	public DataResult doJsonMsg(String msg,AlertTypeEnum alertType){
	return new DataResult(msg,alertType);
	}
	
	/**
	* 输出提示信息
	* @param msg 提示信息
	* @throws Exception
	*/
	public DataResult doJsonMsg(String msg){
	return this.doJsonMsg(msg, AlertTypeEnum.无需关闭的提示1);
	}
	}
