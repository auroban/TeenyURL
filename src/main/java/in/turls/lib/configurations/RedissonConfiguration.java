package in.turls.lib.configurations;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfiguration {
	
	private final static Logger LOG = LogManager.getLogger(RedissonConfiguration.class);
	
	@Value("${redisson.address}")
	private String redisAddress;
	
	@Bean(destroyMethod = "shutdown")
	public RedissonClient redissonClient() {
		LOG.info("Creaing RedissonClient from RedissonConfiguration class");
		Config config = new Config();
		LOG.info("Redis Address: {}", redisAddress);
		config.useSingleServer().setAddress(redisAddress);
		return Redisson.create(config);
	}
	
	@Bean
	public CacheManager cacheManager(@Autowired RedissonClient redissonClient) {
		LOG.info("Supplying RedissonSpringCacheManager from RedissonConfiguration class");
		Map<String, CacheConfig> cacheConfigMap = new HashMap<String, CacheConfig>();
		cacheConfigMap.put("testCacheMap", new CacheConfig(24*60*1000, 12*60*1000));
		return new RedissonSpringCacheManager(redissonClient, cacheConfigMap);
	}

}
