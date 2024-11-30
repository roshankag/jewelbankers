package com.jewelbankers.configuration;

import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;

//@Configuration
public class CacheConfig {

    //@Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("usersListCache"); // Cache name(s)
    }
}
