package com.rest.movie.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {
    @Bean
    public CacheManager cacheManager() {
        //return new ConcurrentMapCacheManager("movies", "actors", "directors", "genres");
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("movies", "actors", "directors", "genres");
        cacheManager.setCaffeine(caffeineCacheBuilder());

        return cacheManager;
    }
    @Bean
    public Caffeine<Object, Object> caffeineCacheBuilder() {
        return Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES) // Expira 10 minutos después de la escritura
                .maximumSize(100); // Máximo de 100 entradas
    }
}
