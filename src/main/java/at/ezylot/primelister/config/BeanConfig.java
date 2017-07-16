package at.ezylot.primelister.config;

import at.ezylot.primelister.BufferedPrinter;
import at.ezylot.primelister.WorkScheduler;
import com.google.common.collect.ImmutableList;
import org.springframework.cache.CacheManager;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public BufferedPrinter bufferedPrinter() {
        return new BufferedPrinter(System.out);
    }


    @Bean
    public WorkScheduler workSchedulerDevelopment() {
        return new WorkScheduler(Runtime.getRuntime().availableProcessors());
    }

    @Bean
    public CacheManager guavaCacheManager() {
        GuavaCacheManager cacheManager = new GuavaCacheManager();
        cacheManager.setCacheNames(ImmutableList.of("squareroots"));
        return cacheManager;
    }
}
