package com.rx.sdk;

import java.util.HashMap;
import java.util.Map;

public class SdkProfileConfig {
	
	
	private static Map<ProfileEnum, HashMap<String, String>> configMap = new HashMap<ProfileEnum, HashMap<String, String>>();
	
	public static String getConfig(ProfileEnum profile, String busType) {
		HashMap<String, String> map = configMap.get(profile);
		if(map != null) {
			return map.get(busType);
		}
		return null;
	}

	public static void setConfig(ProfileEnum profile, String busType ,String value) {
		HashMap<String, String> map = configMap.get(profile);
		if(map == null) {
			map = new HashMap<String, String>();
			configMap.put(profile, map);
		}
		if(map.containsKey(busType)) {
			throw new SdkException(999,"重复的属性注册:profile="+profile.name()+",busType="+busType+",value="+value);
		}
		map.put(busType, value);
	}
	
	public final static String BusType_businessId = "_businessId";
	public final static String BusType_businessSecret = "_businessSecret";
	public final static String BusType_host = "_host";
	
	
	public static ProfileEnum Profile = ProfileEnum.DEV;
	
	public static void initialize(String businessId, String businessSecret, ProfileEnum profile) throws SdkException {
		
		if(businessId == null) {
			throw new SdkException(999,"businessId不能为空");
		}
		if(businessSecret == null) {
			throw new SdkException(999,"businessSecret不能为空");
		}
		if(profile == null) {
			throw new SdkException(999,"profile不能为空");
		}
		
		Profile = profile;
		
		setConfig(profile,BusType_businessId,businessId);
		
		setConfig(profile,BusType_businessSecret,businessSecret);
		
	}
}
