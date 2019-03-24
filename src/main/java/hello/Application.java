package hello;

import org.redisson.api.RedissonClient;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableCaching
public class Application {

    @Autowired
    private RedissonClient redisson;

    @Bean
    public CacheManager cacheManager() {
        return new RedissonSpringCacheManager(redisson, "classpath:caches.yml");
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}