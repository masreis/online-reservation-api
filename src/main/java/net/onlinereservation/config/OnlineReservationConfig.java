package net.onlinereservation.config;

import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class OnlineReservationConfig {

	@Bean
	public ModelMapper modelMapper1() {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper;
	}

	@Bean
	public CacheManagerCustomizer<ConcurrentMapCacheManager> cacheManagerCustomizer() {
		return new CacheManagerCustomizer<ConcurrentMapCacheManager>() {

			@Override
			public void customize(ConcurrentMapCacheManager cacheManager) {
			}
		};
	}
}
