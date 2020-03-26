package com.rx.pub.redis;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis配置
 */

@Configuration
@ConditionalOnClass(RedisOperations.class)
@EnableConfigurationProperties(RedisProperties.class)
//@Import({ LettuceConnectionConfiguration.class, JedisConnectionConfiguration.class })
public class RedisConfig extends CachingConfigurerSupport{

	
	protected final Log logger = LogFactory.getLog(RedisConfig.class);
	
	@Bean
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for (Object obj : params) {
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };
    }
	
	
	 @Bean
	    public JedisPoolConfig jedisPoolConfig(){
	        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
	        //最大连接数
	        jedisPoolConfig.setMaxTotal(100);
	        //最小空闲连接数
	        jedisPoolConfig.setMinIdle(20);
	        //当池内没有可用连接时，最大等待时间
	        jedisPoolConfig.setMaxWaitMillis(10000);
	        //其他属性可以自行添加
	        return jedisPoolConfig;
	    }
	 
	 
	 /*
	 @Bean
	 public RedisConnectionFactory redisConnectionFactory(JedisPoolConfig jedisPoolConfig) {
		logger.info("初始化Redis连接工厂");
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        //设置redis服务器的host或者ip地址
        redisStandaloneConfiguration.setHostName("localhost");
        redisStandaloneConfiguration.setPort(6379);
        //redisStandaloneConfiguration.setPassword(RedisPassword.of("U9zPySpu"));
        redisStandaloneConfiguration.setDatabase(1);
        //获得默认的连接池构造
        //这里需要注意的是，edisConnectionFactoryJ对于Standalone模式的没有（RedisStandaloneConfiguration，JedisPoolConfig）的构造函数，对此
        //我们用JedisClientConfiguration接口的builder方法实例化一个构造器，还得类型转换
        JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jpcf = (JedisClientConfiguration.JedisPoolingClientConfigurationBuilder) JedisClientConfiguration.builder();
        //修改我们的连接池配置
        jpcf.poolConfig(jedisPoolConfig);
        //通过构造器来构造jedis客户端配置
        JedisClientConfiguration jedisClientConfiguration = jpcf.build();
 
        return new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration);
	 }
	*/
	
	@Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        
		//RedisCacheManager redisCacheManager = RedisCacheManager.create(connectionFactory);
		Set<String> cacheNames = new HashSet<>();
        cacheNames.add("redis");
		RedisCacheManager redisCacheManager = RedisCacheManager.builder(connectionFactory).initialCacheNames(cacheNames).build();
        return redisCacheManager;
    }

    /**
     * @Description: 防止redis入库序列化乱码的问题
     * @return     返回类型
     * @date 2018/4/12 10:54
     */
    @SuppressWarnings("unchecked")
	@Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<Object, Object>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());//key序列化
        //redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer(null));  //value序列化

        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());

        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}