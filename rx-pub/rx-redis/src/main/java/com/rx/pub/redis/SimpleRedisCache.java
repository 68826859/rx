package com.rx.pub.redis;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Collection;
import java.util.concurrent.Callable;

import javax.annotation.Resource;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.NullValue;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.util.ByteUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import com.rx.base.cache.RxCacher;

@Component
public class SimpleRedisCache implements RxCacher {

	private static final byte[] BINARY_NULL_VALUE = new JdkSerializationRedisSerializer().serialize(NullValue.INSTANCE);
	
	@Override
	public String getString(Object key) {
		
		return this.get(key, String.class);
	}

	@Override
	public Object getObject(String key) {
		// TODO Auto-generated method stub
		return this.get(key,Object.class);
	}

	@Override
	public void put(Object key, Object value, long millis) {
		getRedisCacheWriter().put(getSpringCache().getName(), createAndConvertCacheKey(key), serializeCacheValue(value), Duration.ofMillis(millis));

	}
	
	protected String createCacheKey(Object key) {

		String convertedKey = convertKey(key);

		if (!getSpringCache().getCacheConfiguration().usePrefix()) {
			return convertedKey;
		}

		return prefixCacheKey(convertedKey);
	}
	
	protected String convertKey(Object key) {

		TypeDescriptor source = TypeDescriptor.forObject(key);
		ConversionService conversionService = getSpringCache().getCacheConfiguration().getConversionService();
		
		if (conversionService.canConvert(source, TypeDescriptor.valueOf(String.class))) {
			return conversionService.convert(key, String.class);
		}

		Method toString = ReflectionUtils.findMethod(key.getClass(), "toString");

		if (toString != null && !Object.class.equals(toString.getDeclaringClass())) {
			return key.toString();
		}

		throw new IllegalStateException(
				String.format("Cannot convert %s to String. Register a Converter or override toString().", source));
	}
	
	
	
	protected byte[] serializeCacheKey(String cacheKey) {
		return ByteUtils.getBytes(getSpringCache().getCacheConfiguration().getKeySerializationPair().write(cacheKey));
	}
	
	protected byte[] serializeCacheValue(Object value) {

		if (getSpringCache().isAllowNullValues() && value instanceof NullValue) {
			return BINARY_NULL_VALUE;
		}

		return ByteUtils.getBytes(getSpringCache().getCacheConfiguration().getValueSerializationPair().write(value));
	}
	
	
	private byte[] createAndConvertCacheKey(Object key) {
		return serializeCacheKey(createCacheKey(key));
	}

	private String prefixCacheKey(String key) {
		return getSpringCache().getCacheConfiguration().getKeyPrefixFor(getSpringCache().getName()) + key;
	}
	
	
	private RedisCacheWriter getRedisCacheWriter() {
		return (RedisCacheWriter)this.getNativeCache();
	}
	
	@Resource
	CacheManager cacheManager;
	
	private RedisCache getSpringCache() {
		Collection<String> names = cacheManager.getCacheNames();
		for(String name : names) {
			Object obj = cacheManager.getCache(name).getNativeCache();
			return (RedisCache)cacheManager.getCache(name);
		}
		return null;
	}
	
	
	//@Override
	public Object getNativeCache() {
		// TODO Auto-generated method stub
		return getSpringCache().getNativeCache();
	}
	
	/*
	@Override
	public String getName() {
		return getSpringCache().getName();
	}

	

	@Override
	public ValueWrapper get(Object key) {
		// TODO Auto-generated method stub
		return getSpringCache().get(key);
	}
*/
	@Override
	public <T> T get(Object key, Class<T> type) {
		// TODO Auto-generated method stub
		return getSpringCache().get(key, type);
	}

	@Override
	public <T> T get(Object key, Callable<T> valueLoader) {
		// TODO Auto-generated method stub
		return getSpringCache().get(key, valueLoader);
	}

	@Override
	public void put(Object key, Object value) {
		// TODO Auto-generated method stub
		getSpringCache().put(key, value);
	}

	/*
	@Override
	public ValueWrapper putIfAbsent(Object key, Object value) {
		// TODO Auto-generated method stub
		return getSpringCache().putIfAbsent(key, value);
	}
	*/

	@Override
	public void evict(Object key) {
		// TODO Auto-generated method stub
		getSpringCache().evict(key);
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		getSpringCache().clear();
	}

	@Override
	public long pttl(Object key) {
		return 0;
	}

}
