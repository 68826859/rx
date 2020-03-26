package com.rx.base.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class PatternUtil {

	private PatternUtil() {
	}

	public static String searchPattern(String string, String pattern, int num) {
		String matchStr = "";
		Matcher matcher = Pattern.compile(pattern).matcher(string);
		if (matcher.find()) {
			matchStr = matcher.group(num);
		}
		return matchStr;
	}

	public static boolean isMatch(String string, String pattern, boolean ignoreCase) {
		if (ignoreCase) {
			return Pattern.compile(pattern, Pattern.CASE_INSENSITIVE).matcher(string).matches();
		} else {
			return Pattern.compile(pattern).matcher(string).matches();
		}
	}

	/**
	 * 多行匹配
	 */
	public static boolean isMatchMul(String string, String pattern, boolean ignoreCase) {
		if (ignoreCase) {
			return Pattern.compile(pattern, Pattern.DOTALL | Pattern.CASE_INSENSITIVE).matcher(string).matches();
		} else {
			return Pattern.compile(pattern, Pattern.DOTALL).matcher(string).matches();
		}
	}

	public static boolean isMatch(String string, String pattern) {
		return isMatch(string, pattern, false);
	}

	/**
	 * 根据正则表达式查找匹配的字符串
	 * 
	 * @param string     源字符串
	 * @param pattern    正则表达式
	 * @param ignoreCase 是否忽略大小写
	 * @return
	 */
	public static String searchPattern(String string, String pattern, boolean ignoreCase) {
		String[] ms = searchPatterns(string, pattern, ignoreCase);
		return ms.length > 0 ? ms[0] : null;
	}

	/**
	 * 根据正则表达式查找匹配的字符串
	 * 
	 * @param string  源字符串
	 * @param pattern 正则表达式
	 * @return 匹配正则表达式的字符串, 如果找不到匹配的则返回空串
	 */
	public static String searchPattern(String string, String pattern) {
		String matchStr = "";
		Matcher matcher = Pattern.compile(pattern).matcher(string);
		if (matcher.find()) {
			matchStr = matcher.group();
		}
		return matchStr;
	}

	/**
	 * 替换匹配正则表达式的字符串
	 * 
	 * @param string    源字符串
	 * @param pattern   正则表达式
	 * @param replaceOf 替换的字符串
	 * @return 替换后的字符串
	 */
	public static String replace(final String string, String pattern, String replaceOf) {
		String temp = string;
		temp = Pattern.compile(pattern).matcher(temp).replaceAll(replaceOf);
		return temp;
	}

	/**
	 * 根据正则表达式查找匹配的字符串
	 * 
	 * @param string  源字符串
	 * @param pattern 正则表达式
	 * @return 匹配正则表达式的字符串, 如果找不到匹配的则返回空串
	 */
	public static String[] searchPatterns(String string, String pattern) {
		return searchPatterns(string, pattern, false);
	}

	/**
	 * 根据正则表达式查找匹配的字符串
	 * 
	 * @param string     源字符串
	 * @param pattern    正则表达式
	 * @param ignoreCase 是否忽略大小写
	 * @return 匹配正则表达式的字符串, 如果找不到匹配的则返回空串
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String[] searchPatterns(String string, String pattern, boolean ignoreCase) {
		List ltMatch = new ArrayList();
		Matcher matcher = null;
		if (ignoreCase) {
			matcher = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE).matcher(string);
		} else {
			matcher = Pattern.compile(pattern).matcher(string);
		}
		while (matcher.find()) {
			Object obj = matcher.group(0);
			if (!isMatch(obj.toString(), "^$")) {
				ltMatch.add(obj);
			}
		}
		return (String[]) ltMatch.toArray(new String[] {});
	}

	/**
	 * 把正则表达式里的特殊字符加上转译符
	 * 
	 * @param str
	 * @return
	 */
	public static String translation(String str) {
		if (StringUtil.noNull(str)) {
			return str.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\.", "\\\\.").replaceAll("\\?", "\\\\?")
					.replaceAll("\\+", "\\\\+").replaceAll("\\(", "\\\\(").replaceAll("\\)", "\\\\)")
					.replaceAll("\\[", "\\\\[").replaceAll("\\]", "\\\\]").replaceAll("\\|", "\\\\|")
					.replaceAll("\\^", "\\\\^").replaceAll("\\$", "\\\\$").replaceAll("\\*", "\\\\*")
					.replaceAll("\\{", "\\\\{");
		}
		return str;
	}

	public static void main(String[] args) {
		System.out.println(PatternUtil.searchPattern("ATY100_CSC100055", "^\\w+_[a-zA-Z]*(?=\\d*$)"));
	}

}
