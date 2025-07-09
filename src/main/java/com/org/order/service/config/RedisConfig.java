package com.org.order.service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

@Configuration
public class RedisConfig
{
	private final String redisHost;
	private final int redisPort;

	public RedisConfig(@Value("${spring.data.redis.host}") String redisHost, @Value("${spring.data.redis.port}") int redisPort)
	{
		this.redisHost = redisHost;
		this.redisPort = redisPort;
	}

	@Bean
	JedisConnectionFactory jedisConnectionFactory()
	{
		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
		redisStandaloneConfiguration.setHostName(redisHost);
		redisStandaloneConfiguration.setPort(redisPort);

		return new JedisConnectionFactory(redisStandaloneConfiguration);
	}

	@Bean
	RedisTemplate<String, Object> redisTemplate(JedisConnectionFactory jedisConnectionFactory)
	{
		RedisTemplate<String, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(jedisConnectionFactory);
		template.setDefaultSerializer(new GenericJackson2JsonRedisSerializer()); // Use JSON serialization
		return template;
	}
}
