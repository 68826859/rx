package com.rx.pub.redis.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.stereotype.Component;
//import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;

import com.rx.base.cache.RxCacher;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

/**
 * redis 工具类
 */
public class RedisCacher implements RxCacher{
	
	//@Autowired
	//private RedisTemplate redisTemplate;

	@Autowired
	private JedisConnectionFactory jedisConnectionFactory;

	
	
	public <T> T excute(Function<Object, T> fun) {
		Jedis jedis = null;
		JedisCluster jedisCluster = null;

		try {
			if (null != jedisConnectionFactory) {
				Object conn = jedisConnectionFactory.getConnection().getNativeConnection();
				if (conn instanceof Jedis) {
					jedis = (Jedis) conn;
					return fun.callback(jedis);
				} else if (conn instanceof JedisCluster) {
					jedisCluster = (JedisCluster) conn;
					return fun.callback(jedisCluster);
				}
			}
			return fun.callback(jedis);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != jedis)
				jedis.close();
		}
		return null;
	}
	
	
	public <T> T excute(Callable<T> fun) {
		Jedis jedis = null;
		JedisCluster jedisCluster = null;

		try {
			if (null != jedisConnectionFactory) {
				Object conn = jedisConnectionFactory.getConnection().getNativeConnection();
				if (conn instanceof Jedis) {
					jedis = (Jedis) conn;
					return fun.call();
				} else if (conn instanceof JedisCluster) {
					jedisCluster = (JedisCluster) conn;
					return fun.call();
				}
			}
			return fun.call();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != jedis)
				jedis.close();
		}
		return null;
	}
	

	/**
	 * put缓存永久(覆盖同key)
	 * 
	 * @param key 缓存key
	 * @param obj 缓存对象
	 */
	public void putObject(final String key, final Object obj) {
		putObject(key, obj, 0);
	}

	/**
	 * put缓存(覆盖同key)
	 * 
	 * @param key     缓存key
	 * @param obj     缓存对象
	 * @param timeout 有效时间(单位:秒)
	 */
	public void putObject(final String key, final Object obj, final long timeout) {
		Assert.hasLength(key, "缓存key不能为空");
		Assert.notNull(obj, "缓存obj不能为空");
		boolean b = obj instanceof Serializable;
		if (!b)
			throw new IllegalArgumentException("缓存obj必须实现Serializable接口");
		if (timeout <= 0) {
//			redisTemplate.opsForValue().set(key, obj);
			excute(new Function<Object, String>() {
				@Override
				public String callback(Object conn) {
					if (conn instanceof JedisCluster) {
						return ((JedisCluster) conn).set(key.getBytes(), toByteArray(obj));
					} else {
						return ((Jedis) conn).set(key.getBytes(), toByteArray(obj));
					}
				}
			});
		} else {
//			redisTemplate.opsForValue().set(key, obj, timeout, TimeUnit.SECONDS);
			final int timeInt = (int) timeout;
			excute(new Function<Object, String>() {
				@Override
				public String callback(Object conn) {
					if (conn instanceof JedisCluster) {
						return ((JedisCluster) conn).setex(key.getBytes(), timeInt, toByteArray(obj));
					} else {
						return ((Jedis) conn).setex(key.getBytes(), timeInt, toByteArray(obj));
					}
				}
			});
		}

	}

	public byte[] toByteArray(Object obj) {
		if (obj == null) {
			return null;
		}
		byte[] bytes = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(obj);
			oos.flush();
			bytes = bos.toByteArray();
			oos.close();
			bos.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return bytes;
	}

	public Object toObject(byte[] bytes) {
		if (bytes == null || bytes.length == 0) {
			return bytes;
		}
		Object obj = null;
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
			ObjectInputStream ois = new ObjectInputStream(bis);
			obj = ois.readObject();
			ois.close();
			bis.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		return obj;
	}

	/**
	 * 获取缓存值
	 * 
	 * @param key 缓存key
	 * @return obj
	 */
	public Object getObject(final String key) {
//        return redisTemplate.opsForValue().get(key);
		return excute(new Function<Object, Object>() {
			@Override
			public Object callback(Object conn) {
				if (conn instanceof JedisCluster) {
					return toObject(((JedisCluster) conn).get(key.getBytes()));
				} else {
					return toObject(((Jedis) conn).get(key.getBytes()));
				}
			}
		});
	}

	public String set(final String key, final String value) {
		return excute(new Function<Object, String>() {
			@Override
			public String callback(Object conn) {
				if (conn instanceof JedisCluster) {
					return ((JedisCluster) conn).set(key, value);
				} else {
					return ((Jedis) conn).set(key, value);
				}
			}
		});
	}

	public String set(final String key, final String value, final Integer timeout) {
		return excute(new Function<Object, String>() {
			@Override
			public String callback(Object conn) {
				if (conn instanceof JedisCluster) {
					String set = ((JedisCluster) conn).set(key, value);
					((JedisCluster) conn).expire(key, timeout);
					return set;
				} else {
					String set = ((Jedis) conn).set(key, value);
					((Jedis) conn).expire(key, timeout);
					return set;
				}
			}
		});
	}

	public String get(final String key) {
		return excute(new Function<Object, String>() {
			@Override
			public String callback(Object conn) {
				if (conn instanceof JedisCluster) {
					return ((JedisCluster) conn).get(key);
				} else {
					return ((Jedis) conn).get(key);
				}
			}
		});
	}

	public void del(final String key) {
		excute(new Function<Object, Long>() {
			@Override
			public Long callback(Object conn) {
				// jedis.del(key.getBytes());
				if (conn instanceof JedisCluster) {
					return ((JedisCluster) conn).del(key);
				} else {
					return ((Jedis) conn).del(key);
				}
			}
		});
	}

	 /**
	  * 设置缓存的过期时间
	  * @param key
	  * @param seconds
	  * @return
	  */
	public Long expire(final String key, final Integer seconds) {
		return excute(new Function<Object, Long>() {
			@Override
			public Long callback(Object conn) {
				if (conn instanceof JedisCluster) {
					return ((JedisCluster) conn).expire(key, seconds);
				} else {
					return ((Jedis) conn).expire(key, seconds);
				}
			}
		});
	}

	/**
	 * 获得键的剩余时间，以毫秒为单位
	 * @param key
	 * @return
	 */
	public Long pttl(final String key) {
		return excute(new Function<Object, Long>() {
			@Override
			public Long callback(Object conn) {
				if (conn instanceof JedisCluster) {
					return ((JedisCluster) conn).pttl(key);
				} else {
					return ((Jedis) conn).pttl(key);
				}
			}
		});
	}

	public Set<String> keys(final String pattern) {
		return excute(new Function<Object, Set<String>>() {
			@Override
			public Set<String> callback(Object conn) {
				if (conn instanceof JedisCluster) {
					return ((JedisCluster) conn).hkeys(pattern);
				} else {
					return ((Jedis) conn).keys(pattern);
				}
			}
		});
	}

	public String hget(final String key, final String field) {
		return excute(new Function<Object, String>() {
			@Override
			public String callback(Object conn) {
				if (conn instanceof JedisCluster) {
					return ((JedisCluster) conn).hget(key, field);
				} else {
					return ((Jedis) conn).hget(key, field);
				}

			}
		});
	}

	public Map<String, String> hgetAll(final String key) {
		return excute(new Function<Object, Map<String, String>>() {
			@Override
			public Map<String, String> callback(Object conn) {
				if (conn instanceof JedisCluster) {
					return ((JedisCluster) conn).hgetAll(key);
				} else {
					return ((Jedis) conn).hgetAll(key);
				}
			}
		});
	}

	public Long hdel(final String key, final String... fields) {
		return excute(new Function<Object, Long>() {
			@Override
			public Long callback(Object conn) {
				if (conn instanceof JedisCluster) {
					return ((JedisCluster) conn).hdel(key, fields);
				} else {
					return ((Jedis) conn).hdel(key, fields);
				}
			}
		});
	}

	public Long hset(final String key, final String field, final String value) {
		return excute(new Function<Object, Long>() {
			@Override
			public Long callback(Object conn) {
				if (conn instanceof JedisCluster) {
					return ((JedisCluster) conn).hset(key, field, value);
				} else {
					return ((Jedis) conn).hset(key, field, value);
				}
			}
		});
	}

	public String type(final String key) {
		return excute(new Function<Object, String>() {
			@Override
			public String callback(Object conn) {
				if (conn instanceof JedisCluster) {
					return ((JedisCluster) conn).type(key);
				} else {
					return ((Jedis) conn).type(key);
				}
			}
		});
	}

	public Set<String> hkeys(final String key) {
		return excute(new Function<Object, Set<String>>() {
			@Override
			public Set<String> callback(Object conn) {
				if (conn instanceof JedisCluster) {
					return ((JedisCluster) conn).hkeys(key);
				} else {
					return ((Jedis) conn).hkeys(key);
				}
			}
		});
	}

	public Long sadd(final String key, final String... values) {
		return excute(new Function<Object, Long>() {
			@Override
			public Long callback(Object conn) {
				if (conn instanceof JedisCluster) {
					return ((JedisCluster) conn).sadd(key, values);
				} else {
					return ((Jedis) conn).sadd(key, values);
				}
			}
		});
	}

	public Long srem(final String key, final String... values) {
		return excute(new Function<Object, Long>() {
			@Override
			public Long callback(Object conn) {
				if (conn instanceof JedisCluster) {
					return ((JedisCluster) conn).srem(key, values);
				} else {
					return ((Jedis) conn).srem(key, values);
				}
			}
		});
	}

	public Set<String> smembers(final String key) {
		return excute(new Function<Object, Set<String>>() {
			@Override
			public Set<String> callback(Object conn) {
				if (conn instanceof JedisCluster) {
					return ((JedisCluster) conn).smembers(key);
				} else {
					return ((Jedis) conn).smembers(key);
				}
			}
		});
	}

	public List<String> brpop(final int timeout, final String key) {
		return excute(new Function<Object, List<String>>() {
			@Override
			public List<String> callback(Object conn) {
				if (conn instanceof JedisCluster) {
					return ((JedisCluster) conn).brpop(timeout, key);
				} else {
					return ((Jedis) conn).brpop(timeout, key);
				}
			}
		});
	}

	public Long lpush(final String key, final String value) {
		return excute(new Function<Object, Long>() {
			@Override
			public Long callback(Object conn) {
				if (conn instanceof JedisCluster) {
					return ((JedisCluster) conn).lpush(key, value);
				} else {
					return ((Jedis) conn).lpush(key, value);
				}
			}
		});
	}

	public String ltrim(final String key, final int start, final int end) {
		return excute(new Function<Object, String>() {
			@Override
			public String callback(Object conn) {
				if (conn instanceof JedisCluster) {
					return ((JedisCluster) conn).ltrim(key, start, end);
				} else {
					return ((Jedis) conn).ltrim(key, start, end);
				}
			}
		});
	}

	public Long llen(final String key) {
		return excute(new Function<Object, Long>() {
			@Override
			public Long callback(Object conn) {
				if (conn instanceof JedisCluster) {
					return ((JedisCluster) conn).llen(key);
				} else {
					return ((Jedis) conn).llen(key);
				}
			}
		});
	}

	public String flushAll() {
	    return excute(new Function<Object, String>() {
            @Override
            public String callback(Object conn) {
                if (conn instanceof JedisCluster) {
                    return ((JedisCluster) conn).flushAll();
                } else {
                    return ((Jedis) conn).flushAll();
                }
            }
        });
    }
	
	
	
	
	/*
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "redis";
	}

	@Override
	public Object getNativeCache() {
		return jedisConnectionFactory.getConnection().getNativeConnection();
	}

	@Override
	public ValueWrapper get(Object key) {
		return null;
	}
	*/

	@Override
	public <T> T get(Object key, Class<T> type) {
		return excute(new Function<Object, T>() {
			@Override
			public T callback(Object conn) {
				if (conn instanceof JedisCluster) {
					return (T)toObject(((JedisCluster) conn).get(toByteArray(key)));
				} else {
					return (T)toObject(((Jedis) conn).get(toByteArray(key)));
				}
			}
		});
	}

	@Override
	public <T> T get(Object key, Callable<T> valueLoader) {
		return excute(valueLoader);
	}

	@Override
	public void put(Object key, Object value) {
		this.put(key,value, 0);
	}

	/*
	 * @Override public ValueWrapper putIfAbsent(Object key, Object value) { // TODO
	 * Auto-generated method stub this.put(key, value); return null; }
	 */

	@Override
	public void evict(Object key) {
		
		excute(new Function<Object, Long>() {
			@Override
			public Long callback(Object conn) {
				// jedis.del(key.getBytes());
				if (conn instanceof JedisCluster) {
					return ((JedisCluster) conn).del(toByteArray(key));
				} else {
					return ((Jedis) conn).del(toByteArray(key));
				}
			}
		});
	}

	@Override
	public void clear() {
		this.flushAll();
	}

	@Override
	public String getString(Object key) {
		return this.get(key, String.class);
	}

	@Override
	public void put(Object key, Object value, long milliseconds) {
		Assert.notNull(key, "缓存key不能为空");
		Assert.notNull(value, "缓存obj不能为空");
		boolean b = value instanceof Serializable;
		if (!b)
			throw new IllegalArgumentException("缓存obj必须实现Serializable接口");
		if (milliseconds <= 0) {
			excute(new Function<Object, String>() {
				@Override
				public String callback(Object conn) {
					if (conn instanceof JedisCluster) {
						return ((JedisCluster) conn).set(toByteArray(key), toByteArray(value));
					} else {
						return ((Jedis) conn).set(toByteArray(key), toByteArray(value));
					}
				}
			});
		} else {
			final int timeInt = (int) milliseconds;
			excute(new Function<Object, String>() {
				@Override
				public String callback(Object conn) {
					if (conn instanceof JedisCluster) {
						return ((JedisCluster) conn).setex(toByteArray(key), timeInt, toByteArray(value));
					} else {
						return ((Jedis) conn).setex(toByteArray(key), timeInt, toByteArray(value));
					}
				}
			});
		}
		
	}


	@Override
	public long pttl(Object key) {
		// TODO Auto-generated method stub
		return 0;
	}

	

}
