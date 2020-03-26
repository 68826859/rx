package com.rx.base.cache;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

public class CacheHelper {
	
	private static RxCacher cacher;

	public static RxCacher getCacher() {

		if (cacher == null) {
			//cacher = AppContextHelper.getBean(RxCacher.class);
			if(cacher == null) {
				//new RuntimeException("初始化cacher对象失败");
				cacher = new RxCacher() {

					private CacheMap map = new CacheMap();

					@Override
					public String getString(Object key) {
						// TODO Auto-generated method stub
						return (String)map.get(key);
					}

					@Override
					public Object getObject(String key) {
						// TODO Auto-generated method stub
						return map.get(key);
					}

					@Override
					public void put(Object key, Object value, long milliseconds) {
						// TODO Auto-generated method stub
						map.put(key, value,milliseconds);
					}

					@Override
					public <T> T get(Object key, Class<T> type) {
						// TODO Auto-generated method stub
						return (T)map.get(key);
					}

					@Override
					public <T> T get(Object key, Callable<T> valueLoader) {
						// TODO Auto-generated method stub
						return (T)map.get(key);
					}

					@Override
					public void put(Object key, Object value) {
						// TODO Auto-generated method stub
						map.put(key, value);
					}

					@Override
					public void evict(Object key) {
						// TODO Auto-generated method stub
						map.remove(key);
					}

					@Override
					public void clear() {
						// TODO Auto-generated method stub
						map.clear();
					}

					@Override
					public long pttl(Object key) {
						return map.pttl(key);
					}	
				};
			}
		}
		return cacher;
	}
	
	public static void setCacher(RxCacher cache) {
		cacher = cache;
	}
}

class CacheMap<K,V> implements Map<K,V> {


    private Map<K,V> hashMap;

    private Map<K,Long> timeMap;

    private Long long_minus_1 = Long.valueOf(-1);

    public CacheMap(int initialCapacity, float loadFactor) {
        hashMap = new ConcurrentHashMap<K,V>(initialCapacity,loadFactor);
        timeMap = new ConcurrentHashMap<K,Long>(initialCapacity,loadFactor);
    }
    public CacheMap(int initialCapacity) {
        hashMap = new ConcurrentHashMap<K,V>(initialCapacity);
        timeMap = new ConcurrentHashMap<K,Long>(initialCapacity);
    }
    public CacheMap() {
        hashMap = new ConcurrentHashMap<K,V>();
        timeMap = new ConcurrentHashMap<K,Long>();
    }
    /*
    public CacheMap(Map<? extends K, ? extends V> m) {
        hashMap = new ConcurrentHashMap<K,V>(m);
        timeMap = new ConcurrentHashMap<K,Long>();
    }
    */

    /**
     * 设置键的过期时间
     * @param key
     * @param milliseconds 过期时间 ，单位毫秒,如果设置0,等于删除，设置小于0等于设置永久
     * @return 返回键的过期时间 单位毫秒 ，键没有过期时间返回 -1 ，不存在键 ，返回0
     */
    public long expire(K key,long milliseconds) {
        if(milliseconds < 0){
            return expire_(key,long_minus_1);
        }else {
            return expire_(key,Long.valueOf(milliseconds));
        }
    }
    /**
     * 设置键的过期时间
     * @param key
     * @param milliseconds 过期时间 ，单位毫秒,如果设置0 等于删除，设置 负数=永久
     * @return 返回键的过期时间 单位毫秒 ，键没有过期时间返回 -1 ，不存在键 ，返回0
     */
    private long expire_(K key,Long milliseconds){
        Iterator<Map.Entry<K,Long>> it = timeMap.entrySet().iterator();
        Map.Entry<K, Long> entry;
        while(it.hasNext()){
            entry = it.next();
            if(entry.getKey().equals(key)) {
                long tm = entry.getValue().longValue();
                if( (tm == -1 || tm - System.currentTimeMillis() > 0 ) && milliseconds != null && milliseconds.longValue() != 0 ){
                    if(milliseconds.longValue() < 0){
                        entry.setValue(long_minus_1);
                        return -1;
                    }else {
                        long lv = System.currentTimeMillis() + milliseconds.longValue();
                        entry.setValue(Long.valueOf(lv));
                        return lv;
                    }
                } else{
                    hashMap.remove(key);
                    it.remove();
                    return 0;
                }
            }
        }
        return 0;
    }
    /**
     * 以毫秒为单位获取key的剩余时间，没有设置时间返回 -1,不存在键 返回0
     * @param key
     * @return milliseconds 单位毫秒
     */
    public long pttl(K key){
        return pttl_(key);
    }
    /**
     * 以毫秒为单位获取key的剩余时间，没有设置时间返回 -1,不存在键 返回0
     * @param key
     * @return milliseconds 单位毫秒
     */
    private long pttl_(K key){
        Iterator<Map.Entry<K,Long>> it = timeMap.entrySet().iterator();
        Map.Entry<K, Long> entry;
        while(it.hasNext()){
            entry = it.next();
            if(entry.getKey().equals(key)) {
                Long tm = entry.getValue();
                if(tm.longValue() < 0){
                    return -1;
                }
                long dis = tm.longValue() - System.currentTimeMillis();
                if(dis > 0){
                    return dis;
                }else{
                    hashMap.remove(key);
                    it.remove();
                    return 0;
                }
            }
        }
        return 0;
    }

