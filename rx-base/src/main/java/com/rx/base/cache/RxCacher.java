package com.rx.base.cache;

import java.util.concurrent.Callable;



public interface RxCacher {
	
	String getString(Object key);
	
	Object getObject(String key);
	
	void put(Object key, Object value ,long milliseconds);
	
	/**
	 *  返回剩余毫秒数
	 * @param key
	 * @return
	 */
	long pttl(Object key);

	<T> T get(Object key, Class<T> type);
	
	<T> T get(Object key, Callable<T> valueLoader);
	
	void put(Object key, Object value);
	
	/**
	 * 清除掉某个key
	 * @param key
	 */
	void evict(Object key);

	
	void clear();
	
	
	
	public static RxCacher  getCacher() {
		
		return null;
		
	}
	
}