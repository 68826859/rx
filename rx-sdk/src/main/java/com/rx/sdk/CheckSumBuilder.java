package com.rx.sdk;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class CheckSumBuilder {
	public static String getCheckSum(String appSecret, String nonce, String curTime) {
		return encode("sha1", appSecret + nonce + curTime);
	}
	
	public static String getSha1(String key) {
		return encode("sha1", key);
	}

	public static String getMD5(String requestBody) {
		return encode("md5", requestBody);
	}

	public static String getMD5(byte[] bytes) {
		return encode("md5", bytes);
	}

	public static String getSortedMapString(HashMap<String, String> map) {
		Collection<String> keyset = map.keySet();
		List<String> list = new ArrayList<String>(keyset);
		Collections.sort(list);

		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			String key = list.get(i);
			stringBuilder.append(key + "=" + (String) map.get(key));
		}
		return stringBuilder.toString();
	}

	private static String encode(String algorithm, String value) {
		if (value == null) {
			return null;
		}

		try {
			MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
			messageDigest.update(value.getBytes());
			return getFormattedText(messageDigest.digest());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static String encode(String algorithm, byte[] bytes) {
		if (bytes == null) {
			return null;
		}

		try {
			MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
			messageDigest.update(bytes);
			return getFormattedText(messageDigest.digest());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static String getFormattedText(byte[] bytes) {
		int len = bytes.length;
		StringBuilder buf = new StringBuilder(len * 2);
		for (int j = 0; j < len; j++) {
			buf.append(HEX_DIGITS[bytes[j] >> 4 & 0xF]);
			buf.append(HEX_DIGITS[bytes[j] & 0xF]);
		}
		return buf.toString();
	}

	private static final char[] HEX_DIGITS = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b',
			'c', 'd', 'e', 'f' };
}
