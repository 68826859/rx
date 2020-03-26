package com.rx.model.utils;

import java.lang.reflect.Field;

import com.rx.base.model.ModelApplyEnum;
import com.rx.base.model.RxModelFieldApplyer;
import com.rx.base.model.annotation.RxConfig;
import com.rx.model.utils.AesUtils;

public class PasswordApplier implements RxModelFieldApplyer  {
	

	@Override
	public void apply(ModelApplyEnum type, Object model, Field field, Field tar, int tag, RxConfig[] cfg)
			throws Exception {
		if(type == ModelApplyEnum.SELECT) {
			Object  keyValue = tar.get(model);;
			String pwd = "";
			if (keyValue != null) {
				pwd = PasswordApplier.convertPwd((String) keyValue);
			}
			field.set(model, pwd);
		}else {
			Object ob = field.get(model);
			if (ob != null) {
				field.set(model, AesUtils.getInstance().encrypt(ob.toString()));
			}
		}
		
	}

	private static String convertPwd(String encryptPwd) {
		// 解密失败会返回原文
		// String clearPwd =
		// getStarString2(AesUtils.getInstance().decrypt(encryptPwd),1,1);
		String clearPwd = AesUtils.getInstance().decrypt(encryptPwd);
		return clearPwd;
	}

	public static void main(String[] args) throws Exception {
		// 密码的明文
		String clearPwd = "1234567890123123123123123123";
		// 密码加密后的密文
		String s0 = AesUtils.getInstance().encrypt(clearPwd);
		String s1 = AesUtils.getInstance().decrypt(s0);
		// 解密后的字符串
		System.out.println("加密:" + s0);
		System.out.println("解密:" + s1);

		String s2 = getStarString(s1, 1, 8);
		String s3 = getStarString2(s1, 1, 1);
		// 解密后的字符串
		System.out.println("指定加*:" + s2);
		System.out.println("首尾加*:" + s3);

	}

	/**
	 * 对字符串处理:将指定位置到指定位置的字符以星号代替
	 *
	 * @param content 传入的字符串
	 * @param begin   开始位置
	 * @param end     结束位置
	 */
	public static String getStarString(String content, int begin, int end) {

		if (begin >= content.length() || begin < 0) {
			return content;
		}
		if (end >= content.length() || end < 0) {
			return content;
		}
		if (begin >= end) {
			return content;
		}
		String starStr = "";
		for (int i = begin; i < end; i++) {
			starStr = starStr + "*";
		}
		return content.substring(0, begin) + starStr + content.substring(end, content.length());

	}

	/**
	 * 对字符加星号处理：除前面几位和后面几位外，其他的字符以星号代替
	 *
	 * @param content  传入的字符串
	 * @param frontNum 保留前面字符的位数
	 * @param endNum   保留后面字符的位数
	 * @return 带星号的字符串
	 */

	public static String getStarString2(String content, int frontNum, int endNum) {

		if (frontNum >= content.length() || frontNum < 0) {
			return content;
		}
		if (endNum >= content.length() || endNum < 0) {
			return content;
		}
		if (frontNum + endNum >= content.length()) {
			return content;
		}
		String starStr = "";
		for (int i = 0; i < (content.length() - frontNum - endNum); i++) {
			starStr = starStr + "*";
		}
		return content.substring(0, frontNum) + starStr
				+ content.substring(content.length() - endNum, content.length());

	}

}