    /**
     *
     * @param key
     * @param value
     * @param milliseconds 毫秒值 0为删除，负数 则设置永久
     * @return
     */
    public V put(K key, V value ,long milliseconds) {
        if(value == null){
            return null;
        }
        if(milliseconds > 0){
            timeMap.put(key, Long.valueOf(System.currentTimeMillis() + milliseconds));
        }else if(milliseconds == 0){
            remove_(key);
            return null;
        }else{
            timeMap.put(key,long_minus_1);
        }
        return hashMap.put(key,value);
    }

    private int checkMap(){
        Iterator<Map.Entry<K,Long>> it = timeMap.entrySet().iterator();
        Map.Entry<K, Long> entry;
        int len = 0;
        while(it.hasNext()){
            entry = it.next();
            long tm = entry.getValue().longValue();
            if(tm > 0 && tm - System.currentTimeMillis() <= 0){
                hashMap.remove(entry.getKey());
                it.remove();
            }else{
                len++;
            }
        }
        return len;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////


    @Override
    public int size() {
        return checkMap();
    }

    @Override
    public boolean isEmpty() {
        return checkMap() == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        if(timeMap.containsKey(key)){
        	long left = pttl_((K)key);
            return left > 0 || left == -1;
        }
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        checkMap();
        return hashMap.containsValue(value);
    }

    @Override
    public V get(Object key) {
        if(containsKey(key)) {
            return hashMap.get(key);
        }
        return null;
    }

    @Override
    public V put(K key, V value) {
        if(value == null){
            return null;
        }
        timeMap.put(key,long_minus_1);
        return hashMap.put(key,value);
    }

    @Override
    public V remove(Object key) {
        if(timeMap.containsKey(key)){
            return remove_((K)key);
        }
        return null;
    }

    private V remove_(K key){
        Iterator<Map.Entry<K,Long>> it = timeMap.entrySet().iterator();
        Map.Entry<K, Long> entry;
        while(it.hasNext()){
            entry = it.next();
            if(entry.getKey().equals(key)) {
                long tm = entry.getValue().longValue();
                it.remove();
                if(tm < 0 || tm - System.currentTimeMillis() > 0){
                    return hashMap.remove(key);
                }else{
                    hashMap.remove(key);
                    return null;
                }
            }
        }
        return null;
    }



    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for(K st : m.keySet()){
            timeMap.put(st,long_minus_1);
        }
        hashMap.putAll(m);
    }

    @Override
    public void clear() {
        timeMap.clear();
        hashMap.clear();
    }

    @Override
    public Set<K> keySet() {
        checkMap();
        return hashMap.keySet();
    }

    @Override
    public Collection<V> values() {
        checkMap();
        return hashMap.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        checkMap();
        return hashMap.entrySet();
    }
}