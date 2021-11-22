package com.fih.ishareing.configurations.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class LettuceRedisConfig {

	@Bean("redisTemplate")
	public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory redisConnectionFactoryy) {

		RedisTemplate<String, String> redisTemplate = new RedisTemplate<String, String>();
		redisTemplate.setConnectionFactory(redisConnectionFactoryy);
		RedisSerializer<String> stringSerializer = new StringRedisSerializer();

		redisTemplate.setKeySerializer(stringSerializer);
		redisTemplate.setValueSerializer(stringSerializer);
		redisTemplate.setHashKeySerializer(stringSerializer);
		redisTemplate.setHashValueSerializer(new GenericToStringSerializer<Object>(Object.class));

		redisTemplate.afterPropertiesSet();

		return redisTemplate;
	}

}
