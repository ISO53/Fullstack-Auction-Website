package com.iso.bidding.config;

import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@EnableCaching
@Configuration
public class RedisConfiguration implements CachingConfigurer {
}
